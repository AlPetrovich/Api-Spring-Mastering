package com.codmain.order.security;

import com.codmain.order.entity.User;
import com.codmain.order.exceptions.NoDataFoundException;
import com.codmain.order.repository.UserRepository;
import com.codmain.order.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class TokenAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepo;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try{
            String jwt = getJwtFromRequest(request); //extrae el token del request

            if (StringUtils.hasText(jwt) && userService.validateToken(jwt)){
                String username = userService.getUserNameFromToken(jwt); //extrae usuario
                User user = userRepo.findByUsername(username) //obtener usuario de la base de datos
                        .orElseThrow(()-> new NoDataFoundException("No existe el usuario"));

                UserPrincipal principal = UserPrincipal.create(user);
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(principal,null, principal.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                //este es el usuario autenticado
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }catch (Exception e){
            log.error("Error al autenticar al usuario", e);
        }

        filterChain.doFilter(request, response);
    }

    public String getJwtFromRequest(HttpServletRequest request){

        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")){
            return bearerToken.substring(7, bearerToken.length());
        }
        return null;
    }
}
