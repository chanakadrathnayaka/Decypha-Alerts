import org.junit.Assert;
import org.junit.Test;

/**
 * Created by chathurag
 * On 12/7/2016
 */
public class DatabaseConnectivityTest {

    @Test
    public void testDBConnection(){
        try {
            /*DriverManagerDataSource dataSource = new DriverManagerDataSource();
            dataSource.setDriverClassName("oracle.jdbc.driver.OracleDriver");
            dataSource.setUrl("jdbc:oracle:thin:@//192.168.13.214:1521/NGPDB2");
            dataSource.setUsername("dfn_plus");
            dataSource.setPassword("password");

            Connection connection = dataSource.getConnection();

            Assert.assertNotNull(connection);

            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM ALERT_EARNINGS_ANN");
            preparedStatement.executeQuery();*/

        } catch (Exception e){
            Assert.fail("Database connection test failed");
        }
    }

    @Test
    public void testDataAccessContext(){

       /* ApplicationContext context =  new ClassPathXmlApplicationContext("alert-db-data-access-bean.xml");
        Assert.assertNotNull(context);

        Object alertDataSource = context.getBean("alertDataSource");
        Assert.assertNotNull(alertDataSource);

        Object jdbcDaoFactory = context.getBean("jdbcDaoFactory");
        Assert.assertNotNull(jdbcDaoFactory);

        Object hibernateSessionFactory = context.getBean("hibernateSessionFactory");
        Assert.assertNotNull(hibernateSessionFactory);*/
    }
}
