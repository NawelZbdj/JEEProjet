package com.jeeproject.DAO;

import com.jeeproject.Model.Result;
import com.jeeproject.Utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

public class ResultDAO {
    public List<Result> getAllResults() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Result> query = session.createQuery("SELECT res FROM Result res " +
                            "JOIN FETCH res.registration reg " +
                            "JOIN FETCH reg.course c " +
                            "LEFT JOIN FETCH reg.professor p " +
                            "LEFT JOIN FETCH reg.student s",Result.class);
            return query.getResultList();
        }catch(Exception e){
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public List<Result> getResultsByStudentId(int studentId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Result> query = session.createQuery(
                    "SELECT res FROM Result res " +
                            "JOIN FETCH res.registration reg " +
                            "JOIN FETCH reg.course c " +
                            "LEFT JOIN FETCH reg.professor p " +
                            "LEFT JOIN FETCH reg.student s " +
                            "WHERE reg.student.id = :studentId",Result.class);
            query.setParameter("studentId", studentId);
            return query.getResultList();
        }catch(Exception e){
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}
