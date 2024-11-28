package com.jeeproject.Controller;

import com.jeeproject.DAO.ProfessorDAO;
import com.jeeproject.DAO.ResultDAO;
import com.jeeproject.Model.Result;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;


@WebServlet(name = "ResultController", value = "/ResultController")

public class ResultController extends HttpServlet {
    private ResultDAO resultDAO;

    @Override
    public void init() throws ServletException {
        resultDAO = new ResultDAO();
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
                default :
                    break;
            }
        }catch(Exception e){
            throw new ServletException(e);
        }
    }

    private void getResultsByStudentId(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Result> resultsList = resultDAO.getResultsByStudentId(1); //remplacer id
        request.setAttribute("results",resultsList);

        String destination = request.getParameter("destination");
        request.getRequestDispatcher(destination).forward(request,response);
    }


}
