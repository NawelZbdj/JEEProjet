package com.jeeproject.DAO;

import com.jeeproject.Model.Professor;
import com.jeeproject.Utils.HibernateUtil;
import com.jeeproject.Model.Registration;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

public class RegistrationDAO {

    //get all registrations
    public List<Registration> getAllRegistrations(){
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
        //Query<Registration> query = session.createQuery("FROM Registration", Registration.class);
        Query<Registration> query = session.createQuery(
                "SELECT r FROM Registration r " +
                   "JOIN FETCH r.course " +
                   "LEFT JOIN FETCH r.professor " +
                   "LEFT JOIN FETCH r.student", Registration.class);
        return query.getResultList();
        }catch(Exception e){
            return null;
        }
    }

    public boolean updateRegistration(Registration registration){
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            session.beginTransaction();
            session.update(registration);
            session.getTransaction().commit();
            return true;
        }catch (Exception e){
            return false;
        }
    }

    public Registration getRegistrationById(int id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Registration> query = session.createQuery("SELECT r FROM Registration r " +
                    "JOIN FETCH r.course c " +
                    "LEFT JOIN FETCH r.professor p " +
                    "LEFT JOIN FETCH r.student s " +
                    "WHERE r.id = :id", Registration.class);
            query.setParameter("id", id);

            return query.uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Registration> getRegistrationsByStudent(int studentId){
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            Query<Registration> query = session.createQuery("SELECT r FROM Registration r " +
                    "JOIN FETCH r.course c " +
                    "LEFT JOIN FETCH r.professor p " +
                    "LEFT JOIN FETCH r.student s " +
                    "WHERE r.student.id = :studentId",Registration.class);
            query.setParameter("studentId", studentId);

            return query.getResultList();
        }catch(Exception e){
            return new ArrayList<>();
        }
    }

    public boolean deleteRegistration(Registration registration){
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            session.beginTransaction();
            if(registration!=null){
                session.delete(registration);
            }
            session.getTransaction().commit();
            return true;
        }catch (Exception e){
            return false;
        }
    }

    public boolean saveRegistration(Registration registration){
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            session.beginTransaction();
            session.save(registration);
            session.getTransaction().commit();
            return true;
        }catch (Exception e){
            return false;
        }
    }

    public List<Registration> getRegistrationsByProfessor(int professorId){
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            Query<Registration> query = session.createQuery("SELECT r FROM Registration r " +
                    "JOIN FETCH r.course c " +
                    "LEFT JOIN FETCH r.professor p " +
                    "LEFT JOIN FETCH r.student s " +
                    "WHERE p.id = :professorId",Registration.class);
            query.setParameter("professorId", professorId);
            return query.getResultList();
        }catch(Exception e){
            return new ArrayList<>();
        }
    }

    public Registration getRegistrationByStudentAndCourse(int studentId, int courseId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Registration> query = session.createQuery("FROM Registration r WHERE r.student.id = :studentId AND r.course.id = :courseId", Registration.class);
            query.setParameter("studentId", studentId);
            query.setParameter("courseId", courseId);
            return query.uniqueResult();
        }
    }

}
