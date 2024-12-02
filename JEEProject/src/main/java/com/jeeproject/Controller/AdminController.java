package com.jeeproject.Controller;


import com.jeeproject.Model.Account;
import com.jeeproject.Model.Administrator;
import com.jeeproject.DAO.AdministratorDAO;
import com.jeeproject.DAO.AccountDAO;

import com.jeeproject.Utils.AccountUtils.*;

import com.jeeproject.Utils.EmailUtil;
import com.jeeproject.Utils.HibernateUtil;
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

@WebServlet("/AdminController")
public class AdminController extends HttpServlet {

    private AdministratorDAO administratorDAO;
    private AccountDAO accountDAO;

    @Override
    public void init() throws ServletException {
        administratorDAO = new AdministratorDAO();
        accountDAO = new AccountDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) action = "list";


        //redirection to method by action
        switch (action) {
            case "list":
                listAdministrators(request, response);
                break;
            case "add":
                showAddForm(request, response);
                break;
            case "update":
                showUpdateForm(request, response);
                break;
            case "delete":
                deleteAdministrator(request, response);
                break;
            case "search":
                searchAdministrators(request, response);
                break;
            default:
                listAdministrators(request, response);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) action = "list";

        switch (action) {
            case "add":
                addAdministrator(request, response);
                break;
            case "update":
                updateAdministrator(request, response);
                break;
        }
    }

    private void listAdministrators(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //retrieve all administrators in database
        List<Administrator> administrators = administratorDAO.getAllAdministrators();
        //set an attribute with admin
        request.setAttribute("administrators", administrators);
        request.getRequestDispatcher("/views/admin/AdminManagement.jsp").forward(request, response);
    }

    private void addAdministrator(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //retrieve all request parameters
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String email = request.getParameter("email");
        String position = request.getParameter("position");
        Date birthDate = null;

        //try to format the birthdate
        try {
            birthDate = new SimpleDateFormat("yyyy-MM-dd").parse(request.getParameter("birthDate"));
        } catch (ParseException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid date format.");
            return;
        }

        //generate username and password
        String username = UsernameGenerator.generateUsername(firstName, lastName);
        String password = PasswordGenerator.generateRandomPassword();

        //create and save the Account entity
        Account adminAccount = new Account();
        adminAccount.setUsername(username);
        adminAccount.setPassword(password);
        adminAccount.setRole("admin");

        //save Account
        accountDAO.saveAccount(adminAccount);

        //log the generated Account ID
        System.out.println("Account ID generated: " + adminAccount.getId());

        //create and associate the Administrator entity
        Administrator admin = new Administrator();
        admin.setFirstName(firstName);
        admin.setLastName(lastName);
        admin.setEmail(email);
        admin.setPosition(position);
        admin.setBirthDate(birthDate);
        admin.setAccount(adminAccount); //associate Account with Administrator

        //save Administrator
        administratorDAO.saveAdministrator(admin);

        //log the Administrator details
        System.out.println("Admin details saved: " + admin.toString());

        //Send a mail to the new admin with his connection credentials
        String subject = "sColartiY : Account created !";
        String body = "Welcome " + admin.getFirstName() + ",\n\n" +
                "You are now part of our staff as a \n" + admin.getPosition() +
                "Here are your connection details: :\n\n" +
                "Username :" + adminAccount.getUsername()+
                "\nPassword :" + adminAccount.getPassword()+
                "\n\nBest Regards,\nAdmin staff.";

        try {
            EmailUtil.sendEmail(admin.getEmail(), subject, body);
        } catch (Exception e) {
            response.sendRedirect("AdminController?action=list");
        }

        //redirect to admin management page
        response.sendRedirect("AdminController?action=list");
    }

    private void updateAdministrator(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //get the id in request
        int id = Integer.parseInt(request.getParameter("id"));
        //retrieve the admin by id
        Administrator admin = administratorDAO.getAdministratorById(id);

        //if the admin exists
        if (admin != null) {
            //update all his information
            admin.setFirstName(request.getParameter("firstName"));
            admin.setLastName(request.getParameter("lastName"));
            admin.setEmail(request.getParameter("email"));
            admin.setPosition(request.getParameter("position"));

            //try to format the birthdate
            try {
                Date birthDate = new SimpleDateFormat("yyyy-MM-dd").parse(request.getParameter("birthDate"));
                admin.setBirthDate(birthDate);
            } catch (ParseException e) {
                e.printStackTrace();
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid birth date format.");
                return;
            }

            //call the dao to update the database
            administratorDAO.updateAdministrator(admin);
        }
        response.sendRedirect("AdminController?action=list");
    }


    private void deleteAdministrator(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //get the id
        int id = Integer.parseInt(request.getParameter("id"));
        //delete the admin
        try{
            administratorDAO.deleteAdministrator(id);
        } catch (Exception e){
            response.sendRedirect("AdminController?action=list");
        }
        response.sendRedirect("AdminController?action=list");
    }
    private void showAddForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //redirection to AddAdmin.jsp
        request.getRequestDispatcher("/views/admin/AddAdmin.jsp").forward(request, response);
    }


    private void showUpdateForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //retrieve the admin to edit by id to pre-fill the edit form
        int id = Integer.parseInt(request.getParameter("id"));
        Administrator admin = administratorDAO.getAdministratorById(id);
        request.setAttribute("admin", admin);
        request.getRequestDispatcher("/views/admin/UpdateAdmin.jsp").forward(request, response);
    }


    private void searchAdministrators(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //retrieve the search parameters
        String keyword = request.getParameter("keyword");
        String position = request.getParameter("position");
        //perform the search in the database
        List<Administrator> administrators = administratorDAO.searchAdministrators(keyword, position);
        //save result in a request attribute
        request.setAttribute("administrators", administrators);
        //redirection to AdminManagement
        request.getRequestDispatcher("/views/admin/AdminManagement.jsp").forward(request, response);
    }
}