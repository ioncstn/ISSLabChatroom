package com.example.repository;

import com.example.domain.Chatter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.example.domain.Moderator;
import jakarta.persistence.PersistenceException;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.exception.GenericJDBCException;
import org.sqlite.SQLiteException;

public class RepositoryORMChatters implements RepositoryChatters{

    private static SessionFactory factory;
    public RepositoryORMChatters(){
        try {
            factory = new Configuration().configure().buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Failed to create sessionFactory object." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }
    @Override
    public boolean add(Chatter item) {
        Session session = factory.openSession();
        System.out.println("adding new chatter");
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
            System.out.println("adding new chatter failed...");
            System.out.println(ex.getMessage());
        }
        catch(PersistenceException ex){
            System.out.println("chatter already exists...");
            System.out.println(ex.getMessage());
        }
        finally {
            session.close();
        }
        return false;
    }

    @Override
    public Optional<Chatter> find(int ID) {
        return Optional.empty();
    }

    @Override
    public boolean update(int ID, Chatter item) {
        System.out.println("trying to update chatter with id " + item.getID());
        Session session = factory.openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            session.merge(item);
            tx.commit();
            return true;
        } catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            System.out.println("failed to update...");
            e.printStackTrace();
        } finally {
            session.close();
        }
        return false;
    }

    @Override
    public boolean delete(Chatter item) {
        System.out.println("trying to delete chatter with id " + item.getID());
        Session session = factory.openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            session.remove(item);
            tx.commit();
            return true;
        } catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            System.out.println("failed to delete...");
            e.printStackTrace();
        } finally {
            session.close();
        }
        return false;
    }

    @Override
    public List<Chatter> getAll() {
        return null;
    }

    @Override
    public Optional<Chatter> findByUsernameAndPassword(String username, String password) {
        Optional<Chatter> result = Optional.empty();
        System.out.println("finding chatter by username and password");

        Session session = factory.openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            List<Chatter> arbiter = session.createQuery("FROM Chatter WHERE password= :password and username= :username", Chatter.class).setParameter("password", password).setParameter("username", username).list();
            if(arbiter.size() > 0){
                result = Optional.of(arbiter.get(0));
            }
            tx.commit();
        } catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            System.out.println("finding chatter by username and password failed...");
            e.printStackTrace();
        } finally {
            session.close();
        }

        return result;
    }

    @Override
    public boolean isModerator(int ID) {
        boolean result = false;
        System.out.println("checking if ID " + ID + " is moderator...");

        Session session = factory.openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            List<Moderator> mods = session.createQuery("FROM Moderator WHERE ID= :modID", Moderator.class).setParameter("modID", ID).list();
            if(mods.size() > 0){
                result = true;
            }
            tx.commit();
        } catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            System.out.println("checking if ID is moderator failed...");
            e.printStackTrace();
        } finally {
            session.close();
        }

        return result;
    }

    @Override
    public boolean isMuted(int ID) {
        boolean result = false;
        System.out.println("checking if ID " + ID + " is muted...");

        Session session = factory.openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            List<Moderator> mods = session.createQuery("FROM Chatter WHERE ID= :uID and muted=true", Moderator.class).setParameter("uID", ID).list();
            if(mods.size() > 0){
                result = true;
            }
            tx.commit();
        } catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            System.out.println("checking if ID is muted failed...");
            e.printStackTrace();
        } finally {
            session.close();
        }

        return result;
    }

    @Override
    public Optional<Chatter> findByUsername(String username) {
        Optional<Chatter> result = Optional.empty();
        System.out.println("finding chatter by username");

        Session session = factory.openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            List<Chatter> arbiter = session.createQuery("FROM Chatter WHERE username= :username", Chatter.class).setParameter("username", username).list();
            if(arbiter.size() > 0){
                result = Optional.of(arbiter.get(0));
            }
            tx.commit();
        } catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            System.out.println("finding chatter by username failed...");
            e.printStackTrace();
        } finally {
            session.close();
        }

        return result;
    }
}
