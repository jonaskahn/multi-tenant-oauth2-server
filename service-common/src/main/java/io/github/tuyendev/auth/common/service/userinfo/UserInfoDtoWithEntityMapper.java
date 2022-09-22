package io.github.tuyendev.auth.common.service.userinfo;

import com.naharoo.commons.mapstruct.BidirectionalMapper;
import io.github.tuyendev.auth.common.entity.rdb.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserInfoDtoWithEntityMapper extends BidirectionalMapper<UserInfoDto, User> {
}
