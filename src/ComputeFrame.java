import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ComputeFrame {
    private static JFrame computeFrame;
    private static JLabel subject;
    private static JTextField answers;
    static String ID = "0";
    private static JPanel board;

    public void showWindows(){
        computeFrame.setVisible(true);
    }

    public ComputeFrame() {
        computeFrame = new JFrame("Compute:"+ID);
        JMenuBar menuBar = new JMenuBar();
        JMenu diff = new JMenu("难度");
        JMenuItem yer = new JMenuItem("幼  儿");
        JMenuItem xxs = new JMenuItem("小学生");
        JMenuItem zdy = new JMenuItem("自定义");
        board = new JPanel();

        board.setSize(740,510);
        board.setLocation(20,30);
        board.setBackground(Color.lightGray);

        computeFrame.setSize(800,600);
        computeFrame.setLocation((Toolkit.getDefaultToolkit().getScreenSize().width - 800) / 2,(Toolkit.getDefaultToolkit().getScreenSize().height - 600) / 2);
        computeFrame.setLayout(null);

        menuBar.setSize(800,27);
        menuBar.setLocation(0,0);
        menuBar.setBackground(Color.lightGray);

        addActionListener(yer);
        addActionListener(xxs);
        addActionListener(zdy);

        diff.add(yer);
        diff.add(xxs);
        diff.addSeparator();
        diff.add(zdy);
        menuBar.add(diff);
        computeFrame.add(menuBar);
        computeFrame.add(board);
    }

    private static void addActionListener(JMenuItem item){
        item.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Subject(board ,item.getText());
            }
        });
    }

    public static void main(String[] args) {
        new ComputeFrame().showWindows();
    }

    public static void Subject(JPanel panel,String type){
        switch (type){
            case "幼  儿":{
                globle(panel);
                panel.repaint();
            }break;
            case "小学生":{
                globle(panel);
                panel.repaint();
            }break;
            case "自定义":{
                globle(panel);
                panel.repaint();
            }
        }
    }

    public static void globle(JPanel panel){
        Font font = new Font("Times New Roman",Font.BOLD,30);

        JLabel equal = new JLabel("=");
        JButton next = new JButton("下一题");
        JButton pre  = new JButton("上一题");
        JButton submit = new JButton("提交");
        subject = new JLabel("????????????");
        answers = new JTextField(10);

        subject.setSize(200,40);
        answers.setSize(200,40);
        equal.setSize(60,30);
        next.setSize(80,30);
        pre.setSize(80,30);
        submit.setSize(80,30);

        equal.setFont(font);
        subject.setFont(font);
        answers.setFont(font);

        equal.setLocation(360,200);
        answers.setLocation(410,195);
        subject.setLocation(110,195);
        next.setLocation(440,350);
        submit.setLocation(330,350);
        pre.setLocation(220,350);

        panel.setLayout(null);
        panel.add(answers);
        panel.add(subject);
        panel.add(equal);
        panel.add(next);
        panel.add(pre);
        panel.add(submit);
    }
}
