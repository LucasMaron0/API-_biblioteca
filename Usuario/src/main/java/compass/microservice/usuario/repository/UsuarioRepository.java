package compass.microservice.usuario.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import compass.microservice.usuario.modelo.Usuario;

public interface UsuarioRepository  extends JpaRepository<Usuario, Long>{

}
