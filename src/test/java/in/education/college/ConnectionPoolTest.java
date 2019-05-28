package in.education.college;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ConnectionPoolTest {

	@Autowired
	DataSource dataSource;

	@Test
	public void connectionPoolTest() {
//		System.out.println("DataSource : "+dataSource.getClass().getName());
		Assert.assertEquals("com.zaxxer.hikari.HikariDataSource", dataSource.getClass().getName());
	}

}
