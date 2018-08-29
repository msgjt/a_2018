package ro.msg.edu.jbugs.userManagement.business.dto;

import ro.msg.edu.jbugs.shared.persistence.util.CustomLogger;
import ro.msg.edu.jbugs.userManagement.persistence.dao.UserPersistenceManager;
import ro.msg.edu.jbugs.userManagement.persistence.entity.User;

import javax.ejb.EJB;
import javax.validation.constraints.NotNull;
import java.util.stream.Collectors;

public class UserDTOHelper {
    @EJB
    private UserPersistenceManager userPersistenceManager;

    public static UserDTO fromEntity(@NotNull User user) {

        UserDTO userDTO = new UserDTO();

        userDTO.setId(user.getId());
        userDTO.setFirstName(user.getFirstName());
        userDTO.setLastName(user.getLastName());
        userDTO.setEmail(user.getEmail());
        userDTO.setUsername(user.getUsername());
        userDTO.setPhoneNumber(user.getPhoneNumber());
        userDTO.setIsActive(user.getIsActive());
        userDTO.setRoles(user.getRoles()
                .stream().map(RoleDTOHelper::fromEntity).collect(Collectors.toList()));

        return userDTO;
    }

    public static User toEntity(@NotNull UserDTO userDTO,@NotNull User oldUser){

        oldUser.setFirstName(userDTO.getFirstName() != null ? userDTO.getFirstName() : oldUser.getFirstName());
        oldUser.setLastName(userDTO.getLastName() != null ? userDTO.getLastName() : oldUser.getLastName());
        oldUser.setEmail(userDTO.getEmail() != null ? userDTO.getEmail() : oldUser.getEmail());
        oldUser.setPassword(userDTO.getPassword() != null ? userDTO.getPassword() : oldUser.getPassword());
        oldUser.setUsername(userDTO.getUsername() != null ? userDTO.getUsername() : oldUser.getUsername());
        oldUser.setIsActive(userDTO.getIsActive() != null ? userDTO.getIsActive() : oldUser.getIsActive());
        oldUser.setPhoneNumber(userDTO.getPhoneNumber() != null ? userDTO.getPhoneNumber() : oldUser.getPhoneNumber());
        oldUser.setRoles(userDTO.getRoles() != null ?
                userDTO.getRoles().stream().map(RoleDTOHelper::toEntity).collect(Collectors.toList()) :
                oldUser.getRoles());

        return oldUser;
    }
}

