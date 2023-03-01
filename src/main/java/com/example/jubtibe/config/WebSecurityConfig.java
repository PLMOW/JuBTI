package com.example.jubtibe.config;

import com.example.jubtibe.jwt.JwtAuthFilter;
import com.example.jubtibe.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;


@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class WebSecurityConfig {
    // 작성자 : 조영준
    private final JwtUtil jwtUtil;
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring()
//                .requestMatchers(PathRequest.toH2Console())
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable();

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.authorizeRequests().antMatchers("/api/**").permitAll()
                .antMatchers("/swagger-ui/**").permitAll()
                .antMatchers("/swagger-resources/**").permitAll()
                .antMatchers("/v3/api-docs/**").permitAll()
                .anyRequest().authenticated()
                .and().cors()
                .and().addFilterBefore(new JwtAuthFilter(jwtUtil), UsernamePasswordAuthenticationFilter.class);


//        http.formLogin().loginPage("/api/auth/login").permitAll(); //로그인 페이지 : form이 있어야 가능,그냥 formLogin만 사용하면 loginPage 없어도됨
//        http.addFilterBefore(new CustomSecurityFilter(userDatailService, passwordEncoder()), UsernamePasswordAuthenticationFilter.class); 써주면 custom한 security filter 쓸수있다.

        http.exceptionHandling().accessDeniedPage("/api/auth/login"); // 인증불가시 보낼곳

        // 401 Error 처리, Authorization 즉, 인증과정에서 실패할 시 처리
//        http.exceptionHandling().authenticationEntryPoint(customAuthenticationEntryPoint);

        // 403 Error 처리, 인증과는 별개로 추가적인 권한이 충족되지 않는 경우
//        http.exceptionHandling().accessDeniedHandler(customAccessDeniedHandler);

        return http.build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOrigin("*");
        configuration.addAllowedMethod("*");
        configuration.addAllowedHeader("*");
        configuration.addExposedHeader("*");
        configuration.setMaxAge(300L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

}