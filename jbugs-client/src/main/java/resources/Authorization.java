package resources;

import jwt.JwtManager;
import ro.msg.edu.jbugs.userManagement.business.control.UserManagement;
import ro.msg.edu.jbugs.userManagement.business.dto.UserDTO;
import ro.msg.edu.jbugs.userManagement.business.exceptions.BusinessException;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/authorize")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class Authorization  {

    @EJB
    private UserManagement userManagement;

    @POST
    public Response isAuthorized(UserDTO userDTO){
        try {
            UserDTO returnedUserDTO = userManagement.login(userDTO.getUsername(),userDTO.getPassword());
            if(returnedUserDTO != null) {
                String token= JwtManager.getInstance().createToken(userDTO.getUsername());
                String tokenComposed= "{ \"token\": \"" + token + "\" }";
                userManagement.addInLoggedUsers(userDTO.getUsername(),token);
                return Response.status(Response.Status.OK)
                        .entity(tokenComposed)
                        .build();
            }
            else {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                        .entity("No user with specified username and password")
                        .build();
            }
        } catch (BusinessException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(e.getMessage())
                    .build();
        }
    }


}
