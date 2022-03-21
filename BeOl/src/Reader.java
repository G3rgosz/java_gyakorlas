import java.util.Scanner;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.util.Vector; 

public class Reader {
    
    public Reader() {
    }

    public Vector<String> readFile() {

        Vector<String> numbers = new Vector<>();
        try {
            
            FileReader fRead = new FileReader("adat.txt");
            Scanner sc = new Scanner(fRead);
            while( sc.hasNext() ) {
                
                String row = sc.nextLine();
                if(row.matches( "[0-9:]+" )) {
                    numbers.add( row );
                }
            }
            sc.close();

        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }

        return numbers;
    }
}
