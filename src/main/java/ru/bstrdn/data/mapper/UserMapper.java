package ru.bstrdn.data.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import ru.bstrdn.data.dto.User;
import ru.bstrdn.data.entity.UserEntity;

@Mapper(componentModel = "spring")
public interface UserMapper {

  UserEntity toEntity(User user);

  User toDto(UserEntity userEntity);

  void update(User user, @MappingTarget UserEntity userEntity);
}
