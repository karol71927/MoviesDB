package manager;

import entity.Author;
import org.junit.jupiter.api.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AuthorManagerTest {

    private static AuthorManager authorManager;
    private static Author author1;
    private static Author author2;
    private static Author authorDelete1;
    private static Author authorDelete2;

    @BeforeAll
    static void beforeAll() {
        authorManager = new AuthorManager();

        author1 = new Author();
        author1.setName("Quentin");
        author1.setSurname("Tarantino");

        author2 = new Author();
        author2.setName("Papryk");
        author2.setSurname("Vegeta");

        authorDelete1 = new Author();
        authorDelete1.setName("Andrzej");
        authorDelete1.setSurname("Wajda");

        authorDelete2 = new Author();
        authorDelete2.setName("Michael");
        authorDelete2.setSurname("Bay");
    }

    @AfterAll
    static void afterAll() {
        authorManager.deleteAuthor(author1);
        authorManager.deleteAuthor(author2);
    }

    @Test
    @Order(1)
    void addAuthor() {
        assertTrue(authorManager.addAuthor(author1));
        assertTrue(authorManager.addAuthor(author2));
        assertTrue(authorManager.addAuthor(authorDelete1));
        assertTrue(authorManager.addAuthor(authorDelete2));
    }

    @Test
    @Order(2)
    void deleteAuthor() {
        assertTrue(authorManager.deleteAuthor(authorDelete1));
        assertFalse(authorManager.deleteAuthor(authorDelete1));
    }

    @Test
    @Order(3)
    void deleteAuthorById() {
        assertTrue(authorManager.deleteAuthorById(authorDelete2.getIdAuthor()));
    }

    @Test
    @Order(4)
    void findById() {
        Author found = authorManager.findById(author1.getIdAuthor());
        assertEquals(author1.getName(), found.getName());
        assertEquals(author1.getSurname(), found.getSurname());
    }

    @Test
    @Order(5)
    void findByName() {
        List<Author> authors = authorManager.findByName(author1.getName(),author1.getSurname());
        assertEquals(author1.getIdAuthor(), authors.get(0).getIdAuthor());
    }
}