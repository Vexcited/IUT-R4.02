package iut.java.spring.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity(name = "individu")
public class Individu {
    @Id
    @GeneratedValue(generator = "seq_individu", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "seq_individu", sequenceName = "seq_individu", initialValue = 100)
    private long id;
    private String firstName;
    private String lastName;
    private String title;
    private Integer height;
    private LocalDate birthDate;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }
}
