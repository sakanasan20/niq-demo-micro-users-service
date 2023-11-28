package tw.niq.micro.bootstrap;

import java.util.UUID;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import tw.niq.micro.domain.Authority;
import tw.niq.micro.domain.Role;
import tw.niq.micro.domain.User;
import tw.niq.micro.repository.AuthorityRepository;
import tw.niq.micro.repository.RoleRepository;
import tw.niq.micro.repository.UserRepository;

@Profile("dev")
@RequiredArgsConstructor
@Component
public class DataLoader implements CommandLineRunner {

	private final UserRepository userRepository;
	private final RoleRepository roleRepository;
	private final AuthorityRepository authorityRepository;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Transactional
	@Override
	public void run(String... args) throws Exception {
		reloadTestAuthorities();
		reloadTestRoles();
		reloadTestUsers();
	}

	@Transactional
	private void reloadTestAuthorities() {
		if (authorityRepository.findByName("user.read").isEmpty()) {
			authorityRepository.saveAndFlush(Authority.builder().name("user.read").build());
		}
		if (authorityRepository.findByName("user.write").isEmpty()) {
			authorityRepository.saveAndFlush(Authority.builder().name("user.write").build());
		}
		if (authorityRepository.findByName("user.delete").isEmpty()) {
			authorityRepository.saveAndFlush(Authority.builder().name("user.delete").build());
		}
	}

	@Transactional
	private void reloadTestRoles() {
		
		Authority authorityUserRead = authorityRepository.findByName("user.read").orElseThrow(RuntimeException::new);
		Authority authorityUserWrite = authorityRepository.findByName("user.write").orElseThrow(RuntimeException::new);
		Authority authorityUserDelete = authorityRepository.findByName("user.delete").orElseThrow(RuntimeException::new);
		
		if (roleRepository.findByName("ADMIN").isEmpty()) {
			roleRepository.saveAndFlush(Role.builder()
					.name("ADMIN")
					.authority(authorityUserRead)
					.authority(authorityUserWrite)
					.authority(authorityUserDelete)
					.build());
		}
		if (roleRepository.findByName("USER").isEmpty()) {
			roleRepository.saveAndFlush(Role.builder()
					.name("USER")
					.authority(authorityUserRead)
					.authority(authorityUserWrite)
					.build());
		}
		if (roleRepository.findByName("GUEST").isEmpty()) {
			roleRepository.saveAndFlush(Role.builder()
					.name("GUEST")
					.authority(authorityUserRead)
					.build());
		}
	}

	@Transactional
	private void reloadTestUsers() {
		
		Role roleAdmin = roleRepository.findByName("ADMIN").orElseThrow(RuntimeException::new);
		Role roleUser = roleRepository.findByName("USER").orElseThrow(RuntimeException::new);
		Role roleGuest = roleRepository.findByName("GUEST").orElseThrow(RuntimeException::new);
		
		if (userRepository.findByUsername("admin").isEmpty()) {
			userRepository.saveAndFlush(User.builder()
					.userId(UUID.randomUUID().toString())
					.firstName("admin")
					.lastName("admin")
					.email("admin@niq.tw")
					.username("admin")
					.password(bCryptPasswordEncoder.encode("admin.secret"))
					.role(roleAdmin)
					.build());
		}
		
		if (userRepository.findByUsername("nick").isEmpty()) {
			userRepository.saveAndFlush(User.builder()
					.userId(UUID.randomUUID().toString())
					.firstName("nick")
					.lastName("chen")
					.email("nick.chen@niq.tw")
					.username("nick")
					.password(bCryptPasswordEncoder.encode("user.secret"))
					.role(roleUser)
					.build());
		}
		
		if (userRepository.findByUsername("guest").isEmpty()) {
			userRepository.saveAndFlush(User.builder()
					.userId(UUID.randomUUID().toString())
					.firstName("guest")
					.lastName("guest")
					.username("guest")
					.email("guest@niq.tw")
					.password(bCryptPasswordEncoder.encode("guest.secret"))
					.role(roleGuest)
					.build());
		}
	}

}
