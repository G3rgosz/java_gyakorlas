import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
//https://www.nosalty.hu/recept/paprikas-krumpli-kolbasszal
public class ConnectDatabase {

    private Connection conn;

    public ConnectDatabase() {
        conn = null;
        ConnectToDatabase();
    }
    private void ConnectToDatabase() {

        String url = "jdbc:mariadb://localhost:3306/lotto";

        try {

            conn = DriverManager.getConnection(url, "root", "");

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        
        if(conn != null) {

            System.out.println("ok");
        }else {

            System.out.println("nem ok");
        }
    }
    public void closeConnect() {

        try {
            if(conn != null) {
                conn.close();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    public Connection getConnection() {
        return conn;
    }
}
