package resources;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
import ro.msg.edu.jbugs.bugManagement.business.control.BugManagement;
import ro.msg.edu.jbugs.bugManagement.business.dto.BugDTO;
import ro.msg.edu.jbugs.shared.business.exceptions.BusinessException;
import ro.msg.edu.jbugs.shared.business.exceptions.DetailedExceptionCode;
import ro.msg.edu.jbugs.shared.business.exceptions.ExceptionCode;
import ro.msg.edu.jbugs.shared.persistence.util.CustomLogger;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Path("/bugs")
public class BugResource {


    @EJB
    private BugManagement bugManagement;


    @GET
    public List<BugDTO> getBugs(){
        CustomLogger.logEnter(this.getClass(),"getBugs","");

        List<BugDTO> bugs = bugManagement.getAllBugs();

        CustomLogger.logExit(this.getClass(),"getBugs",bugs.toString());
        return bugs;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createBug(BugDTO bugDTO) {

        return Response.status(Response.Status.CREATED)
                .entity(bugManagement.createBug(bugDTO))
                .build();


    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateBug(BugDTO bugDTO) {
        CustomLogger.logEnter(this.getClass(), "updateBug", bugDTO.toString());

        BugDTO result = bugManagement.updateBug(bugDTO);

        CustomLogger.logExit(this.getClass(), "updateBug", result.toString());
        return Response.status(Response.Status.OK)
                .entity(result)
                .build();
    }

    @Path("/{id}")
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public BugDTO getBugById(@PathParam("id") Long id) {

        return bugManagement.getBugById(id);
    }

    @Path("/upload")
    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response uploadFile(@DefaultValue("true") @FormDataParam("enabled") boolean enabled,
                               @FormDataParam("file") InputStream uploadedInputStream,
                               @FormDataParam("file") FormDataContentDisposition fileDetail){

        File objFile=new File(fileDetail.getFileName());
        isValidForSaving(objFile);
        saveToFile(uploadedInputStream, objFile);
        String output = "File uploaded to: " + objFile.getAbsolutePath();

        return Response.status(Response.Status.CREATED).entity(output).build();

    }

    private void saveToFile(InputStream uploadedInputStream,
                            File uploadedFile) {

        try {
            OutputStream out = null;
            int read = 0;
            byte[] bytes = new byte[1024];
            long size = 0;

            out = new FileOutputStream(uploadedFile);
            while ((read = uploadedInputStream.read(bytes)) != -1) {
                size = size + bytes.length;
                if(size > 25000000){
                    throw new BusinessException(ExceptionCode.BUG_VALIDATION_EXCEPTION,DetailedExceptionCode.BUG_FILE_TOO_BIG);
                }
                out.write(bytes, 0, read);
            }
            out.flush();
            out.close();
        } catch (IOException e) {
            throw new BusinessException(ExceptionCode.BUG_VALIDATION_EXCEPTION,DetailedExceptionCode.BUG_COULD_NOT_WRITE_FILE);
        }

    }

    private boolean isValidForSaving(File file){
        String extension = "";

        int i = file.getName().lastIndexOf('.');
        if (i > 0) {
            extension = file.getName().substring(i+1);
        }
        List<String> validExtensions = new ArrayList<String>() {{
            add("jpg");
            add("png");
            add("pdf");
            add("doc");
            add("odf");
            add("xls");
            add("xlsx");
            add("txt");
            add("odt");
        }};
        if(!validExtensions.contains(extension)){
            throw new BusinessException(ExceptionCode.BUG_VALIDATION_EXCEPTION,DetailedExceptionCode.BUG_EXTENSION_NOT_VALID);
        }
        return true;
    }
}
