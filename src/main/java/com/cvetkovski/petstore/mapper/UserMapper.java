package com.cvetkovski.petstore.mapper;

import com.cvetkovski.petstore.domain.User;
import com.cvetkovski.petstore.dto.response.UserResponse;
import org.mapstruct.Mapper;

import java.util.Collection;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @SuppressWarnings("unused")
    UserResponse userToUserResponse(User user);

    Collection<UserResponse> usersToUserResponses(Collection<User> users);
}
