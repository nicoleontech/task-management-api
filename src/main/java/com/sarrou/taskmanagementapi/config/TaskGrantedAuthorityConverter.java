package com.sarrou.taskmanagementapi.config;

import com.sarrou.taskmanagementapi.user.UserService;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
@Slf4j
public class TaskGrantedAuthorityConverter implements Converter<Jwt, Collection<GrantedAuthority>> {

    @NonNull
    private final UserService userService;

    @NonNull
    private final JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter ;

    @Autowired
    public TaskGrantedAuthorityConverter(@NonNull UserService userService) {
        this.userService = userService;
        this.jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
    }

    @Override
    public Collection<GrantedAuthority> convert(Jwt source) {
        Collection<GrantedAuthority> authorities = jwtGrantedAuthoritiesConverter.convert(source);
        if(userService.hasUserWithEmail(source.getClaimAsString("email"))){
            log.info("user found, grant registered authority");
            authorities.add(new SimpleGrantedAuthority(WebSecurityConfiguration.REGISTERED_USER_SCOPE));
        } else {
            log.warn("user not found");
        }
        return authorities;
    }

    @Override
    public <U> Converter<Jwt, U> andThen(Converter<? super Collection<GrantedAuthority>, ? extends U> after) {
        return Converter.super.andThen(after);
    }
}
