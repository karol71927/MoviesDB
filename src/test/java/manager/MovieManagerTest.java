package manager;

import entity.Actor;
import entity.Author;
import entity.Movie;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class MovieManagerTest {

    private static MovieManager movieManager;
    private static Movie movie1;
    private static Movie movie2;
    private static Movie movie3;
    private static Movie movieDelete1;
    private static Movie movieDelete2;

    private static ActorManager actorManager;
    private static Actor actor1;
    private static Actor actor2;

    private static AuthorManager authorManager;
    private static Author author;

    @BeforeAll
    static void beforeAll() {
        movieManager = new MovieManager();

        movie1 = new Movie();
        movie1.setTitle("Spartakus");

        movie2 = new Movie();
        movie2.setTitle("Gladiator");

        movie3 = new Movie();
        movie3.setTitle("Batman");

        movieDelete1 = new Movie();
        movieDelete1.setTitle("Batman 2");
        movieManager.addMovie(movieDelete1);

        movieDelete2 = new Movie();
        movieDelete2.setTitle("Batman 2");
        movieManager.addMovie(movieDelete2);

        actor1 = new Actor();
        actor1.setName("Tomasz");
        actor1.setSurname("Karolak");

        actor2 = new Actor();
        actor2.setName("Piotr");
        actor2.setSurname("Adamczyk");

        actorManager = new ActorManager();
        actorManager.addActor(actor1);
        actorManager.addActor(actor2);

        authorManager = new AuthorManager();
        author = new Author();
        author.setName("Quentin");
        author.setSurname("Tarantino");
        authorManager.addAuthor(author);
    }

    @AfterAll
    static void afterAll() {
        movieManager.deleteMovie(movie1);
        movieManager.deleteMovie(movie2);
        movieManager.deleteMovie(movie3);
        actorManager.deleteActor(actor1);
        actorManager.deleteActor(actor2);
    }

    @Test
    @Order(1)
    void addMovie() {
        assertTrue(movieManager.addMovie(movie1));
        assertTrue(movieManager.addMovie(movie2));
        assertTrue(movieManager.addMovie(movie3));
    }

    @Test
    @Order(2)
    void updateMovie() {
        movie1.setTitle("Joker");
        Movie updated = movieManager.updateMovie(movie1);
        assertEquals("Joker", updated.getTitle());
    }

    @Test
    @Order(3)
    void findById() {
        Movie found = movieManager.findById(movie1.getIdMovie());
        assertEquals(movie1.getTitle(), found.getTitle());
    }

    @Test
    @Order(4)
    void findByTitle() {
        List<Movie> movies = movieManager.findByTitle(movie2.getTitle());
        assertEquals(movie2.getTitle(), movies.get(0).getTitle());
    }

    @Test
    @Order(5)
    void addActors() {
        List<Actor> actors = new ArrayList<>();
        actors.add(actor1);
        actors.add(actor2);
        Movie updated = movieManager.addActors(movie1, actors);
        for (Actor actor : actors){
            assertTrue(updated.getActors().contains(actor));
        }
    }

    @Test
    @Order(6)
    void addAuthor() {
        Movie updated = movieManager.addAuthor(movie1, author);
        assertEquals(author, updated.getAuthor());
    }

    @Test
    @Order(7)
    void deleteMovie() {
        assertTrue(movieManager.deleteMovie(movieDelete1));
        assertFalse(movieManager.deleteMovie(movieDelete1));
    }

    @Test
    @Order(8)
    void deleteMovieById() {
        assertTrue(movieManager.deleteMovieById(movieDelete2.getIdMovie()));
    }
}