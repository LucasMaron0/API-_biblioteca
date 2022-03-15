package compass.microservice.biblioteca.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import compass.microservice.biblioteca.modelos.Livro;

public interface LivrosRepository extends JpaRepository<Livro, Long> {

	Page<Livro> findByBiblioteca_id(Long id, Pageable paginacao);

	Page<Livro> findByAutorContainingIgnoreCase(String autor,  Pageable paginacao);

	Page<Livro> findByNomeContainingIgnoreCase(String nome, Pageable paginacao);

	List<Livro> findByNome(String nomeLivro);
}
