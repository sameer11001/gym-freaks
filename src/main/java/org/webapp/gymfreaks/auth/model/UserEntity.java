package org.webapp.gymfreaks.auth.model;

import java.util.Set;

import org.webapp.gymfreaks.core.model.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OrderColumn;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "user")
public class UserEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @NotBlank
    @Column(name = "user_email", unique = true, nullable = false)
    @Email
    private String userEmail;

    @NotBlank
    @Column(name = "user_password", nullable = false)
    private String userPassword;

    @NotBlank
    @Column(name = "user_first_name", nullable = false, length = 50)
    private String userFirstName;

    @Column(name = "user_last_name", length = 50)
    private String userLastName;

    // Use Integer instead of int for phone numbers
    @Column(name = "user_phone_number", unique = true)
    private String userPhoneNumber;

    @Column(name = "user_image")
    private String userImage;

    @Column(name = "user_gender")
    private String userGender; // Consider using an enum for gender

    @Column(name = "user_description")
    private String userDescription;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "sec_user_role", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "role_id"))
    @OrderColumn(name = "id")
    private Set<Role> userRoles;

}
