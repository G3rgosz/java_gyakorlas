import java.sql.Statement;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.Vector;

public class Hatos {
    public static void main(String[] args) throws Exception {
        Vector<String> numbers = new Vector<>();
        try {
            FileReader reader = new FileReader("adat.txt");
            Scanner sc = new Scanner(reader);
            while(sc.hasNext()){
                String row = sc.nextLine();
                if(row.matches("[0-9:]+")){
                    numbers.add(row);
                }
            }
            sc.close();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
        ConnectDatabase connDb = new ConnectDatabase();
        Connection conn = connDb.getConnection();
        numbers.forEach(rows -> {
            String sql = "INSERT INTO drawed (draw) VALUES ('" + rows + "');";
            Statement stmt = null;
            try {
                stmt = conn.createStatement();
                stmt.execute(sql);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });
        connDb.closeConnection();
    }
}
