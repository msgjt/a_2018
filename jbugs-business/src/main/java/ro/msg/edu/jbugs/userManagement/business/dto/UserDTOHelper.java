package ro.msg.edu.jbugs.userManagement.business.dto;

import ro.msg.edu.jbugs.userManagement.persistence.entity.User;

import javax.validation.constraints.NotNull;
import java.util.stream.Collectors;

public class UserDTOHelper {

    /**
     * Converts a user to a userDTO
     *
     * @param user not null
     * @return the user entity
     */
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

    /**
     * Converts a userDTO to a user
     *
     * @param userDTO not null, contains the updated information
     * @param oldUser not null, IMPORTANT:
     *                FOR UPDATE: must be a detached user from the persistence layer, it has to role
     *                of a business guard for not allowing null fields (that must have non-null values)
     *                in the persistence layer
     *                FOR ADD: can be a new User entity
     * @return the user entity
     */
    public static User toEntity(@NotNull UserDTO userDTO, @NotNull User oldUser) {

        oldUser.setId(userDTO.getId() != null ? userDTO.getId() : oldUser.getId());
        oldUser.setFirstName(userDTO.getFirstName() != null && !userDTO.getFirstName().isEmpty() && userDTO.getFirstName()!="null" && userDTO.getFirstName()!=" "  ? userDTO.getFirstName() : oldUser.getFirstName());
        oldUser.setLastName(userDTO.getLastName() != null && !userDTO.getLastName().isEmpty()  && userDTO.getLastName()!="null" && userDTO.getLastName()!=" "  ? userDTO.getLastName() : oldUser.getLastName());
        oldUser.setEmail(userDTO.getEmail() != null && !userDTO.getEmail().isEmpty()  && userDTO.getEmail()!="null" && userDTO.getEmail()!=" " ? userDTO.getEmail() : oldUser.getEmail());
        oldUser.setPassword(userDTO.getPassword() != null && !userDTO.getPassword().isEmpty()  && userDTO.getPassword()!="null" && userDTO.getPassword()!=" "  ? userDTO.getPassword() : oldUser.getPassword());
        oldUser.setUsername(userDTO.getUsername() != null && !userDTO.getUsername().isEmpty()  && userDTO.getUsername()!="null" && userDTO.getUsername()!=" "  ? userDTO.getUsername() : oldUser.getUsername());
        oldUser.setIsActive(userDTO.getIsActive() != null ? userDTO.getIsActive() : oldUser.getIsActive());
        oldUser.setPhoneNumber(userDTO.getPhoneNumber() != null && !userDTO.getPhoneNumber().isEmpty()  && userDTO.getPhoneNumber()!="null" && userDTO.getPhoneNumber()!=" "  ? userDTO.getPhoneNumber() : oldUser.getPhoneNumber());
        oldUser.setRoles(userDTO.getRoles() != null && !userDTO.getRoles().isEmpty() ?
                userDTO.getRoles().stream().map(RoleDTOHelper::toEntity).collect(Collectors.toList()) :
                oldUser.getRoles());

        return oldUser;

    }
}

