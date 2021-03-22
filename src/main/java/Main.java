import entity.Actor;
import entity.Author;
import entity.HibernateFactory;
import entity.Movie;
import manager.ActorManager;
import manager.AuthorManager;
import manager.MovieManager;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Author author = new Author();
        author.setName("Krzysztof");
        author.setSurname("Krawczyk");

        AuthorManager authorManager = new AuthorManager();
        authorManager.addAuthor(author);

        List<Author> authors = authorManager.findByName("Krzysztof", "Krawczyk");
        for( Author author1 : authors){
            System.out.println(author1.toString());
        }

        authorManager.deleteAuthor(author);

        authors = authorManager.findByName("Krzysztof", "Krawczyk");
        for( Author author1 : authors){
            System.out.println(author1.toString());
        }

        Actor actor = new Actor();
        actor.setName("Tomasz");
        actor.setSurname("Karolak");

        ActorManager actorManager = new ActorManager();
        actorManager.addActor(actor);

        List<Actor> actors = actorManager.findByName("Tomasz", "Karolak");


        Movie movie = new Movie();
        movie.setTitle("Maczeta zabija");

        MovieManager movieManager = new MovieManager();
        movieManager.addMovie(movie);

        movieManager.addActors(movie, actors);
        movieManager.addAuthor(movie, author);

        System.out.println(movie);
/*
        for( Actor actor1 : actors){
            System.out.println(actor1);
            actorManager.deleteActor(actor1);
        }
        movieManager.deleteMovie(movie);
*/
    }
}
