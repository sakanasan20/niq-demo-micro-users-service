package tw.niq.micro.bootstrap;

import java.util.Arrays;
import java.util.UUID;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import tw.niq.micro.domain.User;
import tw.niq.micro.repository.UserRepository;

@RequiredArgsConstructor
@Component
public class DataLoader implements CommandLineRunner {

	private final UserRepository userRepository;
	
	@Override
	public void run(String... args) throws Exception {
		loadUsers();
	}

	@Transactional
	private void loadUsers() {
		if (userRepository.count() == 0) {
			userRepository.saveAll(Arrays.asList(
					User.builder().userId(UUID.randomUUID().toString()).firstName("John1").lastName("Doe1").email("John1.Doe1@niq.tw").encryptedPassword(UUID.randomUUID().toString()).build(), 
					User.builder().userId(UUID.randomUUID().toString()).firstName("John2").lastName("Doe2").email("John2.Doe2@niq.tw").encryptedPassword(UUID.randomUUID().toString()).build(), 
					User.builder().userId(UUID.randomUUID().toString()).firstName("John3").lastName("Doe3").email("John3.Doe3@niq.tw").encryptedPassword(UUID.randomUUID().toString()).build()
			));
		}
	}

}
