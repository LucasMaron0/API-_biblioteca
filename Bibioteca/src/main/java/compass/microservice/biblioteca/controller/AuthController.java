package compass.microservice.biblioteca.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import compass.microservice.biblioteca.controller.dto.TokenDto;
import compass.microservice.biblioteca.controller.form.LoginForm;
import compass.microservice.biblioteca.security.TokenService;

@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	private AuthenticationManager  authMng; 
	
	@Autowired
	private TokenService tokenService;
	

	@PostMapping
	public 	ResponseEntity<TokenDto> autenticar (@RequestBody @Valid LoginForm form){
		UsernamePasswordAuthenticationToken dados = form.converter();
		
		try {
			Authentication authenticate = authMng.authenticate(dados); 
			String token = tokenService.gerarToken(authenticate);
			
			
			return  ResponseEntity.ok(new TokenDto(token, "Bearer"));
			
		} catch (AuthenticationException e) {
			return ResponseEntity.badRequest().build();
		}



	}
}	

