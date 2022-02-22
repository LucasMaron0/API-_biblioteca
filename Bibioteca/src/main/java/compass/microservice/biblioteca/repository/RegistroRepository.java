package compass.microservice.biblioteca.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import compass.microservice.biblioteca.modelos.Livro;
import compass.microservice.biblioteca.modelos.Registro;

public interface RegistroRepository extends JpaRepository<Registro, Long> {

	Optional<Registro> findByIdUsuario(Long idUser);

}
