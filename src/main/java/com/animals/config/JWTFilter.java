package com.animals.config;
import static com.animals.config.SecurityConstants.HEADER_STRING;
import static com.animals.config.SecurityConstants.KEY;
import static com.animals.config.SecurityConstants.TOKEN_PREFIX;

import com.animals.models.Users;
import com.animals.repositories.UsersRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import io.jsonwebtoken.Jwts;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class JWTFilter extends BasicAuthenticationFilter {
    private UsersRepository userMapper;

    public JWTFilter(AuthenticationManager authenticationManager, UsersRepository userMapper) {
        super(authenticationManager);
        this.userMapper = userMapper;

    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String header = request.getHeader(HEADER_STRING);
        if (header == null || !header.startsWith(TOKEN_PREFIX)) {
            chain.doFilter(request, response);
            return;
        }
        try {
            UsernamePasswordAuthenticationToken authentication = getAuthentication(header);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token is invalid");
            return;
        }

        chain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(String header) {
        if (header != null) {
            String username = Jwts.parser()
                    .setSigningKey(KEY)
                    .parseClaimsJws(header.replace(TOKEN_PREFIX, ""))
                    .getBody()
                    .getSubject();


            if (username != null) {
                Users user = userMapper.findByUsername(username);
                if (user!= null) {
                    //Seting in your AuthenticationPrincipal the user
                    return new UsernamePasswordAuthenticationToken(user, null, new ArrayList<>());
                }
            }
            return null;
        }
        return null;
    }
}
