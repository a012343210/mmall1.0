package com.mmall.util;

import com.mmall.pojo.testPojo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.type.JavaType;
import org.codehaus.jackson.type.TypeReference;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * @Auther: Administrator
 * @Date: 2019/4/21 21:44
 * @Description:
 */

@Slf4j
public class JsonUtils {
    private static ObjectMapper objectMapper = new ObjectMapper();
    static {
        //取消默认转换timestamp形式 true为时间戳
        objectMapper.configure(SerializationConfig.Feature.WRITE_DATES_AS_TIMESTAMPS,false);

        //所有的日期格式都统一为一下的样式
        objectMapper.setDateFormat( new SimpleDateFormat(DateTimeUtil.STANDARD_FORMAT));

        //对象的所有字段全部列入
        objectMapper.setSerializationInclusion(JsonSerialize.Inclusion.ALWAYS);

        //忽略空bean转json的错误 true报异常
        objectMapper.configure(SerializationConfig.Feature.FAIL_ON_EMPTY_BEANS,false);

        //忽略在json字符串中存在,但是在对象中不存在对应属性的情况,防止错误  true报异常
        objectMapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES,false);
    }

    public static <T> String objToString(T obj){
        if (obj == null){
            return null;
        }
        try {
            return obj instanceof String ? (String)obj : objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            log.warn("parst Object to String error",e);
            return null;
        }
    }

    public static <T> String objToStringPretty(T obj){
        if (obj == null){
            return null;
        }
        try {
            return obj instanceof String ? (String)obj : objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
        } catch (Exception e) {
            log.warn("parst Object to String error",e);
            return null;
        }
    }

    public static <T> T String2Object(String str,Class<T> clazz){
        if (StringUtils.isEmpty(str) || clazz == null) {
            return null;
        }
        try {
            return clazz.equals(String.class) ? (T)str : objectMapper.readValue(str,clazz);
        } catch (Exception e) {
            log.warn("parst String to Object error",e);
            return null;
        }
    }

    public static <T> T String2Object(String str, TypeReference<T> tTypeReference){
        if (StringUtils.isEmpty(str) || tTypeReference == null) {
            return null;
        }
        try {
            return (T)(tTypeReference.getType().equals(String.class)?  str : objectMapper.readValue(str,tTypeReference));
        } catch (Exception e) {
            log.warn("parst String to Object error",e);
            return null;
        }
    }

    public static <T> T String2Object(String str, Class<?> collectionClass , Class<?>... elementClasses){
        JavaType javaType = objectMapper.getTypeFactory()
                .constructParametricType(collectionClass,elementClasses);
        try {
            return  objectMapper.readValue(str,javaType);
        } catch (Exception e) {
            log.warn("parst String to Object error",e);
            return null;
        }
    }

    public static void main(String[] args) {
        testPojo testPojo = new testPojo();
        String s = JsonUtils.objToStringPretty(testPojo);
        log.info(s);

    }


}