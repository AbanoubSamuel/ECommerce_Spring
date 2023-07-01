package com.ecommerce.ecommerce.security;

import com.ecommerce.ecommerce.api.model.JsonResponse;
import com.ecommerce.ecommerce.dao.UserDAO;
import com.ecommerce.ecommerce.entities.User;
import com.ecommerce.ecommerce.impl.JWTServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

@Component
public class JWTRequestFilter extends OncePerRequestFilter {

    private JWTServiceImpl jwtService;
    private UserDAO userDAO;

    public JWTRequestFilter(JWTServiceImpl jwtService, UserDAO userDAO)
    {
        this.jwtService = jwtService;
        this.userDAO = userDAO;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException
    {
        String tokenHeader = request.getHeader(SecurityConstants.AUTHORIZATION);
        if (tokenHeader != null && tokenHeader.startsWith("Bearer ")) {
            String token = tokenHeader.substring(7);
            try {
                System.out.println("JWTRequestFilter");
                String username = jwtService.getUsername(token);
                Optional<User> optionalUser = userDAO.findByUsernameIgnoreCase(username);
                if (optionalUser.isPresent()) {
                    User user = optionalUser.get();
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user, null, new ArrayList<>());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            } catch (Exception exception) {
                logger.error("Invalid token: " + exception.getMessage());

                JsonResponse<Object> jsonResponse = new JsonResponse<>();
                jsonResponse.setStatus(false);
                jsonResponse.setMessage("Invalid token !");

                ObjectMapper objectMapper = new ObjectMapper();
                String jsonResponseJson = objectMapper.writeValueAsString(jsonResponse);
                // Set the response content type and write JSON response
                response.setContentType("application/json");
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write(jsonResponseJson);
                response.getWriter().flush();
                return;
            }

        }
        filterChain.doFilter(request, response);
    }
}
