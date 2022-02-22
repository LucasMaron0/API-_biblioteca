package compass.microservice.biblioteca.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import compass.microservice.biblioteca.modelos.Biblioteca;
import compass.microservice.biblioteca.modelos.Livro;

public interface BibliotecaRepository extends JpaRepository<Biblioteca, Long> {
	
	
	
	

}
