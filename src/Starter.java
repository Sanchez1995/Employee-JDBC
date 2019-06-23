import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import java.sql.SQLException;

public class Starter {

    final static Logger logger = Logger.getLogger(Starter.class);

    public static void main(String[] args) throws SQLException {

        PropertyConfigurator.configure("Log4j.properties");


        try {
            Employee emp = new Employee();
            emp.createDatabase();
            emp.createTable();
            emp.breakdownCSVData();
        } catch (Exception e) {
            e.printStackTrace();
        }
        logger.debug("This is a Debugger");
        logger.info("This is Info");
        logger.warn("This is a Warning");
        logger.error("This is an Error");
        logger.fatal("This is Fatal");

    }
}
