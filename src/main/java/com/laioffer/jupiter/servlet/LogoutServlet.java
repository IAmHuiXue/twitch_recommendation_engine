package com.laioffer.jupiter.servlet;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "LogoutServlet", value = "/logout")
public class LogoutServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Destroy the session since the user is logged out.
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }

        // Cookie: Cookies are text files stored on the client computer, and they are kept for user tracking purposes.
        // The server script sends a set of cookies to the browser. The browser stores this information on a local
        // machine for future use. When the next time the browser sends any request to the web server then it
        // sends those cookies' information to the server and the server uses that information to identify the user.
        Cookie cookie = new Cookie("JSESSIONID", null);
        cookie.setPath("/");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }
}
