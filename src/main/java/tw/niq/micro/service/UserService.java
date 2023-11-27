package tw.niq.micro.service;

import java.util.List;

import tw.niq.micro.dto.UserDto;

public interface UserService {

	List<UserDto> getUsers(int page, int limit);

	UserDto getByUserId(String userId);

	UserDto create(UserDto userDto);

	UserDto update(String userId, UserDto userDto);

	void deleteByUserId(String userId);	
	
}
