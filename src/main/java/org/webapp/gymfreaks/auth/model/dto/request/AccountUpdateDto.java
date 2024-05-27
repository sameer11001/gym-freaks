package org.webapp.gymfreaks.auth.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AccountUpdateDto {
    private String userEmail;
    private String userPassword;
    private String userFirstName;
    private String userLastName;
    private String userPhoneNumber;
    private String userImage;
    private String userGender;
    private String userDescription;
}
