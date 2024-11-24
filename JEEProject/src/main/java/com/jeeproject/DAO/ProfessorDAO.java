package com.jeeproject.DAO;

import com.jeeproject.HibernateUtil;
import com.jeeproject.Model.Course;
import com.jeeproject.Model.Professor;
import com.jeeproject.Model.Registration;
import jakarta.servlet.annotation.WebServlet;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class ProfessorDAO {

    //get all registrations
    public List<Professor> getAllProfessors(){
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            Query<Professor> query = session.createQuery("FROM Professor ", Professor.class);
            return query.getResultList();
        }catch (Exception e){
            return null;
        }
    }

    public Professor getProfessorById(int id){
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            return session.get(Professor.class,id);
        }catch (Exception e){
            return null;
        }
    }

}
