package com.jeeproject.DAO;

import com.jeeproject.Model.Student;
import com.jeeproject.Utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;
import org.hibernate.query.Query;

public class StudentDAO {

    //add a new student in the database
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

    //get a student by id
    public Student getStudentById(int id) {
        try (Session session = com.jeeproject.Utils.HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Student.class, id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //get all students
    public List<Student> getAllStudents() {
        try (Session session = com.jeeproject.Utils.HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from Student", Student.class).list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //update a student in the database
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

    //delete student in the database
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

    //search for students by a keyword in their first name or last name
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

    //get a student by their account id
    public Student getStudentByAccountId(int accountId){
        try(Session session = com.jeeproject.Utils.HibernateUtil.getSessionFactory().openSession()){
            Query<Student> query = session.createQuery("SELECT s FROM Student s WHERE s.account.id = :accountId ",Student.class);
            query.setParameter("accountId", accountId);
            return query.uniqueResult();
        }catch(Exception e){
            return null;
        }
    }

    //get students enrolled in a specific course taught by a specific professor
    public List<Student> getStudentsByCourseIdAndProfessorId(int courseId, int professorId) {

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Student> query = session.createQuery("SELECT DISTINCT s FROM Student s JOIN Registration r ON s.id = r.student.id " +
                    "WHERE r.course.id = :courseId AND r.professor.id = :professorId", Student.class);
            query.setParameter("courseId", courseId);
            query.setParameter("professorId", professorId);
            return query.getResultList();
        } catch (Exception e) {
            return null;
        }
    }
}
