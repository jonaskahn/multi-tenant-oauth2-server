package io.github.tuyendev.auth.common.service.userinfo;

public interface UserInfoService {

    UserInfoDto findActiveUserById(final Long id);

    UserInfoDto findActiveUserByPreferredUsername(final String preferredUsername);

    UserInfoDto findUserByUsername(final String username);

    UserInfoDto findUserByEmail(final String email);
}
