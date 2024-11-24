package com.jeeproject.Controller;

import com.jeeproject.DAO.CourseDAO;
import com.jeeproject.DAO.ProfessorDAO;
import com.jeeproject.DAO.RegistrationDAO;
import com.jeeproject.Model.Course;
import com.jeeproject.Model.Professor;
import com.jeeproject.Model.Registration;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "RegistrationController", value = "/RegistrationController")
public class RegistrationController extends HttpServlet {
    private RegistrationDAO registrationDAO;
    private ProfessorDAO professorDAO;

    @Override
    public void init() throws ServletException {
        registrationDAO = new RegistrationDAO();
        professorDAO = new ProfessorDAO();
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException{
        String action = request.getParameter("action");

        try{
            switch(action){
                case "list":
                    listRegistrations(request,response);
                    break;
                case "listall":
                    listRegistrationsAndProfessors(request,response);
                    break;
                default :
                    break;
            }
        }catch(Exception e){
            throw new ServletException(e);
        }
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException{
        String action = request.getParameter("action");

        try{
            switch(action){
                case "multiupdate":
                    updateManyRegistrations(request,response);
                    break;
                case "update":
                    updateRegistration(request,response);
                    break;
                default :
                    break;
            }
        }catch(Exception e){
            throw new ServletException(e);
        }
    }

    private void listRegistrations(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Registration> registrations = registrationDAO.getAllRegistrations();
        request.setAttribute("registrations",registrations);

        String destination = request.getParameter("destination");
        request.getRequestDispatcher(destination).forward(request,response);
    }

    private void updateManyRegistrations(HttpServletRequest request, HttpServletResponse response) throws IOException {

        if( request.getParameter("idProfessor") !=  null){
            int professorId = Integer.parseInt(request.getParameter("idProfessor"));
            Professor professor = professorDAO.getProfessorById(professorId);

            String[] registrationIds = request.getParameterValues("registrationsList");
            for(String registrationId : registrationIds){
                Registration registration = registrationDAO.getRegistrationById(Integer.parseInt(registrationId));
                if(registration != null){
                    registration.setProfessor(professor);
                    registrationDAO.updateRegistration(registration);
                }
            }
        }
        response.sendRedirect(request.getContextPath() + "/RegistrationController?action=listall&destination=/views/admin/CourseAssignment.jsp");

    }


    private void listRegistrationsAndProfessors(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Registration> registrations = registrationDAO.getAllRegistrations();
        request.setAttribute("registrations", registrations);

        List<Professor> professors = professorDAO.getAllProfessors();
        request.setAttribute("professors", professors);

        request.getRequestDispatcher(request.getParameter("destination")).forward(request, response);
    }

    private void updateRegistration(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String destination = request.getParameter("destination");
        if (request.getParameter("idProfessor") != null) {
            int professorId = Integer.parseInt(request.getParameter("idProfessor"));
            Professor professor = professorDAO.getProfessorById(professorId);

            int registrationId = Integer.parseInt(request.getParameter("registrationId"));
            Registration registration = registrationDAO.getRegistrationById(registrationId);

            registration.setProfessor(professor);
            registrationDAO.updateRegistration(registration);
        }

            if ("editAssign".equals(destination)) {
                response.sendRedirect(request.getContextPath() + "/RegistrationController?action=listall&destination=/views/admin/CourseAssignment.jsp");
            } else {
                request.getRequestDispatcher(destination).forward(request, response);
            }

    }




}
