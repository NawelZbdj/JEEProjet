package com.jeeproject.Controller;

import com.jeeproject.DAO.AccountDAO;
import com.jeeproject.DAO.ProfessorDAO;
import com.jeeproject.Model.Account;
import com.jeeproject.Model.Professor;
import com.jeeproject.Utils.AccountUtils.PasswordGenerator;
import com.jeeproject.Utils.AccountUtils.UsernameGenerator;
import com.jeeproject.Utils.EmailUtil;
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
        //redirection to method by action

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
        //redirection to method by action

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
        //retrieve all professors
        List<Professor> professors = professorDAO.getAllProfessors();
        request.setAttribute("professors", professors);
        request.getRequestDispatcher("/views/admin/ProfessorsManagement.jsp").forward(request, response);
    }

    private void addProfessor(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //get all request parameter values
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String email = request.getParameter("email");
        String specialty = request.getParameter("specialty");
        Date birthDate;

        //attempt to formatt= the birthdate
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

        //email the new professor
        String subject = "sColartiY : Account created !";
        String body = "Welcome " + professor.getFirstName() + ",\n\n" +
                "You are now part of our teaching team in \n" + professor.getSpecialty() +
                "Here are your connection details: :\n\n" +
                "Username :" + professorAccount.getUsername()+
                "\nPassword :" + professorAccount.getPassword()+
                "\n\nBest Regards,\nAdmin staff.";

        try {
            EmailUtil.sendEmail(professor.getEmail(), subject, body);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // Redirect to professor management page
        response.sendRedirect("ProfessorController?action=list");
    }

    private void updateProfessor(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //get the professor by id in parameter
        int id = Integer.parseInt(request.getParameter("id"));
        Professor professor = professorDAO.getProfessorById(id);

        //if the professor exits
        if (professor != null) {
            professor.setFirstName(request.getParameter("firstName"));
            professor.setLastName(request.getParameter("lastName"));
            professor.setEmail(request.getParameter("email"));
            professor.setSpecialty(request.getParameter("specialty"));

            //try to format the birthdate
            try {
                Date birthDate = new SimpleDateFormat("yyyy-MM-dd").parse(request.getParameter("birthDate"));
                professor.setBirthDate(birthDate);
            } catch (ParseException e) {
                e.printStackTrace();
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid birth date format.");
                return;
            }

            //update the teacher
            professorDAO.updateProfessor(professor);
        }
        response.sendRedirect("ProfessorController?action=list");
    }

    private void deleteProfessor(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //find the professor by id
        int id = Integer.parseInt(request.getParameter("id"));
        //attempt to delete the professor
        try{
            professorDAO.deleteProfessor(id);

        }catch (Exception e){
            response.sendRedirect("ProfessorController?action=list");
        }
        response.sendRedirect("ProfessorController?action=list");
    }

    private void showAddForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //redirect to AddProfessor.jsp
        request.getRequestDispatcher("/views/admin/AddProfessor.jsp").forward(request, response);
    }

    private void showUpdateForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //Retrieve the professor to edit
        int id = Integer.parseInt(request.getParameter("id"));
        Professor professor = professorDAO.getProfessorById(id);
        //save the professor as a request attribute to pre-fill the edit form
        request.setAttribute("professor", professor);
        //redirection based in the destination request parameter
        String destination = request.getParameter("destination");
        request.getRequestDispatcher(destination).forward(request,response);
    }

    private void searchProfessors(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //retrieve the search parameters
        String keyword = request.getParameter("keyword");
        String specialty = request.getParameter("specialty");
        //search in the database
        List<Professor> professors = professorDAO.searchProfessors(keyword, specialty);
        //save result as a request attribute
        request.setAttribute("professors", professors);
        request.getRequestDispatcher("/views/admin/ProfessorsManagement.jsp").forward(request, response);
    }
}
