import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionTest {
    String className = "oracle.jdbc.OracleDriver";
    String url = "jdbc:oracle:thin:@oracle_high?TNS_ADMIN=/Library/DataBase/Wallet_oracle";
    String user = "admin";
    String passwd = "Omphalos970127";

    @Test
    public void testConnection() throws Exception{
        Class.forName(className);
        try (Connection con = DriverManager.getConnection(url, user, passwd)){
            System.out.println(con);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

}

