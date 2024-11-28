package com.jeeproject.DAO;

import com.jeeproject.Model.Administrator;
import org.hibernate.Session;
import org.hibernate.Transaction;
import com.jeeproject.Utils.HibernateUtil;

import java.util.List;

public class AdministratorDAO {

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

    public Administrator getAdministratorById(int id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Administrator.class, id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Administrator> getAllAdministrators() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from Administrator", Administrator.class).list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

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

}