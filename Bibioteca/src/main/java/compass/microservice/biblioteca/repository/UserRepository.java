package compass.microservice.biblioteca.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import compass.microservice.biblioteca.modelos.User;

public interface UserRepository extends JpaRepository<User, Long>{
	Optional<User> findByEmail(String email);
}


