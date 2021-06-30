package com.nbs.dto;
import java.io.Serializable;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto implements Serializable {
	private String id;
	private String name;
	@NotBlank(message="* please enter emailid")
	@Email(message = "* please enter valid email id")
	private String email;
	@NotBlank(message = "* please enter password")
	private String password;
	private String type;
	private String created;
	private String updated;
}
