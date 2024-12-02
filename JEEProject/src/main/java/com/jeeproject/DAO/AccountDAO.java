package com.jeeproject.DAO;

import java.util.ArrayList;
import java.util.List;
import com.jeeproject.Model.Account;
import com.jeeproject.Model.Registration;
import com.jeeproject.Utils.HibernateUtil;
import org.hibernate.*;
import org.hibernate.query.Query;

public class AccountDAO {

    //Save a new account in the database
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

    //get all usernames from the account table
    public static List<String> getAllUsernames() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("SELECT username FROM Account", String.class).list();
        }
    }

    //get an account based on username and password for login
    public Account getAccountByLogIn(String username,String password){
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            Query<Account> query = session.createQuery("SELECT a FROM Account a WHERE a.username = :username AND a.password = :password",Account.class);
            query.setParameter("username", username);
            query.setParameter("password", password);
            return query.uniqueResult();
        }catch(Exception e){
            return null;
        }
    }
}
