import java.util.Vector;
import java.util.Random;
import javax.swing.JCheckBox;

public class LottoController {

    private ConnectDatabase connDb;
    private Vector<Integer> numberList;
    private Vector<Integer> drawedList;
    private Vector<Integer> chosenList;
    private LottoForm lottoFrm;

    public LottoController(ConnectDatabase connDb){
        this.connDb = connDb;
        lottoFrm = new LottoForm();
        lottoFrm.exitBtn.addActionListener(event -> exit());
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
            });
        } 
    }
    private void exit(){
        System.exit(1);
    }
}
