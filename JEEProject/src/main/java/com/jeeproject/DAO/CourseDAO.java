package com.jeeproject.DAO;

import com.jeeproject.HibernateUtil;
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

    public Course getCourseById(int id){
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            return session.get(Course.class,id);
        }catch (Exception e){
            return null;
        }
    }

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
