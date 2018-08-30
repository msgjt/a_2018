import ro.msg.edu.jbugs.shared.business.exceptions.CheckedBusinessException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class CheckedExceptionMapper implements ExceptionMapper<CheckedBusinessException> {

    @Override
    public Response toResponse(CheckedBusinessException e) {

        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();

    }
}
