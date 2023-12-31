package tw.niq.micro.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tw.niq.micro.domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	Optional<User> findByUserId(String userId);
	
	Optional<User> findByUsername(String username);
	
	Optional<User> findByEmail(String email);
	
	void deleteByUserId(String userId);

	

}
