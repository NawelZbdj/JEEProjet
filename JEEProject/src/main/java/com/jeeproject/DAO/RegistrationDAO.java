package com.jeeproject.DAO;

import com.jeeproject.Model.Professor;
import com.jeeproject.Utils.HibernateUtil;
import com.jeeproject.Model.Registration;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

public class RegistrationDAO {

        //Get all registrations from the database, including associated courses, professors, and students
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

    //update a registration
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

    //get a registration by id, with related entities
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

    //get all registrations for a specific student by student id
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

    //Delete registration
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

    //Save a new registration in the database
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

    //get all registrations for a specific professor by their professor id
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

    //get a registration for a student and a specific course
    public Registration getRegistrationByStudentAndCourse(int studentId, int courseId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Registration> query = session.createQuery("FROM Registration r WHERE r.student.id = :studentId AND r.course.id = :courseId", Registration.class);
            query.setParameter("studentId", studentId);
            query.setParameter("courseId", courseId);
            return query.uniqueResult();
        }
    }

}
