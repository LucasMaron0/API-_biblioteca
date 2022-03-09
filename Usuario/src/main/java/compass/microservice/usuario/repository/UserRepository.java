package compass.microservice.usuario.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import compass.microservice.usuario.modelo.User;

public interface UserRepository extends JpaRepository<User, Long>{
	Optional<User> findByEmail(String email);
}
