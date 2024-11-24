package com.jeeproject.Controller;

import com.jeeproject.DAO.ProfessorDAO;
import com.jeeproject.DAO.RegistrationDAO;
import com.jeeproject.Model.Professor;
import com.jeeproject.Model.Registration;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "ProfessorController", value = "/ProfessorController")
public class ProfessorController extends HttpServlet {
    private ProfessorDAO professorDAO;

    @Override
    public void init() throws ServletException {
        professorDAO = new ProfessorDAO();
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException{
        String action = request.getParameter("action");

        try{
            switch(action){
                case "list":
                    listProfessors(request,response);
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
                default :
                    break;
            }
        }catch(Exception e){
            throw new ServletException(e);
        }
    }

    private void listProfessors(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Professor> professors = professorDAO.getAllProfessors();
        request.setAttribute("professors",professors);

        String destination = request.getParameter("destination");
        request.getRequestDispatcher(destination).forward(request,response);
    }
}
