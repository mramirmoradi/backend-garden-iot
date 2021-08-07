package com.project.garden.security.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.garden.security.user.model.Role;
import com.project.garden.security.user.service.UserService;
import com.project.garden.core.context.ContextHolder;
import com.project.garden.core.context.ContextHolderService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.util.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    private static final Logger log = LoggerFactory.getLogger(JwtAuthorizationFilter.class);
    private final Environment environment;
    private final UserService userService;
    private final ContextHolderService contextHolderService;

    public JwtAuthorizationFilter(
                                  AuthenticationManager authenticationManager,
                                  Environment environment,
                                  UserService userService,
                                  ContextHolderService contextHolderService) {
        super(authenticationManager);
        this.environment = environment;
        this.userService = userService;
        this.contextHolderService = contextHolderService;
    }
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws IOException, ServletException {
        var authentication = getAuthentication(request);
        var header = request.getHeader(environment.getProperty("security.token.request.header"));
        if (authentication == null
                || StringUtils.isEmpty(header)
                || !header.startsWith(environment.getProperty("security.token.prefix") + " ")) {
            filterChain.doFilter(request, response);
            return;
        }
        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(request, response);
    }

    private void setUserState(ContextHolder contextHolder) {
        this.contextHolderService.setUserState(contextHolder);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        var token = request.getHeader(environment.getProperty("security.token.request.header"));
        if (StringUtils.isNotEmpty(token)) {
            try {
                String[] tokenBody = token.split("\\.");
                Base64 base64 = new Base64(true);
                String body = new String(base64.decode(tokenBody[1]));
                Map<String, String> mapTokenBody = new ObjectMapper().readValue(body, Map.class);

                var parsedToken =
                        Jwts.parserBuilder()
                              .setSigningKey(environment.getProperty("security.secret.key").getBytes())
                        .build()
                        .parseClaimsJws(token.replace(environment.getProperty("security.token.prefix") + " ", ""));

                var id = parsedToken.getBody().getSubject();
                if (!userService.isExist(Long.parseLong(id)))
                    return null;
                ContextHolder contextHolder = ContextHolder.builder()
                        .userId(Long.parseLong(id))
                        .role(Role.valueOf(mapTokenBody.get("role")))
                        .build();
                if (mapTokenBody.containsKey("gateId"))
                    contextHolder.setGateId(Long.parseLong(mapTokenBody.get("gateId")));
                setUserState(contextHolder);

                List<String> permissionList = (List<String>) parsedToken.getBody().get("PERMISSIONS");
                List<SimpleGrantedAuthority> permission = permissionList.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
                if (StringUtils.isNotEmpty(id)) {
                    return new UsernamePasswordAuthenticationToken(id, null, permission);
                }
            } catch (ExpiredJwtException exception) {
                log.warn("Request to parse expired JWT : {} failed : {}", token, exception.getMessage());
            } catch (UnsupportedJwtException exception) {
                log.warn("Request to parse unsupported JWT : {} failed : {}", token, exception.getMessage());
            } catch (MalformedJwtException exception) {

            } catch (SignatureException exception) {
                log.warn("Request to parse JWT with invalid signature : {} failed : {}", token, exception.getMessage());
            } catch (IllegalArgumentException exception) {
                log.warn("Request to parse empty or null JWT : {} failed : {}", token, exception.getMessage());
            } catch (JsonMappingException e) {
                e.printStackTrace();
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }catch (Exception exception){
                log.warn("Request to parse JWT with invalid signature : {} failed : {}", token, exception.getMessage());
            }
        }
        return null;
    }
}
