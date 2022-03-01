package compass.microservice.biblioteca.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import compass.microservice.biblioteca.modelos.Biblioteca;
import compass.microservice.biblioteca.modelos.Livro;

public interface LivrosRepository extends JpaRepository<Livro, Long> {

	Page<Livro> findByBiblioteca_id(Long id, Pageable paginacao);

	Optional<Livro> findByAutorContainingIgnoreCase(String autor);

	Optional<Livro> findByNomeContainingIgnoreCase(String nome);

	List<Livro> findByNome(String nomeLivro);
}
