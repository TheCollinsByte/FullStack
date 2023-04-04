package com.addaboy.api.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * This Filter will be used Once Per request
 *  NOTE: Adding the Authentication Bean to the SecurityContext allows us to add the @AuthenticationPrincipal annotation as input parameter at any controller
 *        The Annotation will inject the Authenticated User so the Filter will give to the Controller and Object Of the authenticated User
 */
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final UserAuthProvider userAuthProvider;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {

        String header = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (header != null) {
            String[] elements = header.split(" ");

            if (elements.length == 2 && "Bearer".equals(elements[0])) {
                try {
                    /**
                     * Validate credentials add the authentication bean into the security context
                     */
                    SecurityContextHolder.getContext().setAuthentication(
                            userAuthProvider.validateToken(elements[1])
                    );
                } catch (RuntimeException e) {
                    /**
                     * If Something goes wrong clear the security context and throw and error
                     */
                    SecurityContextHolder.clearContext();
                    throw e;
                }
            }
        }

        filterChain.doFilter(request, response);
    }
}
