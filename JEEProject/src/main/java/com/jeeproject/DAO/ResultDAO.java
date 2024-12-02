package com.jeeproject.DAO;

import com.jeeproject.Model.Result;
import com.jeeproject.Utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

public class ResultDAO {

    //get all results with associated registration, course, professor, and student
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

    //get all results for a specific student by their student id
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

    //add a new result in the database
    public void saveResult(Result result) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(result);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //update a result in the database
    public void updateResult(Result result) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();

            session.update(result);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //get results for a specific student and a specific course
    public List<Result> getResultsByStudentAndCourse(int studentId, int courseId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                            "SELECT r FROM Result r " +
                                    "JOIN FETCH r.registration reg " +
                                    "JOIN FETCH reg.course c " +
                                    "WHERE reg.student.id = :studentId " +
                                    "AND reg.course.id = :courseId", Result.class)
                    .setParameter("studentId", studentId)
                    .setParameter("courseId", courseId)
                    .getResultList();
        }
    }


}
