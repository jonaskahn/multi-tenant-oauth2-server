package io.github.tuyendev.auth.common.service.userinfo;

import com.naharoo.commons.mapstruct.MappingFacade;
import io.github.tuyendev.auth.common.entity.rdb.User;
import io.github.tuyendev.auth.common.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceInfoImplTest {

    @Mock
    private UserRepository userRepo;

    @Mock
    private MappingFacade mapper;

    @InjectMocks
    private UserServiceInfoImpl userServiceInfo;

    @Test
    @DisplayName("Should throw an exception when the user is not active")
    void findActiveUserByIdWhenUserIsNotActiveThenThrowException() {
        Long id = 1L;
        when(userRepo.findEnabledById(id)).thenReturn(Optional.empty());
        assertThrows(UsernameNotFoundException.class, () -> userServiceInfo.findActiveUserById(id));
    }

    @Test
    @DisplayName("Should return user info when the user is active")
    void findActiveUserByIdWhenUserIsActive() {
        Long id = 1L;
        User user =
                User.builder()
                        .id(id)
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
                        .enabled(true)
                        .locked(false)
                        .gender(1)
                        .birthdate(LocalDate.now())
                        .build();

        UserInfoDto userInfoDto =
                UserInfoDto.builder()
                        .id(id)
                        .username("username")
                        .preferredUsername(user.getPreferredUsername())
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
                        .build();

        when(userRepo.findEnabledById(id)).thenReturn(Optional.of(user));

        when(mapper.map(user, UserInfoDto.class)).thenReturn(userInfoDto);

        UserInfoDto result = userServiceInfo.findActiveUserById(id);

        assertEquals(userInfoDto, result);

        verify(userRepo, times(1)).findEnabledById(id);

        verifyNoMoreInteractions(userRepo);

        verify(mapper, times(1)).map(user, UserInfoDto.class);

        verifyNoMoreInteractions(mapper);
    }
}