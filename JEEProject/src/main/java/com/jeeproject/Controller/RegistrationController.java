package com.jeeproject.Controller;

import com.jeeproject.DAO.CourseDAO;
import com.jeeproject.DAO.ProfessorDAO;
import com.jeeproject.DAO.RegistrationDAO;
import com.jeeproject.DAO.StudentDAO;
import com.jeeproject.Model.Course;
import com.jeeproject.Model.Professor;
import com.jeeproject.Model.Registration;
import com.jeeproject.Model.Student;
import com.jeeproject.Utils.EmailUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

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
        //redirection to method by action

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
        //redirection to method by action

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
        //get all registration with their linked tables
        List<Registration> registrations = registrationDAO.getAllRegistrations();
        request.setAttribute("registrations",registrations);

        //redirect to the jps page base on the destination request parameter
        String destination = request.getParameter("destination");
        request.getRequestDispatcher(destination).forward(request,response);
    }

    private void updateManyRegistrations(HttpServletRequest request, HttpServletResponse response) throws IOException {

        //check if idProfessor exists
        if( request.getParameter("idProfessor") !=  null){
            int professorId = Integer.parseInt(request.getParameter("idProfessor"));
            if(professorId==0){
                //if no professor was assigned in the form
                response.sendRedirect(request.getContextPath() + "/RegistrationController?action=listall&destination=/views/admin/CourseAssignment.jsp");
                return;
            }
            //get professor by ID
            Professor professor = professorDAO.getProfessorById(professorId);

            //assign many registrations to a professor

            //all registrations to assign to the professor
            String[] registrationIds = request.getParameterValues("registrationsList");
            //update each registration one by one
            for(String registrationId : registrationIds){
                //find the registration to update
                Registration registration = registrationDAO.getRegistrationById(Integer.parseInt(registrationId));
                if(registration != null){
                    //update the professor and save
                    registration.setProfessor(professor);
                    registrationDAO.updateRegistration(registration);

                    //email the student to inform them about their teacher
                    Course course = registration.getCourse();
                    Student student = registration.getStudent();
                    String subject = "sColartiY :"+ course.getTitle() + "registration update.";
                    String body = "Hello " + student.getFirstName() + ",\n\n" +
                            "The registration is accepted. Your teacher will be "+ professor.getFirstName()+
                            " "+professor.getLastName()+
                            ".\n\nBest Regards,\nAdmin staff.";

                    try {
                        EmailUtil.sendEmail(student.getEmail(), subject, body);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        response.sendRedirect(request.getContextPath() + "/RegistrationController?action=listall&destination=/views/admin/CourseAssignment.jsp");
    }


    private void listRegistrationsAndProfessors(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //retrieve all registrations
        List<Registration> registrations = registrationDAO.getAllRegistrations();
        request.setAttribute("registrations", registrations);

        //retrieve all professors
        List<Professor> professors = professorDAO.getAllProfessors();
        request.setAttribute("professors", professors);

        request.getRequestDispatcher(request.getParameter("destination")).forward(request, response);
    }

    private void updateRegistration(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String destination = request.getParameter("destination");
        //check if idProfessor exists
        if (request.getParameter("idProfessor") != null) {
            //find the professor to assign
            int professorId = Integer.parseInt(request.getParameter("idProfessor"));
            Professor professor = professorDAO.getProfessorById(professorId);

            //find the registration to update
            int registrationId = Integer.parseInt(request.getParameter("registrationId"));
            Registration registration = registrationDAO.getRegistrationById(registrationId);

            //update and save
            registration.setProfessor(professor);
            registrationDAO.updateRegistration(registration);
        }

            //redirect to jsp page based on the destination request parameter
            if ("editAssign".equals(destination)) {
                response.sendRedirect(request.getContextPath() + "/RegistrationController?action=listall&destination=/views/admin/CourseAssignment.jsp");
            } else {
                request.getRequestDispatcher(destination).forward(request, response);
            }
    }

    private void listRegistrationsByStudent(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //get the current user's information
        HttpSession session = request.getSession();
        Student student = (Student)session.getAttribute("user");

        //get all their registrations
        List<Registration> registrationList = registrationDAO.getRegistrationsByStudent(student.getId());
        request.setAttribute("registrations",registrationList);

        //redirect to jsp page based on the destination request parameter
        String destination = request.getParameter("destination");
        request.getRequestDispatcher(destination).forward(request, response);
    }


    private void deleteRegistration(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //find registration by id
        int id = Integer.parseInt(request.getParameter("id"));
        Registration registration = registrationDAO.getRegistrationById(id);
        //delete the registration
        try{
            registrationDAO.deleteRegistration(registration);

        }catch (Exception e){
            response.sendRedirect(request.getContextPath() + "/views/student/RegistrationManagement.jsp");
        }
        response.sendRedirect(request.getContextPath() + "/views/student/RegistrationManagement.jsp");
    }

    private void listRegistrationsByStudentWithCourses(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        //get current user's information
        HttpSession session = request.getSession();
        Student student = (Student)session.getAttribute("user");

        //retrieve all courses
        List<Course> coursesList = courseDAO.getAllCourses();
        request.setAttribute("courses",coursesList);

        //find all current user's registrations
        List<Registration> registrationsListByStudentId = registrationDAO.getRegistrationsByStudent(student.getId());
        request.setAttribute("registrations",registrationsListByStudentId);

        //redirect to jsp page based on the destination request parameter
        request.getRequestDispatcher(request.getParameter("destination")).forward(request, response);
    }

    private void addRegistration(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //retrieve the course id
        int courseId = Integer.parseInt(request.getParameter("courseId"));

        //get all current user's information
        HttpSession session = request.getSession();
        Student user = (Student)session.getAttribute("user");

        //get Course and Student by id
        Course course = courseDAO.getCourseById(courseId);
        Student student = studentDAO.getStudentById(user.getId());

        Date date = new Date();
        //create a new registration
        Registration registration = new Registration();
        registration.setCourse(course);
        registration.setStudent(student);
        registration.setRegistrationDate(date);
        //save in the database
        registrationDAO.saveRegistration(registration);
        response.sendRedirect(request.getContextPath() + "/RegistrationController?action=listByStudent&destination=/views/student/RegistrationManagement.jsp");
    }

    private void listRegistrationsByProfessor(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //get all current user's information
        HttpSession session = request.getSession();
        Professor professor = (Professor)session.getAttribute("user");

        //find all current user's registrations
        List<Registration> registrations = registrationDAO.getRegistrationsByProfessor(professor.getId());
        request.setAttribute("registration",registrations);
        //redirect to jsp based on the destination request parameter
        String destination = request.getParameter("destination");
        request.getRequestDispatcher(destination).forward(request, response);
    }

    }
