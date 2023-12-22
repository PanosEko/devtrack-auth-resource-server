package com.panoseko.devtrack.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.*;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    @NotBlank(message = "name shouldn't be blank")
    private String fullName;
    @Email(message = "Invalid email address.")
    private String email;
    @NotBlank(message = "username shouldn't be blank")
    private String username;
    @NotBlank(message = "password shouldn't be blank")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$",
            message = "Password should contain at least 8 characters, one letter, one number and one special character.")
    private String password;

}
