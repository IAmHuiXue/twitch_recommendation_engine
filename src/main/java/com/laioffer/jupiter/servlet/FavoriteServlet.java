package com.laioffer.jupiter.servlet;

import com.laioffer.jupiter.db.MySQLConnection;
import com.laioffer.jupiter.db.MySQLException;
import com.laioffer.jupiter.holders.FavoriteRequestBody;
import com.laioffer.jupiter.entity.Item;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import static com.laioffer.jupiter.servlet.ServletUtil.verifySession;

@WebServlet(name = "FavoriteServlet", value = {"/favorite"})
public class FavoriteServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Check if the session is still valid, which means the user has been logged in successfully.
        HttpSession session = request.getSession(false);
        verifySession(session, response);
        String userId = (String) session.getAttribute("user_id");
        Map<String, List<Item>> itemMap;
        MySQLConnection connection = null;
        try {
            // Read the favorite items from the database
            connection = new MySQLConnection();
            itemMap = connection.getFavoriteItems(userId);
            ServletUtil.writeItem(response, itemMap);
        } catch (MySQLException e) {
            throw new ServletException(e);
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        verifySession(session, response);
        String userId = (String) session.getAttribute("user_id");
        FavoriteRequestBody body = ServletUtil.readRequestBody(FavoriteRequestBody.class, request);
        if (body == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        MySQLConnection connection = null;
        try {
            // Save the favorite item to the database
            connection = new MySQLConnection();
            connection.setFavoriteItem(userId, body.getFavoriteItem());
        } catch (MySQLException e) {
            throw new ServletException(e);
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
    }

    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        verifySession(session, response);
        String userId = (String) session.getAttribute("user_id");
        FavoriteRequestBody body = ServletUtil.readRequestBody(FavoriteRequestBody.class, request);
        if (body == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        MySQLConnection connection = null;
        try {
            // Remove the favorite item to the database
            connection = new MySQLConnection();
            connection.unsetFavoriteItem(userId, body.getFavoriteItem().getId());
        } catch (MySQLException e) {
            throw new ServletException(e);
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
    }
}

