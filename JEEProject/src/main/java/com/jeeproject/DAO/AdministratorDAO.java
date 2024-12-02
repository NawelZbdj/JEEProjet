package com.jeeproject.DAO;

import com.jeeproject.Model.Account;
import com.jeeproject.Model.Administrator;
import org.hibernate.Session;
import org.hibernate.Transaction;
import com.jeeproject.Utils.HibernateUtil;
import org.hibernate.query.Query;

import java.util.List;

public class AdministratorDAO {


    //save a new admin in the database
    public void saveAdministrator(Administrator administrator) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(administrator);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    //get admin by id
    public Administrator getAdministratorById(int id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Administrator.class, id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //get all admin from database
    public List<Administrator> getAllAdministrators() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from Administrator", Administrator.class).list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //update an administrator's information
    public void updateAdministrator(Administrator administrator) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.update(administrator);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    //delete an administrator by id
    public void deleteAdministrator(int id) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Administrator administrator = session.get(Administrator.class, id);
            if (administrator != null) session.delete(administrator);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    //search administrators based on a keyword and optional position
    public List<Administrator> searchAdministrators(String keyword, String position) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "from Administrator a where (a.firstName like :keyword or a.lastName like :keyword)";
            if (position != null && !position.isEmpty()) {
                hql += " and a.position = :position";
            }
            var query = session.createQuery(hql, Administrator.class);
            query.setParameter("keyword", "%" + keyword + "%");
            if (position != null && !position.isEmpty()) {
                query.setParameter("position", position);
            }
            return query.list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //get the administrator associated with an account id
    public Administrator getAdminByAccountId(int accountId){
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            Query<Administrator> query = session.createQuery("SELECT a FROM Administrator a WHERE a.account.id = :accountId ",Administrator.class);
            query.setParameter("accountId", accountId);
            return query.uniqueResult();
        }catch(Exception e){
            return null;
        }
    }
}