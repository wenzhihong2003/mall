package com.github.user.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.github.common.util.U;

import java.util.Map;

/** 用户等级 */
public enum UserTestLevel {

    Normal(0, "普通用户"), Vip(1, "vip 用户"), God(2, "黄金用户");

    int code;
    String value;
    UserTestLevel(int code, String value) {
        this.code = code;
        this.value = value;
    }
    /** 显示用 */
    public String getValue() {
        return value;
    }
    /** 数据关联用 */
    public int getCode() {
        return code;
    }

    /** 序列化给前端时, 如果只想给前端返回数值, 去掉此方法并把注解挪到 getCode 即可 */
    @JsonValue
    public Map<String, String> serializer() {
        return U.serializerEnum(code, value);
    }
    /** 数据反序列化. 如 male、0、男、{"code": 0, "value": "男"} 都可以反序列化为 Gender.Male 值 */
    @JsonCreator
    public static UserTestLevel deserializer(Object obj) {
        UserTestLevel level = U.enumDeserializer(obj, UserTestLevel.class);
        return U.isBlank(level) ? Normal : level;
    }
}
