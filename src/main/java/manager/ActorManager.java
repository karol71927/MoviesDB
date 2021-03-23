package manager;

import entity.Actor;
import entity.HibernateFactory;
import entity.Movie;
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
import java.util.Set;

public class ActorManager {
    public boolean addActor(Actor actor){
        EntityManager entityManager = new EntityManager();
        return entityManager.add(actor);
    }

    public boolean deleteActor(Actor actor){
        EntityManager entityManager = new EntityManager();
        return entityManager.delete(actor);
    }

    public Actor updateActor(Actor actor){
        EntityManager entityManager = new EntityManager();
        return (Actor) entityManager.update(actor);
    }

    public boolean deleteActorById(Long id){
        boolean isDeleted = false;
        HibernateFactory hibernateFactory = new HibernateFactory();
        Session session = hibernateFactory.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        try {
            Actor actor = session.load(Actor.class, id);
            Hibernate.initialize(actor);
            session.delete(actor);
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

    public Actor findById(Long id){
        Actor actor = null;
        HibernateFactory hibernateFactory = new HibernateFactory();
        Session session = hibernateFactory.getSessionFactory().openSession();
        actor = session.load(Actor.class, id);
        Hibernate.initialize(actor);
        session.close();
        hibernateFactory.getSessionFactory().close();
        return actor;
    }

    public List<Actor> findByName(String name, String surname){
        List<Actor> list = new ArrayList<>();
        HibernateFactory hibernateFactory = new HibernateFactory();
        Session session = hibernateFactory.getSessionFactory().openSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Actor> criteriaQuery = criteriaBuilder.createQuery(Actor.class);
        Root<Actor> root = criteriaQuery.from(Actor.class);
        Predicate[] predicates = new Predicate[2];
        predicates[0] = criteriaBuilder.equal(root.get("name"), name);
        predicates[1] = criteriaBuilder.equal(root.get("surname"), surname);
        criteriaQuery.select(root).where(predicates);
        Query<Actor> query = session.createQuery(criteriaQuery);
        list = query.getResultList();
        session.close();
        hibernateFactory.getSessionFactory().close();
        return list;
    }

    public Actor addMovie(Actor actor, Movie movie){
        Set<Movie> movies = actor.getMovies();
        movies.add(movie);
        actor.setMovies(movies);
        return updateActor(actor);
    }
}
