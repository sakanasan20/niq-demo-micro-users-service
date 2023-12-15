package tw.niq.micro.model;

import java.time.LocalDateTime;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter 
@Setter 
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Builder
public class UserModel {
	
	@JsonIgnore
	private Long id;
	
	private Long version;

	private LocalDateTime createdDate;
	
	private LocalDateTime lastModifiedDate;
	
	private String userId;

	@NotBlank
	private String firstName;
	
	@NotBlank
	private String lastName;
	
	@NotBlank
	@Email
	private String email;
	
	@NotBlank
	@Size(max = 16)
	private String username;
	
	@NotBlank
	@Size(min = 8, max = 16)
	private String password;
	
	private Boolean accountNonExpired;

	private Boolean accountNonLocked;

	private Boolean credentialsNonExpired;

	private Boolean enabled;
	
	@ToString.Exclude
	@EqualsAndHashCode.Exclude
	private Set<RoleModel> roles;
	
}
