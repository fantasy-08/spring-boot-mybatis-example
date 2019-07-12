package shuaicj.example.mybatis.relationship.mapper;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import shuaicj.example.mybatis.relationship.entity.Identity;
import shuaicj.example.mybatis.relationship.entity.Person;
import shuaicj.example.mybatis.relationship.entity.Project;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test relationship mappers.
 *
 * @author shuaicj 2019/07/03
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RelationshipMapperTest {

    @Autowired
    private PersonMapper personMapper;

    @Autowired
    private IdentityMapper identityMapper;

    @Autowired
    private ProjectMapper projectMapper;

    @Before
    public void setUp() {
        personMapper.deleteAll();
        identityMapper.deleteAll();
        projectMapper.deleteAll();
    }

    @After
    public void tearDown() {
        setUp();
    }

    @Test
    public void findByUsernameOk() {
        Identity identity1 = new Identity("identity1");
        Identity identity2 = new Identity("identity2");
        identityMapper.insert(identity1);
        identityMapper.insert(identity2);

        Project project1 = new Project("project1");
        Project project2 = new Project("project2");
        projectMapper.insert(project1);
        projectMapper.insert(project2);

        Person person1 = new Person("shuaicj1");
        person1.setIdentity(identity1);
        personMapper.insert(person1);

        Person person2 = new Person("shuaicj2");
        person2.setIdentity(identity2);
        personMapper.insert(person2);

        List<Person> list = personMapper.findByName("shuaicj1");
        assertThat(list).hasSize(1);

        Person p = list.get(0);
        assertThat(p.getId()).isEqualTo(person1.getId());
        assertThat(p.getName()).isEqualTo("shuaicj1");
        assertThat(p.getIdentity().getId()).isEqualTo(identity1.getId());
        assertThat(p.getIdentity().getNumber()).isEqualTo("identity1");
    }
}