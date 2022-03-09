package compass.microservice.usuario.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import compass.microservice.usuario.modelo.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Configuration
@Service
public class TokenService {

	private String expiration = "86400000";

	private String secret = "dsabebiwbjeibewqbiebq";

	public String gerarToken(Authentication authentication) {
		User logado = (User) authentication.getPrincipal();
		Date hoje = new Date();
		Date dataExpiracao = new Date(hoje.getTime() + Long.parseLong(expiration));
		
		Collection<? extends GrantedAuthority> authorities = logado.getAuthorities();

		List<String> roles = new ArrayList<String>();
		
		for(GrantedAuthority authority : authorities) {
			roles.add(authority.getAuthority());
			System.out.println(authority.getAuthority());
		}

		return Jwts.builder()
				.setIssuer("API Biblioteca")
				.setSubject(logado.getId().toString())
				.setIssuedAt(hoje)
				.setExpiration(dataExpiracao)
				.claim("Roles", roles)
				.signWith(SignatureAlgorithm.HS256, secret)
				.compact();
	}

	public boolean isTokenValido(String token) {
		try {
			Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public Long getIdUsuario(String token) {
		Claims claims = Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token).getBody();
		return Long.parseLong(claims. getSubject());
	}
}