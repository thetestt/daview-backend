package com.daview.security;

import com.daview.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;

    public JwtAuthenticationFilter(JwtUtil jwtUtil, UserDetailsService userDetailsService) {
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String requestPath = request.getRequestURI();
        System.out.println("JWT Filter processing: " + requestPath);
        
        // 특정 경로는 JWT 검증 완전히 제외
        if (requestPath.startsWith("/api/admin/products") || 
            requestPath.startsWith("/admin/caregivers") ||  // 요양사 관리 전체 경로 제외
            requestPath.startsWith("/api/auth") || 
            requestPath.startsWith("/api/account") ||
            requestPath.startsWith("/uploads") ||
            requestPath.startsWith("/ws-chat")) {
            System.out.println("JWT Filter skipped for: " + requestPath);
            filterChain.doFilter(request, response);
            return;
        }

        String authHeader = request.getHeader("Authorization");
        System.out.println("Authorization header: " + authHeader);

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            try {
                String token = authHeader.substring(7);
                String username = jwtUtil.extractUsername(token);
                Long memberId = jwtUtil.extractMemberId(token);

                if (username != null) {
                    UserDetails userDetails = userDetailsService.loadUserByUsername(username);

                    if (jwtUtil.validateToken(token, userDetails)) {
                        UsernamePasswordAuthenticationToken authToken =
                                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(authToken);

                        request.setAttribute("memberId", memberId);
                        System.out.println("[JWT 필터] memberId = " + memberId);
                        System.out.println("JWT authentication successful for: " + username);
                    }
                }

            } catch (Exception e) {
                System.out.println("JWT parsing error for " + requestPath + ": " + e.getMessage());
                // 에러가 발생해도 계속 진행
            }
        }

        filterChain.doFilter(request, response);
    }
}
