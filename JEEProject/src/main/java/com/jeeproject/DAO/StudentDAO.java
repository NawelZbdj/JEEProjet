package com.jeeproject.DAO;

import com.jeeproject.Model.Student;
import com.jeeproject.Model.Student;
import com.jeeproject.Utils.HibernateUtil;
import com.jeeproject.Utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;
import org.hibernate.query.Query;

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

    public Student getStudentByAccountId(int accountId){
        try(Session session = com.jeeproject.Utils.HibernateUtil.getSessionFactory().openSession()){
            Query<Student> query = session.createQuery("SELECT s FROM Student s WHERE s.account.id = :accountId ",Student.class);
            query.setParameter("accountId", accountId);
            return query.uniqueResult();
        }catch(Exception e){
            return null;
        }
    }

    public List<Student> getStudentsByCourseId(int courseId) {

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Student> query = session.createQuery("SELECT s FROM Student s " + "JOIN Registration r ON s.id = r.student.id " + "WHERE r.course.id = :courseId", Student.class
            );
            query.setParameter("courseId", courseId);
            return query.getResultList();
        } catch (Exception e) {
            return null;
        }
    }
}
