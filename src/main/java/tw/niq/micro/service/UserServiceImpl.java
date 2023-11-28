package tw.niq.micro.service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import tw.niq.micro.domain.User;
import tw.niq.micro.dto.UserDto;
import tw.niq.micro.exception.UserServiceException;
import tw.niq.micro.mapper.CycleAvoidingMappingContext;
import tw.niq.micro.mapper.UserMapper;
import tw.niq.micro.repository.UserRepository;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
	
	private final UserRepository userRepository;
	private final UserMapper userMapper;
	private final CycleAvoidingMappingContext context;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return userRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException(username));
	}
	
	@Override
	public List<UserDto> getUsers(int page, int limit) {
		if (page > 0) page = page - 1;
		Pageable pageable = PageRequest.of(page, limit);
		return userRepository.findAll(pageable).get()
				.map(user -> userMapper.toUserDto(user, context))
				.collect(Collectors.toList());
	}

	@Override
	public UserDto getByUserId(String userId) {
		return userRepository.findByUserId(userId)
				.map(user -> userMapper.toUserDto(user, context))
				.orElseThrow(() -> new UserServiceException());
	}

	@Transactional
	@Override
	public UserDto create(UserDto userDto) {
		User user = userMapper.toUser(userDto, context);
		user.setUserId(UUID.randomUUID().toString());
		user.setPassword(userDto.getPassword());
		User userSaved = userRepository.save(user);
		return userMapper.toUserDto(userSaved, context);
	}

	@Transactional
	@Override
	public UserDto update(String userId, UserDto userDto) {
		User user = userRepository.findByUserId(userId).orElseThrow(() -> new UserServiceException());
		if(userDto.getFirstName() != null && !userDto.getFirstName().equals(user.getFirstName())) user.setFirstName(userDto.getFirstName());
		if(userDto.getLastName() != null && !userDto.getLastName().equals(user.getLastName())) user.setLastName(userDto.getLastName());
		if(userDto.getEmail() != null && !userDto.getEmail().equals(user.getEmail())) user.setEmail(userDto.getEmail());
		User userSaved = userRepository.save(user);
		return userMapper.toUserDto(userSaved, context);
	}

	@Transactional
	@Override
	public void deleteByUserId(String userId) {
		userRepository.deleteByUserId(userId);
	}

}
