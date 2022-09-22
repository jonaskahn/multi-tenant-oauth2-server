package io.github.tuyendev.auth.common.service.auth;

import io.github.tuyendev.auth.common.entity.rdb.Department;
import io.github.tuyendev.auth.common.entity.rdb.Organization;
import io.github.tuyendev.auth.common.security.SecuredUserDetails;
import io.github.tuyendev.auth.common.service.userinfo.UserInfoDto;
import io.github.tuyendev.auth.common.service.userinfo.UserInfoService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class JpaUserDetailsServiceTest {

    @Mock
    private UserInfoService userService;

    @InjectMocks
    private JpaUserDetailsService jpaUserDetailsService;

    @Test
    @DisplayName("Should throw an exception when the user is not found")
    void loadUserByPreferredUsernameWhenUserIsNotFoundThenThrowException() {
        String preferredUsername = "preferredUsername";
        UsernameNotFoundException usernameNotFoundException = new UsernameNotFoundException("user not found");
        when(userService.findActiveUserByPreferredUsername(preferredUsername)).thenThrow(usernameNotFoundException);
        try {
            jpaUserDetailsService.loadUserByPreferredUsername(preferredUsername);
            Assertions.fail();
        } catch (Exception e) {
            assertEquals("user not found", e.getMessage());
        }
    }

    @Test
    @DisplayName("Should return a secureduserdetails when the user is found")
    void loadUserByPreferredUsernameWhenUserIsFoundThenReturnSecuredUserDetails() {
        String preferredUsername = "preferredUsername";
        UserInfoDto userInfoDto =
                UserInfoDto.builder()
                        .id(1L)
                        .username("username")
                        .preferredUsername(UUID.randomUUID())
                        .email("email")
                        .emailVerified(true)
                        .phoneNumber("phoneNumber")
                        .phoneNumberVerified(true)
                        .password("password")
                        .familyName("familyName")
                        .middleName("middleName")
                        .givenName("givenName")
                        .name("name")
                        .unsignedName("unsignedName")
                        .enabled(true)
                        .locked(false)
                        .gender(1)
                        .birthdate(LocalDate.now())
                        .department(Department.builder().build())
                        .organization(Organization.builder().build())
                        .authorities(Set.of())
                        .build();

        when(userService.findActiveUserByPreferredUsername(preferredUsername))
                .thenReturn(userInfoDto);

        UserDetails userDetails =
                jpaUserDetailsService.loadUserByPreferredUsername(preferredUsername);

        assertNotNull(userDetails);
        assertTrue(userDetails instanceof SecuredUserDetails);

        verify(userService, times(1)).findActiveUserByPreferredUsername(preferredUsername);
    }

    @Test
    @DisplayName("Should throw an exception when the user is not found")
    void loadUserByUserIdWhenTheUserIsNotFoundThenThrowException() {
        Long userId = 1L;
        UsernameNotFoundException usernameNotFoundException = new UsernameNotFoundException("user not found");
        when(userService.findActiveUserById(userId)).thenThrow(usernameNotFoundException);
        try {
            jpaUserDetailsService.loadUserByUserId(userId);
            Assertions.fail();
        } catch (Exception e) {
            assertEquals("user not found", e.getMessage());
        }
    }

    @Test
    @DisplayName("Should return a secureduserdetails when the user is found")
    void loadUserByUserIdWhenTheUserIsFound() {
        Long userId = 1L;
        UserInfoDto userInfoDto =
                UserInfoDto.builder()
                        .id(userId)
                        .username("username")
                        .preferredUsername(UUID.randomUUID())
                        .email("email@gmail.com")
                        .emailVerified(true)
                        .phoneNumber("0987654321")
                        .phoneNumberVerified(true)
                        .password("password")
                        .familyName("familyName")
                        .middleName("middleName")
                        .givenName("givenName")
                        .name("name")
                        .unsignedName("unsignedName")
                        .enabled(true)
                        .locked(false)
                        .gender(1)
                        .birthdate(LocalDate.now())
                        .department(Department.builder().id(1L).name("department").build())
                        .organization(Organization.builder().id(1L).name("organization").build())
                        .authorities(Set.of())
                        .build();

        when(userService.findActiveUserById(userId)).thenReturn(userInfoDto);

        UserDetails userDetails = jpaUserDetailsService.loadUserByUserId(userId);

        assertNotNull(userDetails);
        assertTrue(userDetails instanceof SecuredUserDetails);

        verify(userService, times(1)).findActiveUserById(userId);
    }

    @Test
    @DisplayName("Should return a user when the principal is a username")
    void loadUserByUsernameWhenPrincipalIsUsername() {
        String principal = "username";
        UserInfoDto userInfoDto = UserInfoDto.builder().username(principal).build();
        when(userService.findUserByUsername(principal)).thenReturn(userInfoDto);

        UserDetails userDetails = jpaUserDetailsService.loadUserByUsername(principal);

        assertNotNull(userDetails);
        assertTrue(userDetails instanceof SecuredUserDetails);
        assertEquals(principal, userDetails.getUsername());
    }

    @Test
    @DisplayName("Should return a user when the principal is an email")
    void loadUserByUsernameWhenPrincipalIsEmail() {
        String principal = "test@gmail.com";
        UserInfoDto userInfoDto =
                UserInfoDto.builder().id(1L).username("test").email("test@gmail.com").build();
        when(userService.findUserByEmail(principal)).thenReturn(userInfoDto);

        UserDetails userDetails = jpaUserDetailsService.loadUserByUsername(principal);

        assertNotNull(userDetails);
        assertTrue(userDetails instanceof SecuredUserDetails);
        assertEquals(userInfoDto, ((SecuredUserDetails) userDetails).getUser());
    }
}