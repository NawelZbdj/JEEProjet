package com.jeeproject.Controller;

import com.jeeproject.DAO.AccountDAO;
import com.jeeproject.DAO.CourseDAO;
import com.jeeproject.DAO.StudentDAO;
import com.jeeproject.Model.Account;
import com.jeeproject.Model.Course;
import com.jeeproject.Model.Professor;
import com.jeeproject.Model.Student;
import com.jeeproject.Utils.AccountUtils.PasswordGenerator;
import com.jeeproject.Utils.AccountUtils.UsernameGenerator;
import com.jeeproject.Utils.EmailUtil;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@WebServlet(name = "StudentController", value = "/StudentController")
public class StudentController extends HttpServlet {
    private StudentDAO studentDAO;
    private AccountDAO accountDAO;
    private CourseDAO courseDAO;

    @Override
    public void init() throws ServletException {
        studentDAO = new StudentDAO();
        accountDAO = new AccountDAO();
        courseDAO = new CourseDAO();
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        String action = request.getParameter("action");

        try {
            switch (action) {
                case "list":
                    listStudents(request, response);
                    break;
                case "add":
                    showAddForm(request, response);
                    break;
                case "update":
                    showUpdateForm(request, response);
                    break;
                case "delete":
                    deleteStudent(request, response);
                    break;
                case "search":
                    searchStudents(request, response);
                    break;
                default:
                    listStudents(request, response);
                    break;
            }
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        String action = request.getParameter("action");

        try {
            switch (action) {
                case "add":
                    addStudent(request, response);
                    break;
                case "update":
                    updateStudent(request, response);
                    break;
                case "listByCourses":
                    listStudentsByCourse(request, response);
                    break;
                default:
                    break;
            }
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    private void listStudents(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Student> students = studentDAO.getAllStudents();
        request.setAttribute("students", students);
        request.getRequestDispatcher("/views/admin/StudentsManagement.jsp").forward(request, response);
    }

    private void addStudent(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String email = request.getParameter("email");
        Date birthDate;

        try {
            birthDate = new SimpleDateFormat("yyyy-MM-dd").parse(request.getParameter("birthDate"));
        } catch (ParseException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid date format.");
            return;
        }

        // Generate username and password
        String username = UsernameGenerator.generateUsername(firstName, lastName);
        String password = PasswordGenerator.generateRandomPassword();

        // Create and save the Account entity
        Account studentAccount = new Account();
        studentAccount.setUsername(username);
        studentAccount.setPassword(password);
        studentAccount.setRole("student");

        // Save Account
        accountDAO.saveAccount(studentAccount);

        // Create and associate the Student entity
        Student student = new Student();
        student.setFirstName(firstName);
        student.setLastName(lastName);
        student.setEmail(email);
        student.setBirthDate(birthDate);
        student.setAccount(studentAccount);

        // Save Student
        studentDAO.saveStudent(student);

        String subject = "sColartiY : Account created !";
        String body = "Welcome " + student.getFirstName() + ",\n\n" +
                "You are now part of our school. \n" +
                "Here are your connection details: \n\n" +
                "Username :" + studentAccount.getUsername()+
                "\nPassword :" + studentAccount.getPassword()+
                "\n\nBest Regards,\nAdmin staff.";

        try {
            EmailUtil.sendEmail(student.getEmail(), subject, body);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // Redirect to student management page
        response.sendRedirect("StudentController?action=list");
    }

    private void updateStudent(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Student student = studentDAO.getStudentById(id);

        if (student != null) {
            student.setFirstName(request.getParameter("firstName"));
            student.setLastName(request.getParameter("lastName"));
            student.setEmail(request.getParameter("email"));


            try {
                Date birthDate = new SimpleDateFormat("yyyy-MM-dd").parse(request.getParameter("birthDate"));
                student.setBirthDate(birthDate);
            } catch (ParseException e) {
                e.printStackTrace();
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid birth date format.");
                return;
            }

            studentDAO.updateStudent(student);
        }
        response.sendRedirect("StudentController?action=list");
    }

    private void deleteStudent(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        studentDAO.deleteStudent(id);
        response.sendRedirect("StudentController?action=list");
    }

    private void showAddForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/views/admin/AddStudent.jsp").forward(request, response);
    }

    private void showUpdateForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Student student = studentDAO.getStudentById(id);
        request.setAttribute("student", student);
        String destination = request.getParameter("destination");
        request.getRequestDispatcher(destination).forward(request,response);
    }

    private void searchStudents(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String keyword = request.getParameter("keyword");
        List<Student> students = studentDAO.searchStudents(keyword);
        request.setAttribute("students", students);
        request.getRequestDispatcher("/views/admin/StudentsManagement.jsp").forward(request, response);
    }


    private void listStudentsByCourse(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String courseId = request.getParameter("courseId");

        HttpSession session = request.getSession();
        Professor professor = (Professor)session.getAttribute("user");

        List<Student> students = studentDAO.getStudentsByCourseIdAndProfessorId(Integer.parseInt(courseId),professor.getId());

        request.setAttribute("students", students);

        Course course = courseDAO.getCourseById(Integer.parseInt(courseId));
        request.setAttribute("course", course);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/views/professor/ProfessorCourseStudents.jsp");
        dispatcher.forward(request, response);
    }
}
