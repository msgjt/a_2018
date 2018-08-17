package servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ro.msg.edu.jbugs.userManagement.business.control.UserManagement;
import ro.msg.edu.jbugs.userManagement.business.exceptions.BusinessException;
import ro.msg.edu.jbugs.userManagement.business.dto.UserDTO;

@WebServlet(urlPatterns = {"/TestServlet"})
public class TestServlet extends HttpServlet {


    @EJB
    private UserManagement userManagement;

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
        UserDTO persistentUserDTO = null;

        UserDTO userDTO2 = new UserDTO();
        userDTO2.setFirstName("dorel");
        userDTO2.setLastName("dorel");
        userDTO2.setEmail("dordorel@msggroup.com");
        userDTO2.setPassword("Password");
        userDTO2.setPhoneNumber("1234567890");
//        try {
            //persistentUserDTO = userManagement.createUser(userDTO);
            //persistentUserDTO = userManagement.createUser(userDTO2);
        try {
            userManagement.deactivateUser("doreld");
        } catch (BusinessException e) {
            e.printStackTrace();
        }

//        } catch (BusinessException e) {
//            e.printStackTrace();
//        }
      /*  response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println(persistentUserDTO.toString());
        }*/

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

