import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnector {

    final static String url = "jdbc:mysql://localhost:3306/cengonline?useUnicode=true&useLegacyDatetimeCode=false&serverTimezone=Turkey&allowPublicKeyRetrieval=true&useSSL=false";
    final static String user = "root";
    final static String password = "basak";


    public static Connection getDatabaseConnection() throws SQLException {

         Connection con = DriverManager.getConnection(url, user, password);

        return con;
    }
}
