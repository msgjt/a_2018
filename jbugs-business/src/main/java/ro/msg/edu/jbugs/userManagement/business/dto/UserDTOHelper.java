package ro.msg.edu.jbugs.userManagement.business.dto;

import ro.msg.edu.jbugs.userManagement.persistence.entity.User;

import java.util.stream.Collectors;

public class UserDTOHelper {


    public static UserDTO fromEntity(User user){
        UserDTO userDTO = new UserDTO();

        userDTO.setId(user.getId());
        userDTO.setFirstName(user.getFirstName());
        userDTO.setLastName(user.getLastName());
        userDTO.setEmail(user.getEmail());
        userDTO.setUsername(user.getUsername());
        userDTO.setPhoneNumber(user.getPhoneNumber());
        userDTO.setIsActive(user.getIsActive());
        userDTO.setRoleDTOS(user.getRoles()
                .stream().map(RoleDTOHelper::fromEntity).collect(Collectors.toList()));

        return userDTO;
    }

    public static User toEntity(UserDTO userDTO){
        User user = new User();

        user.setId(userDTO.getId());
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        user.setUsername(userDTO.getUsername());
        user.setIsActive(userDTO.getIsActive());
        user.setPhoneNumber(userDTO.getPhoneNumber());
        user.setRoles(userDTO.getRoleDTOS()
                .stream().map(RoleDTOHelper::toEntity).collect(Collectors.toList()));
        return user;

    }
}

