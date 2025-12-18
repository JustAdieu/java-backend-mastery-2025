package com.adieu.common.util;

import com.adieu.common.pojo.User;

public class JsonUtil {

    public static String obj2Json(Object obj){
        if(obj == null){
            return "{}";
        }
        if(obj instanceof User){
            User user = (User)obj;
            return new StringBuilder()
                    .append("{\"id\":\"").append(user.getId()).append("\",")
                    .append("\"name\":\"").append(user.getName()).append("\",")
                    .append("\"age\":").append(user.getAge())
                    .append("}").toString();
        }else if (obj instanceof com.adieu.service.pojo.User) {
            com.adieu.service.pojo.User user = (com.adieu.service.pojo.User) obj;
            return new StringBuilder()
                    .append("{\"id\":\"").append(user.getId()).append("\",")
                    .append("\"name\":\"").append(user.getName()).append("\",")
                    .append("\"age\":").append(user.getAge()).append(",")
                    .append("\"email\":\"").append(user.getEmail()).append("\"")
                    .append("}").toString();
        }

        return "{}";
    }
}
