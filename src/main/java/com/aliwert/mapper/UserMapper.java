package com.aliwert.mapper;

import com.aliwert.dto.DtoUser;
import com.aliwert.model.User;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper extends BaseMapper {

    @Mapping(target = "createTime", source = "createdTime", qualifiedByName = "dateToSqlDate")
    @Mapping(target = "password", ignore = true) // don't include password in DTO
    DtoUser toDto(User user);

    List<DtoUser> toDtoList(List<User> users);
}
