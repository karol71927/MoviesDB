package manager;

import entity.Movie;


public class MovieManager {
    public boolean addMovie(Movie movie){
        EntityManager entityManager = new EntityManager();
        return entityManager.add(movie);
    }

    public boolean deleteMovie(Movie movie){
        EntityManager entityManager = new EntityManager();
        return entityManager.delete(movie);
    }
}
