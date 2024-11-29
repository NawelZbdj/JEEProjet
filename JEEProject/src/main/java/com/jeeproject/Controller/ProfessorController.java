package com.jeeproject.Controller;

import com.jeeproject.DAO.AccountDAO;
import com.jeeproject.DAO.ProfessorDAO;
import com.jeeproject.Model.Account;
import com.jeeproject.Model.Professor;
import com.jeeproject.Utils.AccountUtils.PasswordGenerator;
import com.jeeproject.Utils.AccountUtils.UsernameGenerator;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@WebServlet(name = "ProfessorController", value = "/ProfessorController")
public class ProfessorController extends HttpServlet {
    private ProfessorDAO professorDAO;
    private AccountDAO accountDAO;

    @Override
    public void init() throws ServletException {
        professorDAO = new ProfessorDAO();
        accountDAO = new AccountDAO();
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        String action = request.getParameter("action");

        try {
            switch (action) {
                case "list":
                    listProfessors(request, response);
                    break;
                case "add":
                    showAddForm(request, response);
                    break;
                case "update":
                    showUpdateForm(request, response);
                    break;
                case "delete":
                    deleteProfessor(request, response);
                    break;
                case "search":
                    searchProfessors(request, response);
                    break;
                default:
                    listProfessors(request, response);
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
                    addProfessor(request, response);
                    break;
                case "update":
                    updateProfessor(request, response);
                    break;
                default:
                    break;
            }
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    private void listProfessors(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Professor> professors = professorDAO.getAllProfessors();
        request.setAttribute("professors", professors);
        request.getRequestDispatcher("/views/admin/ProfessorsManagement.jsp").forward(request, response);
    }

    private void addProfessor(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String email = request.getParameter("email");
        String specialty = request.getParameter("specialty");
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
        Account professorAccount = new Account();
        professorAccount.setUsername(username);
        professorAccount.setPassword(password);
        professorAccount.setRole("professor");

        // Save Account
        accountDAO.saveAccount(professorAccount);

        // Create and associate the Professor entity
        Professor professor = new Professor();
        professor.setFirstName(firstName);
        professor.setLastName(lastName);
        professor.setEmail(email);
        professor.setSpecialty(specialty);
        professor.setBirthDate(birthDate);
        professor.setAccount(professorAccount);

        // Save Professor
        professorDAO.saveProfessor(professor);

        // Redirect to professor management page
        response.sendRedirect("ProfessorController?action=list");
    }

    private void updateProfessor(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Professor professor = professorDAO.getProfessorById(id);

        if (professor != null) {
            professor.setFirstName(request.getParameter("firstName"));
            professor.setLastName(request.getParameter("lastName"));
            professor.setEmail(request.getParameter("email"));
            professor.setSpecialty(request.getParameter("specialty"));

            try {
                Date birthDate = new SimpleDateFormat("yyyy-MM-dd").parse(request.getParameter("birthDate"));
                professor.setBirthDate(birthDate);
            } catch (ParseException e) {
                e.printStackTrace();
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid birth date format.");
                return;
            }

            professorDAO.updateProfessor(professor);
        }
        response.sendRedirect("ProfessorController?action=list");
    }

    private void deleteProfessor(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        professorDAO.deleteProfessor(id);
        response.sendRedirect("ProfessorController?action=list");
    }

    private void showAddForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/views/admin/AddProfessor.jsp").forward(request, response);
    }

    private void showUpdateForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Professor professor = professorDAO.getProfessorById(id);
        request.setAttribute("professor", professor);
        String destination = request.getParameter("destination");
        request.getRequestDispatcher(destination).forward(request,response);
    }

    private void searchProfessors(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String keyword = request.getParameter("keyword");
        String specialty = request.getParameter("specialty");
        List<Professor> professors = professorDAO.searchProfessors(keyword, specialty);
        request.setAttribute("professors", professors);
        request.getRequestDispatcher("/views/admin/ProfessorsManagement.jsp").forward(request, response);
    }
}
