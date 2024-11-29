package com.jeeproject.Controller;

import com.jeeproject.DAO.CourseDAO;
import com.jeeproject.DAO.ProfessorDAO;
import com.jeeproject.DAO.RegistrationDAO;
import com.jeeproject.DAO.StudentDAO;
import com.jeeproject.Model.Course;
import com.jeeproject.Model.Professor;
import com.jeeproject.Model.Registration;
import com.jeeproject.Model.Student;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Date;
import java.util.List;

@WebServlet(name = "RegistrationController", value = "/RegistrationController")
public class RegistrationController extends HttpServlet {
    private RegistrationDAO registrationDAO;
    private ProfessorDAO professorDAO;
    private CourseDAO courseDAO;
    private StudentDAO studentDAO;

    @Override
    public void init() throws ServletException {
        registrationDAO = new RegistrationDAO();
        professorDAO = new ProfessorDAO();
        courseDAO = new CourseDAO();
        studentDAO = new StudentDAO();
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
                case "listByStudent":
                    listRegistrationsByStudent(request,response);
                    break;
                case "listByStudentWithCourses":
                    listRegistrationsByStudentWithCourses(request,response);
                    break;
                case "listByProfessor":
                    listRegistrationsByProfessor(request,response);
                    break;
                case "delete":
                    deleteRegistration(request,response);
                    break;
                case "add":
                    addRegistration(request,response);
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

    private void listRegistrationsByStudent(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Registration> registrationList = registrationDAO.getRegistrationsByStudent(1);
        request.setAttribute("registrations",registrationList);

        String destination = request.getParameter("destination");
        request.getRequestDispatcher(destination).forward(request, response);
    }


    private void deleteRegistration(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Registration registration = registrationDAO.getRegistrationById(id);
        registrationDAO.deleteRegistration(registration);
        response.sendRedirect(request.getContextPath() + "/views/student/RegistrationManagement.jsp");
    }

    private void listRegistrationsByStudentWithCourses(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        int studentId = 1;        //a remplacer par l'id selon session!!!!

        List<Course> coursesList = courseDAO.getAllCourses();
        request.setAttribute("courses",coursesList);

        List<Registration> registrationsListByStudentId = registrationDAO.getRegistrationsByStudent(studentId);
        request.setAttribute("registrations",registrationsListByStudentId);
        request.getRequestDispatcher(request.getParameter("destination")).forward(request, response);
    }

    private void addRegistration(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int courseId = Integer.parseInt(request.getParameter("courseId"));
        int studentId = 1; //a changer

        Course course = courseDAO.getCourseById(courseId);
        Student student = studentDAO.getStudentById(studentId);
        Date date = new Date();

        Registration registration = new Registration();
        registration.setCourse(course);
        registration.setStudent(student);
        registration.setRegistrationDate(date);

        registrationDAO.saveRegistration(registration);
        response.sendRedirect(request.getContextPath() + "/RegistrationController?action=listByStudent&destination=/views/student/RegistrationManagement.jsp");
    }

    private void listRegistrationsByProfessor(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Registration> registrations = registrationDAO.getRegistrationsByProfessor(3);
        request.setAttribute("registration",registrations);
        String destination = request.getParameter("destination");
        request.getRequestDispatcher(destination).forward(request, response);
    }

    }
