package compass.microservice.biblioteca.controller;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import compass.microservice.biblioteca.controller.dto.EncerrarPedidoDto;
import compass.microservice.biblioteca.repository.BibliotecaRepository;
import compass.microservice.biblioteca.repository.LivrosRepository;
import compass.microservice.biblioteca.repository.RegistroRepository;
import compass.microservice.biblioteca.service.BibliotecaService;

@RestController
@RequestMapping("/registro")
public class RegistroController {
	
	@Autowired
	private BibliotecaRepository bRepo;
	
	@Autowired
	private LivrosRepository lRepo;
	
	@Autowired
	private RegistroRepository rRepo;
	
	@Autowired
	private BibliotecaService bService;
	
	
	@PutMapping("/encerrar/{id}")
	@Transactional
	public ResponseEntity<?>  encerrarRegistro(@PathVariable Long id){
		
		 ResponseEntity<?>  encPedido = bService.encerrarPedido(id);
		
		return ResponseEntity.ok(encPedido);
	}
	
	

}
