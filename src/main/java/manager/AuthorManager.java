package manager;

import entity.Author;
import entity.HibernateFactory;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class AuthorManager {
    public boolean addAuthor(Author author){
        EntityManager entityManager = new EntityManager();
        return entityManager.add(author);
    }

    public boolean deleteAuthor(Author author){
        EntityManager entityManager = new EntityManager();
        return entityManager.delete(author);
    }

    public boolean deleteAuthorById(Long id){
        boolean isDeleted = false;
        HibernateFactory hibernateFactory = new HibernateFactory();
        Session session = hibernateFactory.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        try {
            Author author = session.load(Author.class, id);
            Hibernate.initialize(author);
            session.delete(author);
            session.getTransaction().commit();
            isDeleted = true;
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
            hibernateFactory.getSessionFactory().close();
        }
        return isDeleted;
    }

    public Author findById(Long id){
        Author author = null;
        HibernateFactory hibernateFactory = new HibernateFactory();
        Session session = hibernateFactory.getSessionFactory().openSession();
        author = session.load(Author.class, id);
        Hibernate.initialize(author);
        session.close();
        hibernateFactory.getSessionFactory().close();
        return author;
    }

    public List<Author> findByName(String name, String surname){
        List<Author> list = new ArrayList<>();
        HibernateFactory hibernateFactory = new HibernateFactory();
        Session session = hibernateFactory.getSessionFactory().openSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Author> criteriaQuery = criteriaBuilder.createQuery(Author.class);
        Root<Author> root = criteriaQuery.from(Author.class);
        Predicate[] predicates = new Predicate[2];
        predicates[0] = criteriaBuilder.like(root.get("name"), "%" + name + "%");
        predicates[1] = criteriaBuilder.like(root.get("surname"), "%" + surname + "%");
        criteriaQuery.select(root).where(predicates);
        Query<Author> query = session.createQuery(criteriaQuery);
        list = query.getResultList();
        session.close();
        hibernateFactory.getSessionFactory().close();
        return list;
    }
}
