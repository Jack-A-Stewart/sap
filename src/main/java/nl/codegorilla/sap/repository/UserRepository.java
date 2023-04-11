package nl.codegorilla.sap.repository;

import java.util.Optional;

import nl.codegorilla.sap.model.authentication.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByEmail(String email);

}
