package tw.niq.micro.security;

import java.util.Base64;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JwtClaimParser {
	
	private Jwt<?, ?> jwtObject;

	public JwtClaimParser(String jwt, String tokenSecret) {
		this.jwtObject = parseJwt(jwt, tokenSecret);
	}

	private Jwt<?, ?> parseJwt(String jwt, String tokenSecret) {
		
		byte[] tokenSecretBytes = Base64.getEncoder().encode(tokenSecret.getBytes());
		SecretKey secretKey = new SecretKeySpec(tokenSecretBytes, SignatureAlgorithm.HS512.getJcaName());
		
		JwtParser jwtParser = Jwts.parserBuilder()
				.setSigningKey(secretKey)
				.build();
		
		return jwtParser.parse(jwt);
	}
	
	public Collection<? extends GrantedAuthority> getScope() {
		
		Collection<Map<String, String>> scopes = ((Claims) jwtObject.getBody()).get("scope", List.class);
		
		return scopes.stream()
				.map(scope -> new SimpleGrantedAuthority(scope.get("authority")))
				.collect(Collectors.toSet());
	}
	
	public String getSubject() {
		
		return ((Claims) jwtObject.getBody()).getSubject();
	}
	
}
