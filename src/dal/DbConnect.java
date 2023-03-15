package dal;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import com.microsoft.sqlserver.jdbc.SQLServerException;

import java.sql.Connection;
import java.sql.SQLException;

public class DbConnect {
    private SQLServerDataSource dataSource;

    public DbConnect(){
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
        DbConnect dataBaseConnection = new DbConnect();
        Connection connection = dataBaseConnection.getConnection();

        System.out.println("Database connected" + !connection.isClosed());
        connection.close();
    }
}
