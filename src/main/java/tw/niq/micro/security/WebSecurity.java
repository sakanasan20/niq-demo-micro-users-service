package tw.niq.micro.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import lombok.RequiredArgsConstructor;
import tw.niq.micro.service.UserService;

@EnableMethodSecurity(securedEnabled = true, prePostEnabled = true)
@EnableWebSecurity
@RequiredArgsConstructor
@Configuration
public class WebSecurity {
	
	@Value("${tw.niq.login.url}")
    private String loginUrl;
	
	@Value("${tw.niq.token.secret}")
    private String tokenSecret;
	
	@Value("${tw.niq.token.expiration}")
    private String tokenExpiration;
	
	public static String LOGIN_URL;
	public static String TOKEN_SECRET;
	public static String TOKEN_EXPIRATION;
	public static final String TOKEN_PREFIX = "Bearer ";
	
	private final UserService userService;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Value("${tw.niq.login.url}")
    public void setLoginUrl(String loginUrl) {
		WebSecurity.LOGIN_URL = loginUrl;
    }
	
	@Value("${tw.niq.token.secret}")
    public void setTokenSecret(String tokenSecret) {
		WebSecurity.TOKEN_SECRET = tokenSecret;
    }
	
	@Value("${tw.niq.token.expiration}")
    public void setTokenExpiration(String tokenExpiration) {
		WebSecurity.TOKEN_EXPIRATION = tokenExpiration;
    }

	@Bean
	protected SecurityFilterChain configure(HttpSecurity http) throws Exception {
		
		AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
		authenticationManagerBuilder.userDetailsService(userService).passwordEncoder(bCryptPasswordEncoder);
		
		AuthenticationManager authenticationManager = authenticationManagerBuilder.build();
		
		CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter(authenticationManager);
		customAuthenticationFilter.setFilterProcessesUrl(LOGIN_URL);
		
		CustomAuthorizationFilter customAuthorizationFilter = new CustomAuthorizationFilter(authenticationManager);
		
		http
			.authenticationManager(authenticationManager)
			.addFilter(customAuthenticationFilter)
			.addFilter(customAuthorizationFilter)
			.headers(headers -> headers.
					frameOptions(frameOptions -> frameOptions.disable()))
			.csrf(csrf -> csrf.disable())
			.sessionManagement((sessionManagement) -> sessionManagement
					.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
			.authorizeHttpRequests((authorize) -> authorize
					.requestMatchers(HttpMethod.POST, LOGIN_URL).permitAll()
					.requestMatchers(HttpMethod.GET, "/actuator/**").permitAll()
					.requestMatchers("/api/v1/users/**").permitAll()
					.anyRequest().authenticated());
		
		return http.build();
	}
}
