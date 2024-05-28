package org.webapp.gymfreaks.auth.model.dto.user;

import java.time.LocalDate;
import java.util.Set;

import org.webapp.gymfreaks.auth.model.Role;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserViewDto {

    private String userEmail;
    private String userPassword;
    private String userFirstName;
    private String userLastName;
    private String userPhoneNumber;
    private String userImage;
    private String userGender;
    private String userDescription;
    private Set<Role> userRoles;
    private LocalDate createdDate;
    private LocalDate updatedDate;

}
