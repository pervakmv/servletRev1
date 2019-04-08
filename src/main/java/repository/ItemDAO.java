package repository;

import model.Item;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

public class ItemDAO implements DAO<Item> {

    private static SessionFactory sessionFactory;

    public static SessionFactory createSessionFactory() {
        //singletion patern
        if (sessionFactory == null) {
            sessionFactory = new Configuration().configure().buildSessionFactory();
        }
        return sessionFactory;
    }


    @Override
    public Item save(Item item) {
        Session session = null;
        Transaction tr = null;
        try {
            session = createSessionFactory().openSession();
            tr = session.getTransaction();
            tr.begin();
            //action
            session.save(item);
            //close session/tr
            session.getTransaction().commit();
            System.out.println("Save item is done");

        } catch (HibernateException e) {
            System.err.println("Save hotel is failed");
            System.err.println(e.getMessage());
            item = null;
        } finally {
            if (session != null)
                session.close();
        }

        return item;
    }

    @Override
    public Item delete(Item item) {
        Session session = null;
        Transaction tr = null;
        try {
            session = createSessionFactory().openSession();
            tr = session.getTransaction();
            tr.begin();

            //action
            session.delete(item);
            //close session/tr
            session.getTransaction().commit();
            System.out.println("Delete hotel is done");
        } catch (HibernateException e) {
            System.err.println("Delete item is failed");
            System.err.println(e.getMessage());
            item = null;
            if (tr != null)
                tr.rollback();
        } finally {
            if (session != null)
                session.close();
        }

        return item;
    }

    @Override
    public Item update(Item item){
        Session session = null;
        Transaction tr = null;


        try {
            session = createSessionFactory().openSession();
            tr = session.getTransaction();
            tr.begin();

            //action
            session.update(item);
            //close session /tr
            session.getTransaction().commit();
            System.out.println("Update item is done");
        } catch (HibernateException e) {
            System.err.println("Update hotel is failed");
            System.out.println(e.getMessage());
            item = null;
            if (tr != null)
                tr.rollback();

        } finally {
            if (session != null)
                session.close();
        }
        return item;
    }

    @Override
    public Item findById(long id) {
        Session session = null;
        Transaction tr = null;
        Item resItem = new Item();
        try {
            session = createSessionFactory().openSession();
            tr = session.getTransaction();
            tr.begin();

            resItem = session.get(Item.class, id);
            System.out.println("Item = " + resItem.getName());
            session.getTransaction().commit();
            System.out.println("find item by id is done");
        } catch (HibernateException e) {
            System.err.println("find item by id is faild");
            System.out.println(e.getMessage());
            if (tr != null)
                tr.rollback();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return resItem;
    }

    public List<Item> itemsList() {
        List<Item> resList = new ArrayList();
        Session session = null;
        Transaction tr = null;

        try {
            session = createSessionFactory().openSession();
            tr = session.getTransaction();
            tr.begin();
            Query query = session.createQuery("from Item");
            resList = query.list();
            session.getTransaction().commit();
            System.out.println("get list done");

        } catch (HibernateException e) {
            System.err.println("get list done is failed");
            System.out.println(e.getMessage());
            if (tr != null) {
                tr.rollback();
            }

        } finally {
            if (session != null) {
                session.close();
            }
        }
        return resList;
    }

    public Item findByName(String name) {
        List<Item> list = itemsList();
        if (list == null)
            return null;
        for (Item it : list) {

            if (name.equals(it.getName())) {
                return it;
            }
        }
        return null;
    }
}
