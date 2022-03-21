import java.util.Vector;
import java.util.Random;
import javax.swing.JCheckBox;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.SQLDataException;

public class LottoController {

    private ConnectDatabase connDb;
    private Vector<Integer> numberList;
    private Vector<Integer> drawedList;
    private Vector<Integer> chosenList;
    private LottoForm lottoFrm;
    private int counter = 0;

    public LottoController(ConnectDatabase connDb){
        chosenList = new Vector<>();
        drawedList = new Vector<>();
        numberList = new Vector<>();
        this.connDb = connDb;
        lottoFrm = new LottoForm();
        lottoFrm.exitBtn.addActionListener(event -> exit());
        lottoFrm.drawBtn.addActionListener(event -> drawing());
        fillNumberList();
        numberCheckBoxes();
        lottoFrm.setVisible(true);
    }
    private void fillNumberList(){
        for(int i = 1; i < 91; i++){
            numberList.add(i);
        }
    }
    private void numberCheckBoxes(){
        for(Integer i = 1; i < 91; i++){
            JCheckBox box = new JCheckBox();
            box.setText( i.toString() );
            lottoFrm.centerPnl.add(box);
            box.addItemListener(event -> {
                JCheckBox check = (JCheckBox) event.getSource();
                chosenList.add(Integer.parseInt(check.getText()));
                counter ++;
                if(counter == 5){
                    lottoFrm.drawBtn.setEnabled(true);
                }else{
                    lottoFrm.drawBtn.setEnabled(false);
                }
            });
        } 
    }
    private void drawing(){
        int numbers = 90;
        Random rand = new Random();
        for(int i = 0; i < 5; i++){
            int number = rand.nextInt(numbers) + 1;
            numberList.remove(number - 1);
            numbers --;
            drawedList.add(number);
        }
        showResult();
        numbersToDatabase();
    }
    private void showResult(){
        Integer result = 0;
        for(int i = 0; i < chosenList.size(); i++){
            for(int j = 0; j < drawedList.size(); j++){
                if(chosenList.get(i) == drawedList.get(j)){
                    result ++;
                }
            }
        }
        String lblValue = lottoFrm.resultLbl.getText();
        lottoFrm.resultLbl.setText(lblValue + result.toString());
  
        for (Integer i : drawedList) {
            String drawValue = lottoFrm.drawLbl.getText();
            String number = String.valueOf(i);
            lottoFrm.drawLbl.setText(drawValue + number + " ");
        }
    }
    private void numbersToDatabase(){
        Connection conn = connDb.getConnection();
        Statement stmt = null;
        String sqlData = "";
        for(int i = 0; i < drawedList.size(); i++){
            if(i < (drawedList.size()-1)){
                sqlData += String.valueOf(drawedList.get(i)) + ":";
            }else{
                sqlData += String.valueOf(drawedList.get(i));
            }
        }
        System.out.println(sqlData);
        String sql = "INSERT INTO drawed (draw) VALUES ('" + sqlData + "');";
        try {
            stmt = conn.createStatement();
            stmt.execute(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
        connDb.closeConnect();
    }
    private void exit(){
        System.exit(1);
    }
}
