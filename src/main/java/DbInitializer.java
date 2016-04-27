import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@WebListener
public class DbInitializer implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {

    }

    private Connection getConnection() {
        try {
            Context initContext = new InitialContext();
            Context envContext  = (Context) initContext.lookup("java:/comp/env");
            DataSource ds = (DataSource) envContext.lookup("jdbc/TestDB");
            return ds.getConnection();
        } catch (NamingException | SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
