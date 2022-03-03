package com.laioffer.jupiter.servlet;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.codec.digest.DigestUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class ServletUtil {

//    public static void writeItemMap(HttpServletResponse response, Map<String, List<Item>> itemMap) throws IOException {
//        response.setContentType("application/json;charset=UTF-8");
//        response.getWriter().print(new ObjectMapper().writeValueAsString(itemMap));
//    }

    public static <T> void writeItem(HttpServletResponse response, T item) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().print(new ObjectMapper().writeValueAsString(item));
    }

    // Help encrypt the user password before save to the database
    public static String encryptPassword(String userId, String password) throws IOException {
        return DigestUtils.md5Hex(userId + DigestUtils.md5Hex(password)).toLowerCase();
    }

    public static <T> T readRequestBody(Class<T> cl, HttpServletRequest request) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(request.getReader(), cl);
        } catch (JsonParseException | JsonMappingException e) {
            return null;
        }
    }

    public static void verifySession(HttpSession session, HttpServletResponse response) throws IOException {
        if (session == null) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return;
        }
    }
}
