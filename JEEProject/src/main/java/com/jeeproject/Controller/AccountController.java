package com.jeeproject.Controller;

import com.jeeproject.DAO.*;
import com.jeeproject.Model.Account;
import com.jeeproject.Model.Administrator;
import com.jeeproject.Model.Professor;
import com.jeeproject.Model.Student;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet(name = "AccountController", value = "/AccountController")
public class AccountController extends HttpServlet {
    private AccountDAO accountDAO;
    private StudentDAO studentDAO;
    private AdministratorDAO administratorDAO;
    private ProfessorDAO professorDAO;

    @Override
    public void init() throws ServletException {
        accountDAO = new AccountDAO();
        studentDAO = new StudentDAO();
        administratorDAO = new AdministratorDAO();
        professorDAO = new ProfessorDAO();
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException{
        String action = request.getParameter("action");

        try{
            switch(action){
                case "connect":
                    loginByRole(request,response);
                    break;
                default :
                    break;
            }
        }catch(Exception e){
            throw new ServletException(e);
        }
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException{
        String action = request.getParameter("action");

        try{
            switch(action){
                default :
                    break;
            }
        }catch(Exception e){
            throw new ServletException(e);
        }
    }

    private void loginByRole(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String username=request.getParameter("username");
        String password = request.getParameter("password");
        String role = request.getParameter("role");

        Account account = accountDAO.getAccountByLogIn(username,password);

        HttpSession session = request.getSession();

        if (account != null) {
            if (role.equals("admin")) {
                Administrator admin = administratorDAO.getAdminByAccountId(account.getId());
                session.setAttribute("admin",admin);
                session.setAttribute("role",role);
                response.sendRedirect("views/admin/AdminMenu.jsp");

            } else if (role.equals("professor")) {
                Professor professor =professorDAO.getProfessorByAccountId(account.getId());
                session.setAttribute("professor",professor);
                session.setAttribute("role",role);
                response.sendRedirect("views/professor/ProfessorMenu.jsp");

            } else if (role.equals("admin")) {
                Student student = studentDAO.getStudentByAccountId(account.getId());
                session.setAttribute("student",student);
                session.setAttribute("role",role);
                response.sendRedirect("views/student/StudentMenu.jsp");
            }
        }
        else{
            response.sendRedirect("views/menu.jsp");
        }
    }
}
