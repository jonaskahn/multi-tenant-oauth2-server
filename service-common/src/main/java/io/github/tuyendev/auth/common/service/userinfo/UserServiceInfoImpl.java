package io.github.tuyendev.auth.common.service.userinfo;

import com.naharoo.commons.mapstruct.MappingFacade;
import io.github.tuyendev.auth.common.entity.rdb.User;
import io.github.tuyendev.auth.common.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceInfoImpl implements UserInfoService {

    private final UserRepository userRepo;

    private final MappingFacade mapper;

    @Override
    public UserInfoDto findActiveUserById(Long id) {
        User user = userRepo.findEnabledById(id)
                .orElseThrow(() -> new UsernameNotFoundException("Not found user with given id"));
        return mapper.map(user, UserInfoDto.class);
    }

    @Override
    public UserInfoDto findActiveUserByPreferredUsername(String preferredUsername) {
        User user = userRepo.findEnabledByPreferredUsername(UUID.fromString(preferredUsername))
                .orElseThrow(() -> new UsernameNotFoundException("Not found user with given preferredUsername"));
        return mapper.map(user, UserInfoDto.class);
    }

    @Override
    public UserInfoDto findUserByUsername(String username) {
        User user = userRepo.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Not found user with given username"));
        return mapper.map(user, UserInfoDto.class);
    }

    @Override
    public UserInfoDto findUserByEmail(String email) {
        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Not found user with given email"));
        return mapper.map(user, UserInfoDto.class);
    }
}
