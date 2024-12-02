package com.jeeproject.DAO;

import com.jeeproject.Utils.HibernateUtil;
import com.jeeproject.Model.Course;
import org.hibernate.Session;

import java.util.List;
import org.hibernate.query.Query;


public class CourseDAO {

    //get all courses
    public List<Course> getAllCourses(){
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            Query<Course> query = session.createQuery("FROM Course", Course.class);
            return query.getResultList();
        }catch (Exception e){
            return null;
        }
    }

    //get course by id
    public Course getCourseById(int id){
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            return session.get(Course.class,id);
        }catch (Exception e){
            return null;
        }
    }

    //get courses assigned to a professor
    public List<Course> getCoursesByProfessorId(int professorId){

        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            Query<Course> queryList = session.createQuery("SELECT c FROM Course c JOIN Registration r ON c.id = r.course.id WHERE r.professor.id = :professorId", Course.class);
            queryList.setParameter("professorId", professorId);
            return queryList.getResultList();
        }catch (Exception e){
            return null;
        }
    }

    //update a course
    public boolean updateCourse(Course course){
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            session.beginTransaction();
            session.update(course);
            session.getTransaction().commit();
            return true;
        }catch (Exception e){
            return false;
        }
    }

    //add a new course to the database
    public boolean saveCourse(Course course){
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            session.beginTransaction();
            session.save(course);
            session.getTransaction().commit();
            return true;
        }catch (Exception e){
            return false;
        }
    }

    //delete a course
    public boolean deleteCourse(Course course){
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            session.beginTransaction();
            if(course!=null){
                session.delete(course);
            }
            session.getTransaction().commit();
            return true;
        }catch (Exception e){
            return false;
        }
    }

}
