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

        //redirect to method based on the action request parameter
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

        //redirect to method based on the action request parameter
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
        //get all students
        List<Student> students = studentDAO.getAllStudents();
        //save as a request attribute
        request.setAttribute("students", students);
        //redirect to StudentsManagement.jsp
        request.getRequestDispatcher("/views/admin/StudentsManagement.jsp").forward(request, response);
    }

    private void addStudent(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //retrieve all request parameters
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String email = request.getParameter("email");
        Date birthDate;

        //try to format the birthdate
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

        //email to the student
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
        //get student by id from the request parameter
        int id = Integer.parseInt(request.getParameter("id"));
        Student student = studentDAO.getStudentById(id);

        if (student != null) {
            //update information
            student.setFirstName(request.getParameter("firstName"));
            student.setLastName(request.getParameter("lastName"));
            student.setEmail(request.getParameter("email"));

            //try to format the birthdate
            try {
                Date birthDate = new SimpleDateFormat("yyyy-MM-dd").parse(request.getParameter("birthDate"));
                student.setBirthDate(birthDate);
            } catch (ParseException e) {
                e.printStackTrace();
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid birth date format.");
                return;
            }

            //update the student
            studentDAO.updateStudent(student);
        }
        response.sendRedirect("StudentController?action=list");
    }

    private void deleteStudent(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //find the student to delete
        int id = Integer.parseInt(request.getParameter("id"));
        try{
            studentDAO.deleteStudent(id);
        }catch (Exception e){
            response.sendRedirect("StudentController?action=list");
        }
        response.sendRedirect("StudentController?action=list");
    }

    private void showAddForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //redirect to AddStudent.jsp
        request.getRequestDispatcher("/views/admin/AddStudent.jsp").forward(request, response);
    }

    private void showUpdateForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //find the student to update
        int id = Integer.parseInt(request.getParameter("id"));
        Student student = studentDAO.getStudentById(id);
        //save it as a request attribute
        request.setAttribute("student", student);
        //redirect to jsp based on the destination request parameter
        String destination = request.getParameter("destination");
        request.getRequestDispatcher(destination).forward(request,response);
    }

    private void searchStudents(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //retrieve search parameter
        String keyword = request.getParameter("keyword");
        //search in database
        List<Student> students = studentDAO.searchStudents(keyword);
        //save the result as a request attribute
        request.setAttribute("students", students);
        //redirect to StudentsManagement.jsp
        request.getRequestDispatcher("/views/admin/StudentsManagement.jsp").forward(request, response);
    }


    private void listStudentsByCourse(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //get the id of course from the request parameter
        String courseId = request.getParameter("courseId");

        //get current user's information
        HttpSession session = request.getSession();
        Professor professor = (Professor)session.getAttribute("user");

        //get Students by course id and professor id
        List<Student> students = studentDAO.getStudentsByCourseIdAndProfessorId(Integer.parseInt(courseId),professor.getId());

        //save as request attribute
        request.setAttribute("students", students);

        //get course by id
        Course course = courseDAO.getCourseById(Integer.parseInt(courseId));
        //save as request attribute
        request.setAttribute("course", course);

        //Redirect to ProfessorCourseStudents.jsp
        RequestDispatcher dispatcher = request.getRequestDispatcher("/views/professor/ProfessorCourseStudents.jsp");
        dispatcher.forward(request, response);
    }
}
