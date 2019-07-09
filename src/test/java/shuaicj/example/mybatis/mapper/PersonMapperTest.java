package shuaicj.example.mybatis.mapper;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import shuaicj.example.mybatis.entity.Identity;
import shuaicj.example.mybatis.entity.IdentityDetail;
import shuaicj.example.mybatis.entity.Person;
import shuaicj.example.mybatis.entity.PersonDO;
import shuaicj.example.mybatis.entity.PersonDetail;
import shuaicj.example.mybatis.entity.Project;

/**
 * Test {@link UserMapper}.
 *
 * @author shuaicj 2019/07/03
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class PersonMapperTest {

    private static final Logger logger = LoggerFactory.getLogger(PersonMapperTest.class);

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
        Person person1 = new Person("person1", identity1.getId());
        Person person2 = new Person("person2", identity2.getId());
        personMapper.insert(person1);
        personMapper.insert(person2);
        // PersonDetail personDetail1 = personMapper.findDetailById2(person1.getId());
        // PersonDetail personDetail2 = personMapper.findDetailById2(person2.getId());
        // IdentityDetail identityDetail1 = identityMapper.findDetailById(identity1.getId());
        // IdentityDetail identityDetail2 = identityMapper.findDetailById(identity2.getId());
        PersonDO personDO1 = personMapper.findDoById(person1.getId());
        logger.info("----------------- success");
    }
}