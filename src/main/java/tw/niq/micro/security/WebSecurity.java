package tw.niq.micro.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import lombok.RequiredArgsConstructor;
import tw.niq.micro.service.UserService;

@EnableWebSecurity
@RequiredArgsConstructor
@Configuration
public class WebSecurity {
	
	public static final String TOKEN_PREFIX = "Bearer ";
	public static final String TOKEN_SECRET = "AaMckyLM973x9qPw9u5G7k7EYxYn0f0jP0FERRbwbsEEUKvpkYWudx57AJRgTbh3";
	public static final String TOKEN_EXPIRATION = "3600000";
	public static final String LOGIN_URL = "/api/v1/users/login";
	
	private final UserService userService;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;

	@Bean
	protected SecurityFilterChain configure(HttpSecurity http) throws Exception {
		
		AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
		authenticationManagerBuilder.userDetailsService(userService).passwordEncoder(bCryptPasswordEncoder);
		
		AuthenticationManager authenticationManager = authenticationManagerBuilder.build();
		
		CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter(authenticationManager);
		customAuthenticationFilter.setFilterProcessesUrl(LOGIN_URL);
		
		http
			.authenticationManager(authenticationManager)
			.addFilter(customAuthenticationFilter)
			.headers(headers -> headers.
					frameOptions(frameOptions -> frameOptions.disable()))
			.csrf(csrf -> csrf.disable())
			.sessionManagement((sessionManagement) -> sessionManagement
					.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
			.authorizeHttpRequests((authorize) -> authorize
					.anyRequest().permitAll());
		
		return http.build();
	}
}
