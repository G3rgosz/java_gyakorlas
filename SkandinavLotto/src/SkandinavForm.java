import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.FlowLayout;

public class SkandinavForm extends JFrame{
    public JPanel mainPnl = new JPanel();
    public JPanel northPnl = new JPanel();
    public JPanel eastPnl = new JPanel();
    public JPanel southPnl = new JPanel();
    public JPanel westPnl = new JPanel();
    public JPanel centerPnl = new JPanel();
    public JPanel drawPnl = new JPanel();
    public JPanel buttonPnl = new JPanel();
    
    public JLabel resultLbl = new JLabel("Találatok: ");
    public JLabel drawLbl = new JLabel("Számok ");

    public JButton drawBtn = new JButton("Húzás");
    public JButton exitBtn = new JButton("Kilépés");
    
    public SkandinavForm(){
        initComponents();
        this.setVisible(true);
    } 
    private void initComponents(){
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(450,400);
        this.setLayout(new GridLayout(1,1));
        mainPnl.setLayout( new BorderLayout());
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
        mainPnl.add(westPnl);

        centerPnl.setLayout(new GridLayout(10,9));
        mainPnl.add(centerPnl, BorderLayout.CENTER);
    }
}
