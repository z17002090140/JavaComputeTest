import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ComputeFrame {
    private static JFrame computeFrame;
    private static JTextField subject;
    private static JTextField answers;
    static String ID = "0";
    private static JPanel board;
    private static Subjects subjects;
    private static JLabel hiddenLab;

    public void showWindows(){
        computeFrame.setVisible(true);
    }

    public ComputeFrame() {
        computeFrame = new JFrame("欢迎使用四则运算训练器，您当前的账户是："+ID);
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

    private static void subjectSet(int level, int methor,JPanel panel,Subjects subjects){
        globle(panel);
        subjects.level = level;
        subjects.methor =methor;
        subjects.subjects();
        subject.setText(subjects.subject);
        panel.repaint();
    }

    private static String answerRegex(String input){
        String s = input;
        if (!input.matches("-?[1-9]\\d*")) {
             s = JOptionPane.showInputDialog("Please input a value");
             s = answerRegex(s);
        }
        return s;
    }

    private static void subjectSet(Subjects subjects){
        if (Integer.parseInt(answerRegex(answers.getText())) != subjects.answer) {
            hiddenLab.setText("WrongAnswer 正确答案：" + subjects.answer);
            hiddenLab.setVisible(true);
            hiddenLab.setLocation(185,240);
            board.add(hiddenLab);
            board.repaint();
//            try {
//                Thread.sleep(3000);
//            }catch (InterruptedException e){
//                System.out.println(e.getMessage());
//            }
        }else{
            hiddenLab.setText("Accepted!!");
            hiddenLab.setVisible(true);
            hiddenLab.setLocation(250,240);
            board.add(hiddenLab);
            board.repaint();
//            try {
//                Thread.sleep(1000);
//            }catch (InterruptedException e){
//                System.out.println(e.getMessage());
//            }
        }
//        hiddenLab.setVisible(false);
//        subjects.subjects();
//        subject.setText(subjects.subject);
//        hiddenLab.setText(""+subjects.answer);
//        board.repaint();

    }

    private static void Subject(JPanel panel,String type){
        subjects = Subjects.getInstance();
        switch (type){
            case "幼  儿":{
                subjectSet(0  ,1,panel,subjects);
            }break;
            case "小学生":{
                subjectSet(100,2,panel,subjects);
            }break;
            case "自定义":{
                globle(panel);
                panel.repaint();
            }
        }
    }

    private static void addButtonActionListener(JButton button){
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                subjectSet(subjects);
            }
        });
    }

    private static void globle(JPanel panel){
        Font font = new Font("Times New Roman",Font.BOLD,30);

        JLabel equal = new JLabel("=");
        JButton next = new JButton("下一题");
        JButton pre  = new JButton("上一题");
        JButton submit = new JButton("提交");
        hiddenLab = new JLabel(""+subjects.answer);
        subject = new JTextField();
        answers = new JTextField(10);

        subject.setEnabled(false);
        subject.setDisabledTextColor(Color.BLACK);
        subject.setSize(200,40);
        answers.setSize(200,40);
        hiddenLab.setSize(600,40);
        equal.setSize(60,30);
        next.setSize(80,30);
        pre.setSize(80,30);
        submit.setSize(80,30);
        hiddenLab.setVisible(false);
        pre.setEnabled(false);

        addButtonActionListener(next);
        addButtonActionListener(submit);
        addButtonActionListener(pre);

        equal.setFont(font);
        subject.setFont(font);
        answers.setFont(font);
        hiddenLab.setFont(new Font("MS Song", Font.BOLD, 30));

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
