package com.jeeproject.DAO;

import com.jeeproject.Model.Professor;
import com.jeeproject.Model.Student;
import com.jeeproject.Util.HibernateUtil;
import org.hibernate.Session;

public class StudentDAO {
    public Student getStudentById(int id){
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            return session.get(Student.class,id);
        }catch (Exception e){
            return null;
        }
    }
}
