package tw.niq.micro.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserModel {

	@NotBlank
	private String firstName;
	
	@NotBlank
	private String lastName;
	
	@NotBlank
	@Email
	private String email;
	
	private String userId;
	
	@NotBlank
	@Size(max = 16)
	private String username;
	
	@NotBlank
	@Size(min = 8, max = 16)
	private String password;
	
}
