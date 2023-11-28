package tw.niq.micro.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

import lombok.RequiredArgsConstructor;

@EnableWebSecurity
@RequiredArgsConstructor
@Configuration
public class WebSecurity {
	

	@Bean
	protected SecurityFilterChain configure(HttpSecurity http) throws Exception {
		
		http
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
