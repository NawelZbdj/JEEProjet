package com.jeeproject.DAO;

import com.jeeproject.Model.Course;
import com.jeeproject.Util.HibernateUtil;
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

    public Registration getRegistrationById(int id){
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            return session.get(Registration.class,id);
        }catch (Exception e){
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

}
