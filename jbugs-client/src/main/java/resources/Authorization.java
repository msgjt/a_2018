package resources;

import jwt.JwtManager;
import ro.msg.edu.jbugs.shared.business.exceptions.CheckedBusinessException;
import ro.msg.edu.jbugs.shared.persistence.util.CustomLogger;
import ro.msg.edu.jbugs.userManagement.business.control.UserManagement;
import ro.msg.edu.jbugs.userManagement.business.dto.UserDTO;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/authorize")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class Authorization {

    @EJB
    private UserManagement userManagement;

    @POST
    public Response isAuthorized(UserDTO userDTO) throws CheckedBusinessException {
        CustomLogger.logEnter(this.getClass(), "isAuthorized", userDTO.toString());

        UserDTO returnedUserDTO = userManagement.login(userDTO.getUsername(), userDTO.getPassword());
        if (returnedUserDTO != null) {
            String token = JwtManager.getInstance().createToken(userDTO.getUsername());
            String username = userDTO.getUsername();
            String tokenComposed = "{ \"token\": \"" + token + "\"" + ",\"id\": " + returnedUserDTO.getId() + "}";
            userManagement.addInLoggedUsers(username, token);
            CustomLogger.logExit(this.getClass(), "isAuthorized", "OK");
            return Response.status(Response.Status.OK)
                    .entity(tokenComposed)
                    .build();
        } else {
            CustomLogger.logExit(this.getClass(), "isAuthorized", "INTERNAL_SERVER_ERROR");
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("No user with specified username and password")
                    .build();
        }

    }


}
