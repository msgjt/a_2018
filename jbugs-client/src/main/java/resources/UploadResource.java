package resources;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
import ro.msg.edu.jbugs.bugManagement.business.control.BugManagement;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.*;

@Path("/upload")
public class UploadResource {

    @EJB
    private BugManagement bugManagement;

    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response uploadFile(@DefaultValue("true") @FormDataParam("enabled") boolean enabled,
                               @FormDataParam("file") InputStream uploadedInputStream,
                               @FormDataParam("file") FormDataContentDisposition fileDetail){

        File objFile=new File(fileDetail.getFileName());
        saveToFile(uploadedInputStream, objFile);
        String output = "File uploaded to: " + objFile.getAbsolutePath();

        return Response.status(200).entity(output).build();

    }
    private void saveToFile(InputStream uploadedInputStream,
                            File uploadedFile) {

        try {
            OutputStream out = null;
            int read = 0;
            byte[] bytes = new byte[1024];


            out = new FileOutputStream(uploadedFile);
            while ((read = uploadedInputStream.read(bytes)) != -1) {
                out.write(bytes, 0, read);
            }
            out.flush();
            out.close();
        } catch (IOException e) {

            e.printStackTrace();
        }

    }
}
