package tw.niq.micro.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDto {

	private String firstName;
	
	private String lastName;
	
	private String email;
	
	private String userId;
	
	private String username;
	
	private String password;
	
}
