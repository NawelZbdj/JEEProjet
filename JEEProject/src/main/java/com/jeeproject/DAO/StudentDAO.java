package com.jeeproject.DAO;

import com.jeeproject.Model.Student;
import com.jeeproject.Model.Student;
import com.jeeproject.Utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class StudentDAO {

    public void saveStudent(Student student) {
        Transaction transaction = null;
        try (Session session = com.jeeproject.Utils.HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(student);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    public Student getStudentById(int id) {
        try (Session session = com.jeeproject.Utils.HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Student.class, id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Student> getAllStudents() {
        try (Session session = com.jeeproject.Utils.HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from Student", Student.class).list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void updateStudent(Student student) {
        Transaction transaction = null;
        try (Session session = com.jeeproject.Utils.HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.update(student);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    public void deleteStudent(int id) {
        Transaction transaction = null;
        try (Session session = com.jeeproject.Utils.HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Student student = session.get(Student.class, id);
            if (student != null) session.delete(student);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    public List<Student> searchStudents(String keyword) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "from Student p where (p.firstName like :keyword or p.lastName like :keyword)";

            var query = session.createQuery(hql, Student.class);
            query.setParameter("keyword", "%" + keyword + "%");

            return query.list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
