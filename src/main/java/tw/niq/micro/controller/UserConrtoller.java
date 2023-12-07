package tw.niq.micro.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import tw.niq.micro.mapper.CycleAvoidingMappingContext;
import tw.niq.micro.mapper.UserMapper;
import tw.niq.micro.model.ReportModel;
import tw.niq.micro.model.UserModel;
import tw.niq.micro.service.UserService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/users")
public class UserConrtoller {

	private final UserService userService;
	private final UserMapper userMapper;
	private final CycleAvoidingMappingContext context;
	private final Environment environment;
	
	@GetMapping(
			path = "/check", 
			produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public String check() {
		String port = environment.getProperty("local.server.port");
		return "Users Service: running on port " + port + " ...";
	}
	
	@GetMapping(produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public List<UserModel> getUsers(
			@RequestParam(value = "page", defaultValue = "1", required = false) int page, 
			@RequestParam(value = "limit", defaultValue = "10", required = false) int limit) {
		
		return userService.getUsers(page, limit).stream()
				.map((userDto) -> userMapper.toUserModel(userDto, context))
				.collect(Collectors.toList());
	}
	
	@GetMapping(
			path = "/{userId}", 
			produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public UserModel getUser(@PathVariable("userId") String userId) {
		return userMapper.toUserModel(userService.getByUserId(userId), context);
	}
	
	@Transactional
	@PostMapping(
			consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE }, 
			produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<UserModel> createUser(@RequestBody @Valid UserModel userModel) {
		UserModel userCreated = userMapper.toUserModel(userService.create(userMapper.toUserDto(userModel, context)), context);
		return new ResponseEntity<>(userCreated, HttpStatus.CREATED);
	}
	
	@PutMapping(
			path = "/{userId}", 
			consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE }, 
			produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public UserModel updateUser(
			@PathVariable("userId") String userId, 
			@RequestBody UserModel userModel) {
		return userMapper.toUserModel(userService.update(userId, userMapper.toUserDto(userModel, context)), context);
	}
	
	@DeleteMapping(path = "/{userId}")
	public ResponseEntity<Void> deleteUser(@PathVariable("userId") String userId) {
		userService.deleteByUserId(userId);
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping(
			path = "/{userId}/reports", 
			produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public List<ReportModel> getUserReports(@PathVariable("userId") String userId) {
		return userService.getUserReports(userId);
	}
	
}
