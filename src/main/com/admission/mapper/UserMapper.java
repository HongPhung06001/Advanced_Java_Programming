package com.admission.mapper;

import com.admission.dto.UserDTO;
import com.admission.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper
public interface UserMapper {

    @Mappings({
        @Mapping(target = "studentId", source = "user.student.id")
    })
    UserDTO toUserDTO(User user);

}
