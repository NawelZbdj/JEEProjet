package com.jeeproject.DAO;

import java.util.List;
import com.jeeproject.Model.Account;
import com.jeeproject.Utils.HibernateUtil;
import org.hibernate.*;

public class AccountDAO {
    public void saveAccount(Account account) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            session.save(account);


            transaction.commit();
            System.out.println("Transaction successful! Data should be in the database.");
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
                System.out.println("Transaction failed and rolled back.");
            }
            e.printStackTrace();
        }

    }

    public static List<String> getAllUsernames() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("SELECT username FROM Account", String.class).list();
        }
    }
}
