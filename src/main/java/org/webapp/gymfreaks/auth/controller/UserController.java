package org.webapp.gymfreaks.auth.controller;

import org.springframework.web.bind.annotation.RestController;
import org.webapp.gymfreaks.auth.error.UserNotFoundException;
import org.webapp.gymfreaks.auth.mapper.UserMapper;
import org.webapp.gymfreaks.auth.model.UserEntity;
import org.webapp.gymfreaks.auth.model.dto.request.AccountUpdateDto;
import org.webapp.gymfreaks.auth.model.dto.user.UserViewDto;
import org.webapp.gymfreaks.auth.service.AuthService;
import org.webapp.gymfreaks.auth.service.RoleService;
import org.webapp.gymfreaks.auth.service.UserService;
import org.webapp.gymfreaks.core.error.RunTimeException;
import org.webapp.gymfreaks.core.model.dto.CustomApiResponse;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/v1/user")
@Tag(name = "User Controller", description = "User mangement API")
public class UserController {

    @Autowired
    AuthService accountService;

    @Autowired
    RoleService roleService;

    @Autowired
    UserMapper userMapper;

    @Autowired
    UserService userService;

    public UserController(AuthService accountService) {
        this.accountService = accountService;
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/get_all")
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "View all profile successfully"),
            @ApiResponse(responseCode = "404", description = "User Not Found"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    public CustomApiResponse<List<UserViewDto>> getUsers() {

        try {
            List<UserEntity> accounts = accountService.findAll();
            List<UserViewDto> accountsDto = new ArrayList<>();
            for (UserEntity account : accounts) {
                UserViewDto accountDto = userMapper.toDto(account);
                accountsDto.add(accountDto);
            }
            return CustomApiResponse.successOf(accountsDto, "Get all profile successfully", null);
        } catch (Exception e) {
            throw new UserNotFoundException();
        }
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ApiResponse(responseCode = "200", description = "view profile")
    public CustomApiResponse<UserViewDto> getUserById(
            @Parameter(description = "ID of employee to be retrieve the profile", required = true) @PathVariable Long id) {
        try {
            UserEntity account = accountService.findById(id);
            UserViewDto accountDto = userMapper.toDto(account);
            return CustomApiResponse.successOf(accountDto, "Get profile successfully", null);
        } catch (Exception e) {
            throw new UserNotFoundException();
        }
    }

    @PutMapping("/update/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @ApiResponse(responseCode = "202", description = "Update profile successfully")
    public CustomApiResponse<UserViewDto> updateUserById(@Valid @RequestBody AccountUpdateDto requestBody,
            @Parameter(description = "ID of employee to be update the profile", required = true) @PathVariable Long id) {
        try {
            UserEntity entity = userService.findById(id);

            UserEntity account = userMapper.updateDtoToEntity(requestBody, entity);

            return CustomApiResponse.successOf(userMapper.toDto(userService.updateById(id, account)),
                    "Update profile successfully", HttpStatus.ACCEPTED);
        } catch (RuntimeException e) {
            throw new RunTimeException(e.toString());
        }
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiResponse(responseCode = "204", description = "Delete profile successfully")
    public CustomApiResponse<Void> deleteUserById(
            @Parameter(description = "ID of employee to be delete", required = true) @PathVariable String id) throws RuntimeException {

        try {
            accountService.deleteById(Long.parseLong(id));
            return CustomApiResponse.successOf(null, "Delete profile successfully", HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            throw new RunTimeException(e.toString());
        }
    }

}
