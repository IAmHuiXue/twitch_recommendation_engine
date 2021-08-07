package com.laioffer.jupiter.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.laioffer.jupiter.external.TwitchClient;
import com.laioffer.jupiter.external.TwitchException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// @WebServlet annotation is to map the Servlet class and the url pattern
// so that when the server receives HTTP request, it will map to the corresponding Servlet
@WebServlet(name = "GameServlet", urlPatterns = {"/game"})
// use GameServlet to serve game queries
// clients send the request either with a game name as the parameter, and we return the
// details of that game, or without a game name, and we return the details of a list of top games
public class GameServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        // Get gameName from request URL
        String gameName = request.getParameter("game_name");
        // Create a TwitchClient to send requests from our server to Twitch API
        TwitchClient client = new TwitchClient();

        // Let the client know the returned data is in JSON format.
        response.setContentType("application/json;charset=UTF-8");
        try {
            // Return the dedicated game information if gameName is provided in the request URL,
            // otherwise return the top x games.
            if (gameName != null) {
                // ObjectMapper is a mapper tool provided by Jackson to convert between JSON strings and Java objects
                // here it turns Java objects into JSON strings by writeValueAsString() method
                response.getWriter().print(new ObjectMapper().writeValueAsString(client.searchGame(gameName)));
            } else {
                response.getWriter().print(new ObjectMapper().writeValueAsString(client.topGames(0)));
            }
        } catch (TwitchException e) {
            throw new ServletException(e); // --> 500 internal server error
        }
    }
}
