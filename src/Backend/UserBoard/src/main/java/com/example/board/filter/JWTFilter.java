package com.example.board.filter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JWTFilter extends GenericFilter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        ServletOutputStream pw = response.getOutputStream();
        String authHeader = request.getHeader("Authorization");

        if (request.getRequestURI().endsWith("/register")) {
            filterChain.doFilter(request, response);
            return;
        }

        if (authHeader == null || !authHeader.startsWith("Bearer")) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            System.out.println("Working if");
            pw.println("Missing or invalid Token ");
            pw.close();
        } else {
            System.out.println("Working else");
            String jwtToken = authHeader.substring(7);
            Claims username = Jwts.parser().setSigningKey("21182518").parseClaimsJws(jwtToken).getBody();
            request.setAttribute("username", username);
            System.out.println("Working" + username);
            filterChain.doFilter(request, response);
        }
    }
}
