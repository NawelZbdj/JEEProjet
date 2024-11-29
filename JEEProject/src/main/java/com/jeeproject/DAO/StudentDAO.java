package com.jeeproject.DAO;

import com.jeeproject.Model.Professor;
import com.jeeproject.Model.Student;
import com.jeeproject.Util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.query.Query;

public class StudentDAO {
    public Student getStudentById(int id){
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            return session.get(Student.class,id);
        }catch (Exception e){
            return null;
        }
    }

    public Student getStudentByAccountId(int accountId){
        try(Session session = com.jeeproject.Utils.HibernateUtil.getSessionFactory().openSession()){
            Query<Student> query = session.createQuery("SELECT s FROM Student s WHERE s.account.id = :accountId ",Student.class);
            query.setParameter("accountId", accountId);
            return query.uniqueResult();
        }catch(Exception e){
            return null;
        }
    }
}
