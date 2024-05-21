//package com.rs.countrydetails.jwt;
//
//import com.rs.countrydetails.exception.CDException;
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.springframework.stereotype.Component;
//import org.springframework.web.filter.OncePerRequestFilter;
//import java.io.IOException;
//@Component
//public class JwtFilter extends OncePerRequestFilter {
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//        String header = request.getHeader("Authenticate");
//
//
//        try {
//            if (header == null || !header.startsWith("Bearer")) {
//                filterChain.doFilter(request, response);
//                return;
//            }
//        } catch (Exception ignored) {
//            response.setHeader("Error","credentials entered were either wrong or don't exist");
//            try {
//                throw new CDException("");
//            } catch (CDException e) {
//                throw new RuntimeException(e);
//            }
//
//        }
//    }
//}
