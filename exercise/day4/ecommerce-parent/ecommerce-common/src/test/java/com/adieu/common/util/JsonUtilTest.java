package com.adieu.common.util;

import com.adieu.common.pojo.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class JsonUtilTest {

    @Test
    public void testObj2Json(){
        User user = new User("1001", "张三", 25);
        String json = JsonUtil.obj2Json(user);
        System.out.println("【common模块】对象转JSON：" + json);
        // 验证结果正确性
        assertTrue(json.contains("\"id\":\"1001\""));
        assertTrue(json.contains("\"name\":\"张三\""));
        assertTrue(json.contains("\"age\":25"));
    }



}

