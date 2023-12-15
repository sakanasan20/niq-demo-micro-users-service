package tw.niq.micro.security;

import java.io.IOException;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CustomAuthorizationFilter extends BasicAuthenticationFilter {

	public CustomAuthorizationFilter(AuthenticationManager authenticationManager) {
		super(authenticationManager);
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

		if (authorizationHeader == null || !authorizationHeader.startsWith(WebSecurity.TOKEN_PREFIX)) {
			chain.doFilter(request, response);
			return;
		}

		String tokenToVerify = authorizationHeader.replace(WebSecurity.TOKEN_PREFIX, "");

		UsernamePasswordAuthenticationToken authentication = getAuthentication(tokenToVerify);

		SecurityContextHolder.getContext().setAuthentication(authentication);

		chain.doFilter(request, response);
	}

	private UsernamePasswordAuthenticationToken getAuthentication(String tokenToVerify) {

		JwtClaimParser jwtClaimParser = new JwtClaimParser(tokenToVerify, WebSecurity.TOKEN_SECRET);
		
		return new UsernamePasswordAuthenticationToken(jwtClaimParser.getSubject(), null, jwtClaimParser.getScope());
	}

}
