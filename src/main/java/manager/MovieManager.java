package manager;

import entity.Actor;
import entity.Author;
import entity.HibernateFactory;
import entity.Movie;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;


public class MovieManager {
    public boolean addMovie(Movie movie){
        EntityManager entityManager = new EntityManager();
        return entityManager.add(movie);
    }

    public boolean deleteMovie(Movie movie){
        EntityManager entityManager = new EntityManager();
        return entityManager.delete(movie);
    }

    public Movie updateMovie(Movie movie){
        EntityManager entityManager = new EntityManager();
        return (Movie) entityManager.update(movie);
    }

    public boolean deleteMovieById(Long id){
        boolean isDeleted = false;
        HibernateFactory hibernateFactory = new HibernateFactory();
        Session session = hibernateFactory.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        try {
            Movie movie = session.load(Movie.class, id);
            session.delete(movie);
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

    public Movie findById(Long id){
        Movie movie = null;
        HibernateFactory hibernateFactory = new HibernateFactory();
        Session session = hibernateFactory.getSessionFactory().openSession();
        try {
            movie = session.load(Movie.class, id);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
            hibernateFactory.getSessionFactory().close();
        }
        return movie;
    }

    public List<Movie> findByTitle(String title){
        List<Movie> list = new ArrayList<>();
        HibernateFactory hibernateFactory = new HibernateFactory();
        Session session = hibernateFactory.getSessionFactory().openSession();
        try {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<Movie> criteriaQuery = criteriaBuilder.createQuery(Movie.class);
            Root<Movie> root = criteriaQuery.from(Movie.class);
            Predicate[] predicates = new Predicate[2];
            predicates[0] = criteriaBuilder.equal(root.get("title"), title);
            criteriaQuery.select(root).where(predicates);

            Query<Movie> query = session.createQuery(criteriaQuery);
            list = query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
            hibernateFactory.getSessionFactory().close();
        }
        return list;
    }

    public Movie addActors(Movie movie, List<Actor> actorList){
        Set<Actor> actorSet = movie.getActors();
        for (Actor actor : actorList)
            actorSet.add(actor);
        movie.setActors(actorSet);
        /*HibernateFactory hibernateFactory = new HibernateFactory();
        Session session = hibernateFactory.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.update(movie);
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        } finally {
            session.close();
            hibernateFactory.getSessionFactory().close();
        }*/
        return updateMovie(movie);
    }

    public Movie addAuthor(Movie movie, Author author){
        movie.setAuthor(author);
        /*HibernateFactory hibernateFactory = new HibernateFactory();
        Session session = hibernateFactory.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.update(movie);
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        } finally {
            session.close();
            hibernateFactory.getSessionFactory().close();
        }*/
        return updateMovie(movie);
    }
}
