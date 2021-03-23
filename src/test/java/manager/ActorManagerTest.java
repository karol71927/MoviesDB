package manager;

import entity.Actor;
import entity.Movie;
import org.junit.jupiter.api.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ActorManagerTest {
    private static ActorManager actorManager;
    private static Actor actor1;
    private static Actor actor2;
    private static Actor actor3;
    private static Actor actorDelete1;
    private static Actor actorDelete2;

    @BeforeAll
    static void beforeAll() {
        actorManager = new ActorManager();
        actor1 = new Actor();
        actor1.setName("Tomasz");
        actor1.setSurname("Karolak");

        actor2 = new Actor();
        actor2.setName("Piotr");
        actor2.setSurname("Adamczyk");

        actorDelete1 = new Actor();
        actorDelete1.setName("Tomasz");
        actorDelete1.setSurname("Kot");
        actorManager.addActor(actorDelete1);

        actorDelete2 = new Actor();
        actorDelete2.setName("");
        actorDelete2.setSurname("");
        actorManager.addActor(actorDelete2);
    }


    @AfterAll
    public static void afterAll(){
        actorManager.deleteActor(actor1);
        actorManager.deleteActor(actor2);
        actorManager.deleteActor(actor3);
    }

    @org.junit.jupiter.api.Test
    @Order(1)
    void addActor() {
        assertTrue(actorManager.addActor(actor1));
        assertTrue(actorManager.addActor(actor2));

        actor3 = new Actor();
        actor3.setName("Andrzej");
        actor3.setSurname("Grabowski");
        actor3.setIdActor(actor1.getIdActor());
        assertTrue(actorManager.addActor(actor3));

        Actor actor4 = new Actor();
        assertFalse(actorManager.addActor(actor4));
    }

    @org.junit.jupiter.api.Test
    @Order(2)
    void deleteActor() {
        assertTrue(actorManager.deleteActor(actorDelete1));
        assertFalse(actorManager.deleteActor(actorDelete1));
    }

    @org.junit.jupiter.api.Test
    @Order(3)
    void updateActor() {
        actor2.setName("Antonio");
        actor2.setSurname("Banderas");
        Actor updated = actorManager.updateActor(actor2);
        assertEquals("Antonio", updated.getName());
        assertEquals("Banderas", updated.getSurname());
        assertEquals(actor2.getIdActor(),updated.getIdActor());
    }

    @org.junit.jupiter.api.Test
    @Order(4)
    void deleteActorById() {
        assertTrue(actorManager.deleteActorById(actorDelete2.getIdActor()));
    }

    @org.junit.jupiter.api.Test
    @Order(5)
    void findById() {
        Long id = actor1.getIdActor();
        Actor found = actorManager.findById(id);
        assertEquals(actor1.getName(), found.getName());
        assertEquals(actor1.getSurname(), found.getSurname());
    }

    @org.junit.jupiter.api.Test
    @Order(6)
    void findByName() {
        List<Actor> found = actorManager.findByName("Tomasz", "Karolak");
        assertEquals(actor1.getName(), found.get(0).getName());
        assertEquals(actor1.getSurname(), found.get(0).getSurname());
    }

    @org.junit.jupiter.api.Test
    @Order(7)
    void addMovie() {
        Movie movie = new Movie();
        movie.setTitle("Krzyżacy");
        MovieManager movieManager = new MovieManager();
        movieManager.addMovie(movie);
        Actor updated = actorManager.addMovie(actor1, movie);
        assertTrue(updated.getMovies().contains(movie));
    }
}