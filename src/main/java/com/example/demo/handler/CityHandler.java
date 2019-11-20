package com.example.demo.handler;

import com.example.demo.entity.City;
import com.example.demo.repo.CityRepository;
import com.example.demo.uitls.JsonUtlis;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ReactiveStringRedisTemplate;
import org.springframework.data.redis.core.ReactiveValueOperations;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class CityHandler {

    private final CityRepository cityRepository;
    private static final String CITY_KYE = "city:";
    private final ReactiveStringRedisTemplate reactiveStringRedisTemplate;

    @Autowired
    public CityHandler(CityRepository cityRepository, ReactiveStringRedisTemplate reactiveRedisTemplate) {
        this.cityRepository = cityRepository;
        this.reactiveStringRedisTemplate = reactiveRedisTemplate;
    }



    public Mono<City> save(City city) {
        String key = CITY_KYE + city.getId();
        ReactiveValueOperations<String, String> operations = reactiveStringRedisTemplate.opsForValue();
        Mono<City> save = cityRepository.save(city);
        save.flatMap(s -> operations.set(key, JsonUtlis.toJson(s))).subscribe(System.out::println);
        return save;
    }

    public Mono<City> findCityById(Long id) {
        String key = CITY_KYE + id;
        ReactiveValueOperations<String, String> operations = reactiveStringRedisTemplate.opsForValue();
        // 缓存存在
        Mono<Boolean> booleanMono = reactiveStringRedisTemplate.hasKey(key);
        return booleanMono.flatMap(v -> {
            if (v) {
                Mono<City> cityMono = operations.get(key).flatMap(s -> Mono.just(JsonUtlis.toObject(s, City.class)));
                cityMono.subscribe(city -> log.info("findCityById() getByCache >> {} ", city));
                return cityMono;
            }
            Mono<City> byId = cityRepository.findById(id);
            return byId.flatMap(CityHandler::apply);
        });
    }

    private static Mono<City> apply(City city1) {
        log.info("findCityById apply city1 = {}", city1);
        if (city1 == null) {
            return Mono.just(new City());
        }
        return Mono.just(city1);
    }

    public Flux<City> findAllCity() {
        return cityRepository.findAll();
    }

    public Mono<City> modifyCity(City city) {
        String key = CITY_KYE + city.getId();
        ReactiveValueOperations<String, String> operations = reactiveStringRedisTemplate.opsForValue();
        Mono<City> save = cityRepository.save(city);
        save.flatMap(city1 -> operations.set(key, JsonUtlis.toJson(city1)));
        return save;
    }

    public Mono<Long> deleteCity(Long id) {
        String key = CITY_KYE + id;
        ReactiveValueOperations<String, String> operations = reactiveStringRedisTemplate.opsForValue();
        cityRepository.deleteById(id).subscribe();
        Mono.just(key).flatMap(s -> operations.delete(s)).subscribe(aBoolean -> {
            System.out.println("redis 删除 id "+aBoolean);
        });
        return Mono.create(cityMonoSink -> cityMonoSink.success(id));
    }

}