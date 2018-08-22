package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ro.msg.edu.jbugs.bugManagement.business.control.BugManagement;
import ro.msg.edu.jbugs.bugsManagement.persistence.entity.Severity;
import ro.msg.edu.jbugs.bugManagement.business.dto.BugDTO;
import ro.msg.edu.jbugs.userManagement.business.dto.UserDTOHelper;
import ro.msg.edu.jbugs.userManagement.business.exceptions.BusinessException;
import ro.msg.edu.jbugs.userManagement.business.dto.UserDTO;
import ro.msg.edu.jbugs.userManagement.persistence.entity.User;

@WebServlet(urlPatterns = {"/TestServlet"})
public class TestServlet extends HttpServlet {

    @EJB
    private BugManagement bugManagement;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request  servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    protected void processRequest(final HttpServletRequest request, final HttpServletResponse response)
            throws ServletException, IOException {


        UserDTO userDTO = new UserDTO();
        userDTO.setFirstName("dorel");
        userDTO.setLastName("dorel");
        userDTO.setEmail("doreldorel@msggroup.com");
        userDTO.setPassword("Password");
        userDTO.setPhoneNumber("1234567890");
        userDTO.setUsername("Dorelut");
        UserDTO persistentUserDTO = null;

        UserDTOHelper userDTOHelper = new UserDTOHelper();
        User user = userDTOHelper.toEntity(userDTO);

        BugDTO bug = new BugDTO();
        bug.setTitle("title");
        bug.setDescription("descriptioooooooooooooo");
        bug.setVersion("vers");
        bug.setTargetDate("2020-05-11");
        bug.setStatus("1");
        bug.setFixedVersion("fVers");
        bug.setSeverity(Severity.LVL2);
        bug.setCreatedBy(user);
        bug.setAssignedTo(user);

        PrintWriter out = response.getWriter();


        try {
            bugManagement.createBug(bug);
            List<BugDTO> bugs = bugManagement.getAllBugs();
            for (BugDTO b:bugs){
                out.println(b);
            }
        } catch (BusinessException e) {
            e.printStackTrace();
        }


    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on
    // the + sign on the left to edit the code.">

    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request  servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doGet(final HttpServletRequest request, final HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request  servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doPost(final HttpServletRequest request, final HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}

