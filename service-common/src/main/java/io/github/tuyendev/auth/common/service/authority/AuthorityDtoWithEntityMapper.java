package io.github.tuyendev.auth.common.service.authority;

import com.naharoo.commons.mapstruct.BidirectionalMapper;
import io.github.tuyendev.auth.common.entity.rdb.Authority;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AuthorityDtoWithEntityMapper extends BidirectionalMapper<AuthorityDto, Authority> {
}
