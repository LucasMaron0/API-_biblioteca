package compass.microservice.biblioteca.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import compass.microservice.biblioteca.modelos.Biblioteca;
import compass.microservice.biblioteca.modelos.Livro;

public interface LivrosRepository extends JpaRepository<Livro, Long>  {
	
	Page<Livro> findByBiblioteca_id(Long id, Pageable paginacao);

}
