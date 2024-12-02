package com.jeeproject.DAO;

import com.jeeproject.Model.Administrator;
import com.jeeproject.Utils.HibernateUtil;
import com.jeeproject.Model.Professor;
import org.hibernate.Session;
import org.hibernate.Transaction;
import com.jeeproject.Utils.HibernateUtil;
import org.hibernate.query.Query;

import java.util.List;

public class ProfessorDAO {

    //Add a new professor to the database
    public void saveProfessor(Professor professor) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(professor);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    //get prof by id
    public Professor getProfessorById(int id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Professor.class, id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //get all professors
    public List<Professor> getAllProfessors() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from Professor", Professor.class).list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //get a professor by their accountId
    public Professor getProfessorByAccountId(int accountId){
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            Query<Professor> query = session.createQuery("SELECT p FROM Professor p WHERE p.account.id = :accountId ",Professor.class);
            query.setParameter("accountId", accountId);
            return query.uniqueResult();
        }catch(Exception e){
            return null;
        }
    }

    //update the professor's information
    public void updateProfessor(Professor professor) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.update(professor);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    //delete a professor
    public void deleteProfessor(int id) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Professor professor = session.get(Professor.class, id);
            if (professor != null) session.delete(professor);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    //search for professors by keyword and optional specialty
    public List<Professor> searchProfessors(String keyword, String specialty) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "from Professor p where (p.firstName like :keyword or p.lastName like :keyword)";
            if (specialty != null && !specialty.isEmpty()) {
                hql += " and p.specialty = :specialty";
            }
            var query = session.createQuery(hql, Professor.class);
            query.setParameter("keyword", "%" + keyword + "%");
            if (specialty != null && !specialty.isEmpty()) {
                query.setParameter("specialty", specialty);
            }
            return query.list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
