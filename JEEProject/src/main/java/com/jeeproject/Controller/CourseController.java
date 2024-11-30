package com.jeeproject.Controller;

import com.jeeproject.DAO.CourseDAO;
import com.jeeproject.Model.Course;
import com.jeeproject.Model.Student;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "CourseController", value = "/CourseController")
public class CourseController extends HttpServlet {
    private CourseDAO courseDAO;

    @Override
    public void init() throws ServletException{
        courseDAO = new CourseDAO();
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException{
        String action = request.getParameter("action");

        try{
            switch(action){
                case "list":
                    listCourses(request,response);
                    break;
                case "listByProfessor":
                    listCoursesByProfessor(request,response);
                    break;
                case "edit":
                    showEditList(request,response);
                    break;
                case "delete":
                    deleteCourse(request,response);
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
                case "update":
                    updateCourse(request,response);
                    break;
                case "save":
                    saveCourse(request,response);
                    break;
                default :
                    break;
            }
        }catch(Exception e){
            throw new ServletException(e);
        }
    }

    private void listCourses(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Course> courses = courseDAO.getAllCourses();
        request.setAttribute("courses",courses);

        String destination = request.getParameter("destination");
        request.getRequestDispatcher(destination).forward(request,response);
    }

    private void showEditList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Course course = courseDAO.getCourseById(id);
        if(course != null){
            request.setAttribute("course",course);
            request.getRequestDispatcher("/views/admin/EditCourse.jsp").forward(request,response);
        } else {
            response.sendRedirect(request.getContextPath() + "/CourseController?action=list&destination=/views/admin/SubjectManagement.jsp");
        }
    }

    private void updateCourse(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        String title = request.getParameter("title");
        String description = request.getParameter("description");
        double credit = Double.parseDouble(request.getParameter("credit"));
        String speciality = request.getParameter("speciality");

        Course updatedCourse= new Course();
        updatedCourse.setId(id);
        updatedCourse.setCredit(credit);
        updatedCourse.setTitle(title);
        updatedCourse.setDescription(description);
        updatedCourse.setSpeciality(speciality);

        courseDAO.updateCourse(updatedCourse);
        response.sendRedirect(request.getContextPath() + "/views/admin/SubjectManagement.jsp");
    }


    private void saveCourse(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String title = request.getParameter("title");
        String description = request.getParameter("description");
        double credit = Double.parseDouble(request.getParameter("credit"));
        String speciality = request.getParameter("speciality");

        Course newCourse= new Course();
        newCourse.setCredit(credit);
        newCourse.setTitle(title);
        newCourse.setDescription(description);
        newCourse.setSpeciality(speciality);

        courseDAO.saveCourse(newCourse);
        response.sendRedirect(request.getContextPath() + "/views/admin/SubjectManagement.jsp");
    }

    private void deleteCourse(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Course course = courseDAO.getCourseById(id);
        courseDAO.deleteCourse(course);
        response.sendRedirect(request.getContextPath() + "/views/admin/SubjectManagement.jsp");
    }

    private void listCoursesByProfessor(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Ici faut recup l'ID du prof ( parametre session I guess)
        List<Course> coursesList = courseDAO.getCoursesByProfessorId(1);
        request.setAttribute("coursesList",coursesList);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/views/professor/ProfessorCourses.jsp");
        dispatcher.forward(request, response);
    }

}
