package nl.codegorilla.sap.model;

import jakarta.persistence.*;

import java.util.Set;

@Entity
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, updatable = false, name = "id")
    private long id;

    private String firstName;

    private String lastName;

    private String email;

    @OneToMany
    private Set<CourseStatus> courseStatusSet;


    public Student() {
    }


    public Student(long id, String firstName, String lastName, String email, Set<CourseStatus> courseStatusSet) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.courseStatusSet = courseStatusSet;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<CourseStatus> getCourseStatusSet() {
        return courseStatusSet;
    }

    public void setCourseStatusSet(Set<CourseStatus> courseStatusSet) {
        this.courseStatusSet = courseStatusSet;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", status=" +
                '}';
    }
}


