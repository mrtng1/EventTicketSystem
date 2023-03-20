package ets.dal;

// imports
import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import com.microsoft.sqlserver.jdbc.SQLServerException;

// java imports
import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 * @author tomdra01, mrtng1
 */
public class ConnectionManager {
    private SQLServerDataSource dataSource;

    public ConnectionManager(){
        dataSource = new SQLServerDataSource();
        dataSource.setServerName("10.176.111.34");
        dataSource.setDatabaseName("CSe2022B_e_17_EventTicketSystem");
        dataSource.setUser("CSe2022B_e_17");
        dataSource.setPassword("CSe2022BE17#");
        dataSource.setPortNumber(1433);
        dataSource.setTrustServerCertificate(true);
    }

    public Connection getConnection() throws SQLServerException {
        return dataSource.getConnection();
    }

    public static void main(String[] args) throws SQLException {
        ConnectionManager dataBaseConnection = new ConnectionManager();
        Connection connection = dataBaseConnection.getConnection();

        System.out.println("Database connected" + !connection.isClosed());
        connection.close();
    }
}
