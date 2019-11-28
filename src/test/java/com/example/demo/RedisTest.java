package com.example.demo;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.ReactiveStringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.Duration;
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RedisTest {
    @Autowired
    private ReactiveStringRedisTemplate reactiveStringRedisTemplate;

    @Test
    public void testRedis() {

        Flux flux = Mono.just("value1")
                .flatMap(v -> reactiveStringRedisTemplate.opsForValue().set("user1", v))
                .thenMany(reactiveStringRedisTemplate.keys("*")
                        .flatMap(key -> reactiveStringRedisTemplate.opsForValue().get(key)));/* .subscribe(v->Assert.assertEquals(v,"value1"))*/
       // flux.subscribe(v -> Assert.assertEquals(v, "value1"));
        StepVerifier.create(flux)
                .verifyComplete();
    }
    @Test
    public void testRedis1() {

        Flux flux = Mono.just("value1")
                .flatMap(v -> reactiveStringRedisTemplate.opsForHash().put("user1", "sessionKey", v))
                .thenMany(reactiveStringRedisTemplate.keys("*")
                        .flatMap(key -> reactiveStringRedisTemplate.opsForHash().get(key, "sessionKey")));/* .subscribe(v->Assert.assertEquals(v,"value1"))*/

        StepVerifier.create(flux)
                .verifyComplete();
    }
    @Test
    public void testRedisWithExpired() {

        Mono.just("value1")
                .flatMap(v->{
                    reactiveStringRedisTemplate.expire("user1", Duration.ofMinutes(1)).subscribe();
                    return reactiveStringRedisTemplate.opsForHash().put("user1","sessionKey", v);
                })
                .thenMany(reactiveStringRedisTemplate.keys("*")
                        .flatMap(key-> reactiveStringRedisTemplate.opsForHash().get(key,"sessionKey")))
                .subscribe(v-> Assert.assertEquals(v,"value1"));



    }
}
