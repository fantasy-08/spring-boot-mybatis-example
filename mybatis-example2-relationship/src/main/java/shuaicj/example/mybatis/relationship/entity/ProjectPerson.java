package shuaicj.example.mybatis.relationship.entity;

import java.io.Serializable;

/**
 * A java bean representing the relationship of projects and persons.
 *
 * @author shuaicj 2019/07/16
 */
@SuppressWarnings("serial")
public class ProjectPerson implements Serializable {

    private Long id;
    private Project project;
    private Person person;

    public ProjectPerson() {
    }

    public ProjectPerson(Project project, Person person) {
        this.project = project;
        this.person = person;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    @Override
    public String toString() {
        return "ProjectPerson{" +
                "id=" + id +
                ", project=" + project +
                ", person=" + person +
                '}';
    }
}
