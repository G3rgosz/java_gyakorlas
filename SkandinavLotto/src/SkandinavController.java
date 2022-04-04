import java.util.Vector;
import java.util.Random;
import javax.swing.JCheckBox;
import java.sql.Connection;
import java.sql.Statement;

public class SkandinavController {
    
    private ConnectDatabase connDb;
    private Vector<Integer> numberList;
    private Vector<Integer> drawedList;
    private Vector<Integer> choosenList;
    private SkandinavForm skandinavFrm;
    private int counter = 0;

    public SkandinavController(ConnectDatabase connDb){
        choosenList = new Vector<>();
        numberList = new Vector<>();
        drawedList = new Vector<>();
        this.connDb = connDb;
        skandinavFrm = new SkandinavForm();
        skandinavFrm.exitBtn.addActionListener(event -> exit());
        skandinavFrm.drawBtn.addActionListener(event -> drawing());
        fillNumberList();
        numberCheckBoxes();
        skandinavFrm.setVisible(true);
    }
    private void fillNumberList(){
        for(int i = 1; i < 46; i++){
            numberList.add(i);
        }
    }
    private void numberCheckBoxes(){
        for(Integer i = 1; i < 46; i++){
            JCheckBox box = new JCheckBox();
            box.setText(i.toString());
            skandinavFrm.centerPnl.add(box);
            box.addItemListener(event -> {
                JCheckBox check = (JCheckBox) event.getSource();
                choosenList.add(Integer.parseInt(check.getText()));
                counter ++;
                if(counter == 7){
                    skandinavFrm.drawBtn.setEnabled(true);
                }else{
                    skandinavFrm.drawBtn.setEnabled(false);
                }
            });
        }
    }
    private void drawing(){
        int numbers = 45;
        Random rand = new Random();
        for(int i = 0; i < 7; i++){
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
        for(int i = 0; i < choosenList.size(); i++){
            for(int j = 0; j < drawedList.size(); j++){
                if(choosenList.get(i) == drawedList.get(j)){
                    result++;
                }
            }
        }
        String lblValue = skandinavFrm.resultLbl.getText();
        skandinavFrm.resultLbl.setText(lblValue + result.toString());
  
        for (Integer i : drawedList) {
            String drawValue = skandinavFrm.drawLbl.getText();
            String number = String.valueOf(i);
            skandinavFrm.drawLbl.setText(drawValue + number + " ");
        }
    }
    private void numbersToDatabase(){
        Connection conn = connDb.getConnection();
        Statement stmt = null;
        String sqlData = "";
        for(int i = 0; i < drawedList.size(); i++){
            if(i < (drawedList.size()-1)){
                sqlData += String.valueOf(drawedList.get(i) + ":");
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
