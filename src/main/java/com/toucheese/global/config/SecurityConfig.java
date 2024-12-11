package com.toucheese.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.toucheese.global.util.JwtAuthenticationFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

	private final JwtAuthenticationFilter jwtAuthenticationFilter;

	@Bean
	SecurityFilterChain apiFilterChain(HttpSecurity http) throws Exception {
		http
			.csrf(AbstractHttpConfigurer::disable)
			.cors(cors -> cors.configurationSource(corsConfigurationSource())) // CORS 설정 추가
			.authorizeHttpRequests(requests ->
				requests.requestMatchers(HttpMethod.GET, "/v1/concepts/**").permitAll()
					.requestMatchers(HttpMethod.GET, "/v1/studios/**").permitAll()
					.requestMatchers(HttpMethod.GET, "/v1/products/**").permitAll()
					.requestMatchers(HttpMethod.GET, "/v1/reviews/**").permitAll()
					.requestMatchers(HttpMethod.POST, "/v1/members/**").permitAll()
					.requestMatchers(HttpMethod.POST, "/v1/messages/**").permitAll()
					.requestMatchers("/swagger", "/swagger-ui.html", "/swagger-ui/**", "/api-docs", "/api-docs/**",
						"/v3/api-docs/**").permitAll()
					.anyRequest().authenticated()
			)
			.sessionManagement(sessionManagement ->
				sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			)
			.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}

	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.addAllowedOrigin("https://www.toucheese-macwin.store");
		configuration.addAllowedOrigin("http://localhost:3000");
		configuration.addAllowedMethod("*"); // 모든 HTTP 메서드 허용
		configuration.addAllowedHeader("*"); // 모든 헤더 허용
		configuration.addExposedHeader("Authorization"); // Authorization 헤더 접근 허용
		configuration.setAllowCredentials(true); // 인증 정보 허용

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}
}
