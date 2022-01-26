package com.codmain.order.converters;

import com.codmain.order.dtos.SingupRequestDTO;
import com.codmain.order.dtos.UserDTO;
import com.codmain.order.entity.User;

public class UserConverter extends AbstractConverter<User, UserDTO>{
    @Override
    public UserDTO fromEntity(User entity) {
        if (entity == null) return null;
        return UserDTO.builder()
                .id(entity.getId())
                .username(entity.getUsername())
                .build();
    }

    @Override
    public User fromDTO(UserDTO dto) {
        if (dto == null) return  null;
      return User.builder()
                .id(dto.getId())
                .username(dto.getUsername())
                .build();
    }

    public User signup(SingupRequestDTO dto){
        if (dto == null) return  null;
        return User.builder()
                .username(dto.getUsername())
                .password(dto.getPassword())
                .build();

    }
}
