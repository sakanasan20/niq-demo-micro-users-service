package tw.niq.micro.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tw.niq.micro.domain.Authority;

@Repository
public interface AuthorityRepository extends JpaRepository<Authority, Long> {

	Optional<Authority> findByName(String name);

	void deleteByName(String name);
	
}
