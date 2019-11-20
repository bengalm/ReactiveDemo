package com.example.demo.uitls;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonParser.Feature;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.TimeZone;

@Slf4j
public class JsonUtlis {

	//private static log log = LoggerFactory.getLogger(JsonUtlis.class);
	
    private static final ObjectMapper objectMapper;
   
    static {
        objectMapper = new ObjectMapper();
        // set 去掉默认的时间戳格式
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        // set 设置为中国上海时区
        objectMapper.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        // set 空值不序列化； 只对pojo起作用，对map和list不起作用
        objectMapper.setSerializationInclusion(Include.NON_NULL);
        // set 反序列化时，属性不存在的兼容处理
        objectMapper.getDeserializationConfig().withoutFeatures(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        // set 序列化时，日期的统一格式
        // objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        // set 忽略空Bean转json的错误
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        // set 遇到未知属性是否抛出异常 ，默认是抛出异常的
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        // set 单引号处理；默认是false
        objectMapper.configure(Feature.ALLOW_SINGLE_QUOTES, true);
    }
 
    
    @SuppressWarnings("unchecked")
	public static <T> T toObject(String json, Class<T> clz) {
    	if (StringUtils.isBlank(json) || clz == null) return null;
        try {
            return clz.equals(String.class) ? (T) json : objectMapper.readValue(json, clz);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            T t = null;
            try {
                t = clz.newInstance();
            } catch (InstantiationException | IllegalAccessException ex) {
                log.error(ex.getMessage(), ex);
            }
            return t;
        }
        
    }
 
    
    public static <T> String toJson(T entity) {
    	if (entity == null) return null;
        try {
            return entity instanceof String ? (String) entity : objectMapper.writeValueAsString(entity);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }
 
    
    @SuppressWarnings("unchecked")
	public static <T> T toCollection(String json, TypeReference<T> reference) {
    	if (StringUtils.isBlank(json) || reference == null) return null;
        try {
            return reference.getType().equals(String.class) ? (T)json : objectMapper.readValue(json, reference);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return null;
        } 
    }
    
    
    public static <T> T convertValue(Object obj, Class<T> clazz) {
    	return objectMapper.convertValue(obj, clazz);
    }
}