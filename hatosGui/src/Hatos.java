
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import java.awt.GridLayout;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Random;
import java.util.Vector;
import java.awt.BorderLayout;
import java.awt.FlowLayout;

public class Hatos extends JFrame{
    public JPanel mainPnl = new JPanel();
    public JPanel northPnl = new JPanel();
    public JPanel eastPnl = new JPanel();
    public JPanel southPnl = new JPanel();
    public JPanel westPnl = new JPanel();
    public JPanel centerPnl = new JPanel();
    public JPanel drawPnl = new JPanel();
    public JPanel buttonPnl = new JPanel();

    public JLabel resultLbl = new JLabel("Találatok: ");
    public JLabel drawLbl = new JLabel("Számok: ");

    public JButton drawBtn = new JButton("Húzás");
    public JButton exitBtn = new JButton("Kilépés");

    private ConnectDatabase connDb;
    private Vector<Integer> numberList;
    private Vector<Integer> drawedList;
    private Vector<Integer> choosenList;
    private int counter = 0;

    public static void main(String[] args) throws Exception {
        new Hatos();
    }
    public Hatos(){
        initComponents();
        this.setVisible(true);
        hatosController();
    }
    private void initComponents(){
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(450,400);
        this.setLayout(new GridLayout(1,1));
        mainPnl.setLayout(new BorderLayout());
        this.add(mainPnl);

        northPnl.setLayout(new FlowLayout(FlowLayout.CENTER));
        northPnl.add(resultLbl);
        mainPnl.add(northPnl, BorderLayout.NORTH);

        southPnl.setLayout(new GridLayout(1,2));
        drawPnl.setLayout(new FlowLayout(FlowLayout.CENTER));
        drawPnl.add(drawLbl);
        buttonPnl.setLayout(new FlowLayout(FlowLayout.RIGHT));
        buttonPnl.add(drawBtn);
        drawBtn.setEnabled(false);
        buttonPnl.add(exitBtn);
        southPnl.add(drawPnl);
        southPnl.add(buttonPnl);
        mainPnl.add(southPnl, BorderLayout.SOUTH);

        westPnl.setSize(10,400);
        mainPnl.add(westPnl, BorderLayout.WEST);

        centerPnl.setLayout(new GridLayout(10,9));
        mainPnl.add(centerPnl, BorderLayout.CENTER);
    }
    private void hatosController(){
        numberList = new Vector<>();
        drawedList = new Vector<>();
        choosenList = new Vector<>();
        exitBtn.addActionListener(event -> exit());
        drawBtn.addActionListener(event -> drawing());
        fillNumberList();
        numberCheckBoxes();
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
            centerPnl.add(box);
            box.addItemListener(event -> {
                JCheckBox check = (JCheckBox) event.getSource();
                choosenList.add(Integer.parseInt(check.getText()));
                counter++;
                if (counter == 6) {
                    drawBtn.setEnabled(true);
                }else{
                    drawBtn.setEnabled(false);
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
        String lblValue = resultLbl.getText();
        resultLbl.setText(lblValue + result.toString());
        for(Integer i : drawedList){
            String drawValue = drawLbl.getText();
            String number = String.valueOf(i);
            drawLbl.setText(drawValue + number + " ");
        }
    } 
    private void numbersToDatabase(){
        ConnectDatabase connDb = new ConnectDatabase();
        Connection conn = connDb.getConnection();
        Statement stmt = null;
        String sqlData = "";
        for(int i = 0; i < drawedList.size(); i++){
            if(i < (drawedList.size() - 1)){
                sqlData += String.valueOf(drawedList.get(i) + ":");
            } else{
                sqlData += String.valueOf(drawedList.get(i));
            }
        }
        System.out.println(sqlData);
        String sql = "INSERT INTO drawed (draw) VALUES ('"+ sqlData +"');";
        try {
            stmt = conn.createStatement();
            stmt.execute(sql);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        connDb.closeConnection();
    }
    private void exit(){
        System.exit(1);
    }
}
