package org.webapp.gymfreaks.auth.model.dto.request;

import jakarta.validation.constraints.Email;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequestDto {

    @Email(message = "Please enter valid e-mail address")
    @NotBlank(message = "E-mail can't be blank.")
    private String email;

    @NotBlank(message = "Password is required!")
    @Size(min = 8, message = "Password must have atleast 8 characters!")
    @Size(max = 20, message = "Password can have have atmost 20 characters!")
    private String password;

    @NotBlank(message = "First name can't be blank.")
    @Size(min = 2, max = 30, message = "Minimum first name length is 2 characters.")
    private String firstName;

    private String lastName;

    @NotBlank(message = "Phone number can't be blank.")
    @Size(min = 5, max = 16, message = "Minimum phone number length is 8 characters.")
    private String phoneNumber;

    @NotBlank
    private String gender;

    private String description;
}
