package entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "movies")
public class Movie {
    @Id
    @Column(name = "ID_movie")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idMovie;

    @Column(name = "title")
    private String title;

    @ManyToOne
    @JoinColumn(name = "id_author", referencedColumnName = "ID_author")
    private Author author;

    @ManyToMany(mappedBy = "movies")
    private Set<Actor> actors = new HashSet<>();

    public Long getIdMovie() {
        return idMovie;
    }

    public void setIdMovie(Long idMovie) {
        this.idMovie = idMovie;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String name) {
        this.title = name;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public Set<Actor> getActors() {
        return actors;
    }

    public void setActors(Set<Actor> actors) {
        this.actors = actors;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "idMovie=" + idMovie +
                ", title='" + title + '\'' +
                ", author=" + author +
                ", actors=" + actors +
                '}';
    }
}
