package com.example.repository;

import com.example.domain.Message;
import org.hibernate.SessionFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class RepositoryORMMessages implements RepositoryMessages{
    private static SessionFactory factory;
    public RepositoryORMMessages(){
        try {
            factory = new Configuration().configure().buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Failed to create sessionFactory object." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }
    @Override
    public boolean add(Message item) {
        Session session = factory.openSession();
        System.out.println("adding new message");
        Transaction transaction = null;
        try{
            transaction = session.beginTransaction();
            session.persist(item);
            transaction.commit();
            return true;
        }
        catch(HibernateException ex){
            if(transaction != null){
                transaction.rollback();
            }
            System.out.println("adding new message failed...");
            System.out.println(ex.getMessage());
        }
        finally {
            session.close();
        }
        return false;
    }

    @Override
    public Optional<Message> find(int ID) {
        return Optional.empty();
    }

    @Override
    public boolean update(int ID, Message item) {
        return false;
    }

    @Override
    public boolean delete(Message item) {
        return false;
    }

    @Override
    public List<Message> getAll() {
        Session session = factory.openSession();
        System.out.println("getting all messages");
        List<Message> result = new ArrayList<>();
        Transaction transaction = null;
        try{
            transaction = session.beginTransaction();
            result = session.createQuery("FROM Message WHERE receiver=null", Message.class).list();
            transaction.commit();
        }
        catch(HibernateException ex){
            if(transaction != null){
                transaction.rollback();
            }
            System.out.println("getting all messages failed...");
            System.out.println(ex.getMessage());
        }
        finally {
            session.close();
        }
        return result;
    }

    @Override
    public List<Message> getAllForUser(String username) {
        Session session = factory.openSession();
        System.out.println("getting all messages for user: " + username);
        List<Message> result = new ArrayList<>();
        Transaction transaction = null;
        try{
            transaction = session.beginTransaction();
            result = session.createQuery("FROM Message WHERE receiver=null or receiver = :rcv or username = :snd", Message.class).setParameter("rcv", username).setParameter("snd", username).list();
            transaction.commit();
        }
        catch(HibernateException ex){
            if(transaction != null){
                transaction.rollback();
            }
            System.out.println("getting all messages failed...");
            System.out.println(ex.getMessage());
        }
        finally {
            session.close();
        }
        return result;
    }
}
