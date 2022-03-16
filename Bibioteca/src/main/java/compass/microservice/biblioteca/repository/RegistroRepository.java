package compass.microservice.biblioteca.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.RequestParam;

import compass.microservice.biblioteca.modelos.Registro;
import compass.microservice.biblioteca.modelos.StatusRegistro;

public interface RegistroRepository extends JpaRepository<Registro, Long> {



	Page<Registro> findByStatusRegistro(@RequestParam("status_registro") StatusRegistro statusRegistro, Pageable paginacao);

	List<Registro> findAllByIdUsuario(Long idUsuario);

	List<Registro> findByStatusRegistro(StatusRegistro emAndamento);
	
	Page<Registro> findByBiblioteca_id(Long id, Pageable paginacao);
	
	
}
