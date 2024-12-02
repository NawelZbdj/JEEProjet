package com.jeeproject.Controller;

import com.jeeproject.DAO.*;
import com.jeeproject.Model.Course;
import com.jeeproject.Model.Registration;
import com.jeeproject.Model.Result;
import com.jeeproject.Model.Student;
import com.jeeproject.Utils.EmailUtil;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;


@WebServlet(name = "ResultController", value = "/ResultController")

public class ResultController extends HttpServlet {
    private ResultDAO resultDAO;
    private StudentDAO studentDAO;
    private CourseDAO courseDAO;
    private RegistrationDAO registrationDAO;

    @Override
    public void init() throws ServletException {
        resultDAO = new ResultDAO();
        studentDAO = new StudentDAO();
        courseDAO = new CourseDAO();
        registrationDAO = new RegistrationDAO();
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException{
        String action = request.getParameter("action");

        try{
            switch(action){
                case "listByStudent":
                    getResultsByStudentId(request,response);
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
                case "viewGrades":
                    viewGrades(request,response);
                    break;
                case "addGrade":
                    addGrade(request, response);
                    break;
                case "saveGrades":
                    saveGrades(request,response);
                    break;
                default :
                    break;
            }
        }catch(Exception e){
            throw new ServletException(e);
        }
    }

    private void getResultsByStudentId(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        Student student = (Student)session.getAttribute("user");
        List<Result> resultsList = resultDAO.getResultsByStudentId(student.getId());
        request.setAttribute("results",resultsList);

        String destination = request.getParameter("destination");
        request.getRequestDispatcher(destination).forward(request,response);
    }

    private void viewGrades(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{

        int studentId = Integer.parseInt(request.getParameter("studentId"));
        int courseId = Integer.parseInt(request.getParameter("courseId"));

        Student student = studentDAO.getStudentById(studentId);
        request.setAttribute("student", student);

        Course course = courseDAO.getCourseById(courseId);
        request.setAttribute("course", course);

        List<Result> results = resultDAO.getResultsByStudentAndCourse(studentId, courseId);
        request.setAttribute("results", results);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/views/professor/ProfessorStudentGrades.jsp");
        dispatcher.forward(request, response);
    }


    private void addGrade(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int studentId = Integer.parseInt(request.getParameter("studentId"));
        int courseId = Integer.parseInt(request.getParameter("courseId"));

        // Default values
        double grade = 0.0;
        double coefficient = 1.0;


        Result result = new Result();
        result.setGrade(grade);
        result.setCoefficient(coefficient);
        result.setRegistration(registrationDAO.getRegistrationByStudentAndCourse(studentId,courseId));

        resultDAO.saveResult(result);


        Student student = studentDAO.getStudentById(studentId);
        Course course = courseDAO.getCourseById(courseId);
        List<Result> results = resultDAO.getResultsByStudentAndCourse(studentId, courseId);

        request.setAttribute("student", student);
        request.setAttribute("course", course);
        request.setAttribute("results", results);

        //response.sendRedirect("ProfessorStudentGrades.jsp?studentId=" + studentId + "&courseId=" + courseId);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/views/professor/ProfessorStudentGrades.jsp?action=viewGrades&studentId=" + studentId + "&courseId=" + courseId);
        dispatcher.forward(request, response);
    }

    private void saveGrades(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int studentId = Integer.parseInt(request.getParameter("studentId"));
        int courseId = Integer.parseInt(request.getParameter("courseId"));

        String[] grades = request.getParameterValues("grades");
        String[] coefficients = request.getParameterValues("coefficients");
        String[] resultIds =  request.getParameterValues("resultIds");

        if (grades != null && coefficients != null && grades.length == coefficients.length) {

            for (int i = 0; i < grades.length; i++) {

                double grade = Double.parseDouble(grades[i]);
                double coefficient = Double.parseDouble(coefficients[i]);
                int resultId = Integer.parseInt(resultIds[i]);

                Registration registration = registrationDAO.getRegistrationByStudentAndCourse(studentId, courseId);

                if (registration != null) {

                    Result result = new Result();
                    result.setId(resultId);
                    result.setGrade(grade);
                    result.setCoefficient(coefficient);
                    result.setRegistration(registration);


                    resultDAO.updateResult(result);
                }

            }
        } else {

            throw new ServletException("Error.");
        }

        Student student = studentDAO.getStudentById(studentId);
        Course course = courseDAO.getCourseById(courseId);
        List<Result> results = resultDAO.getResultsByStudentAndCourse(studentId, courseId);



        request.setAttribute("student", student);
        request.setAttribute("course", course);
        request.setAttribute("results", results);

        //response.sendRedirect("ProfessorStudentGrades.jsp?action=viewGrades&studentId=" + studentId + "&courseId=" + courseId);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/views/professor/ProfessorStudentGrades.jsp?action=viewGrades&studentId=" + studentId + "&courseId=" + courseId);
        dispatcher.forward(request, response);
    }


}
