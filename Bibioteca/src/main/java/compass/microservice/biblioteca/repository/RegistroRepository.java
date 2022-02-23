package compass.microservice.biblioteca.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.RequestParam;

import compass.microservice.biblioteca.modelos.Livro;
import compass.microservice.biblioteca.modelos.Registro;
import compass.microservice.biblioteca.modelos.StatusRegistro;

public interface RegistroRepository extends JpaRepository<Registro, Long> {

	Optional<Registro> findByIdUsuario(Long idUser);

	Page<Registro> findByStatusRegistro(StatusRegistro statusRegistro, Pageable paginacao);

}
