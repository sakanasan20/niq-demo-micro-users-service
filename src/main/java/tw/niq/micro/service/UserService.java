package tw.niq.micro.service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;

import tw.niq.micro.dto.UserDto;
import tw.niq.micro.model.ReportModel;

public interface UserService extends UserDetailsService {

	List<UserDto> getUsers(int page, int limit);

	UserDto getByUserId(String userId);

	UserDto create(UserDto userDto);

	UserDto update(String userId, UserDto userDto);

	void deleteByUserId(String userId);

	List<ReportModel> getUserReports(String userId);	
	
}
