import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ComputeFrame {
    private static JFrame computeFrame;
    private static JTextField subject;
    private static JTextField answers;
    static String ID = "0";
    private static JPanel board;
    private static Subjects subjects;
    private static JLabel hiddenLab;

    //四个按钮
    private static JButton submit;
    private static JButton next;
    private static JButton pre;
    private static JButton finish;

//    //上一题和下一题按钮的状态
//    private static Boolean nextStatus = false;
//    private static Boolean preStatus = false;

    public void showWindows() {
        computeFrame.setVisible(true);
    }

    public ComputeFrame() {
        computeFrame = new JFrame("欢迎使用四则运算训练器，您当前的账户是：" + ID);
        JMenuBar menuBar = new JMenuBar();
        JMenu diff = new JMenu("难度");
        JMenuItem yer = new JMenuItem("幼  儿");
        JMenuItem xxs = new JMenuItem("小学生");
        JMenuItem zdy = new JMenuItem("自定义");
        board = new JPanel();

        board.setSize(740, 510);
        board.setLocation(20, 30);
        board.setBackground(Color.lightGray);

        computeFrame.setSize(800, 600);
        computeFrame.setLocation((Toolkit.getDefaultToolkit().getScreenSize().width - 800) / 2, (Toolkit.getDefaultToolkit().getScreenSize().height - 600) / 2);
        computeFrame.setLayout(null);

        menuBar.setSize(800, 27);
        menuBar.setLocation(0, 0);
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

    private static void addActionListener(JMenuItem item) {
        item.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Subject(board, item.getText());
            }
        });
    }

    public static void main(String[] args) {
        new ComputeFrame().showWindows();
    }

    private static void subjectSet(int level, int methor, JPanel panel, Subjects subjects) {
        globle(panel);
        subjects.level = level;
        subjects.methor = methor;
        subjects.subjects();
        subject.setText(subjects.subject);
        panel.repaint();
    }

    //提交事件
    private static void subjectSet(Subjects subjects) {
        String str = answers.getText();
        Integer ans = null;
        try {
            ans = Integer.parseInt(str);
        } catch (Exception e) {
            hiddenLab.setText("请输入一个数字作答");
            hiddenLab.setVisible(true);
            hiddenLab.setLocation(185, 240);
            board.add(hiddenLab);
            board.repaint();
        }
        //创建一个Record类 用于在本地记录做下的题目
        Record record = new Record();
        //获取算式和输入的答案
        record.setContent(subjects.subject);
        record.setAns(answers.getText());

        if (ans != null) {
            if (!ans.equals(subjects.answer)) {
                hiddenLab.setText("WrongAnswer 正确答案：" + subjects.answer);
                hiddenLab.setVisible(true);
                hiddenLab.setLocation(185, 240);
                board.add(hiddenLab);
                board.repaint();
                record.setFlag(0);
                record.setStatus("WrongAnswer 正确答案：" + subjects.answer);
            } else {
                hiddenLab.setText("Accepted!!");
                hiddenLab.setVisible(true);
                hiddenLab.setLocation(250, 240);
                board.add(hiddenLab);
                board.repaint();
                record.setFlag(1);
                record.setStatus("Accepted!!");
            }
            answers.setEditable(false);
            submit.setEnabled(false);
        }else{
            hiddenLab.setText("请输入一个数字作答");
            hiddenLab.setVisible(true);
            hiddenLab.setLocation(185, 240);
            board.add(hiddenLab);
            board.repaint();
        }
        Record.add(record);
    }

    private static void Subject(JPanel panel, String type) {
        subjects = Subjects.getInstance();
        switch (type) {
            case "幼  儿": {
                subjectSet(0, 1, panel, subjects);
            }
            break;
            case "小学生": {
                subjectSet(100, 2, panel, subjects);
            }
            break;
            case "自定义": {
                globle(panel);
                panel.repaint();
            }
        }
    }

    private static void globle(JPanel panel) {
        Font font = new Font("Times New Roman", Font.BOLD, 30);

        JLabel equal = new JLabel("=");

        finish = new JButton("交卷");

        next = new JButton("下一题");
        pre = new JButton("上一题");
        submit = new JButton("提交");

        hiddenLab = new JLabel("" + subjects.answer);
        subject = new JTextField();
        answers = new JTextField(10);

        subject.setEnabled(false);
        subject.setDisabledTextColor(Color.BLACK);
        subject.setSize(200, 40);
        answers.setSize(200, 40);
        hiddenLab.setSize(600, 40);
        equal.setSize(60, 30);
        next.setSize(80, 30);
        pre.setSize(80, 30);
        submit.setSize(80, 30);
        finish.setSize(80,30);
        hiddenLab.setVisible(false);
        pre.setEnabled(false);

        //给提交按钮添加事件
        submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                subjectSet(subjects);
                //将上一题按钮置为可用
                pre.setEnabled(true);
            }
        });

        //给下一题按钮添加事件 刷新题目
        next.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //通过判断提交按钮的情况来确定是在浏览过往题目还是正常的点下一题
                if(!submit.isEnabled()&&Record.getIndex().equals(Record.recordList.size()-1)) {
                    //将上一题的结果隐藏
                    hiddenLab.setVisible(false);
                    //设置答案框为可编辑状态 设置答案为空
                    answers.setEditable(true);
                    answers.setText("");
                    //启用提交按钮
                    submit.setEnabled(true);
                    //重新生成题目
                    subjects.subjects();
                    subject.setText(subjects.subject);
                    board.repaint();
                }else{
                    Record record = Record.getNext();
                    pre.setEnabled(true);
                    answers.setText(record.getAns());
                    subject.setText(record.getContent());
                    hiddenLab.setText(record.getStatus());
                    board.repaint();
                }
            }
        });

        //浏览上一题的事件
        pre.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //只有已提交当前题目后才可以点击上一题按钮
                if(!submit.isEnabled()){
                    Record record = Record.getPre();
                    //到达第一条记录时不可以在点击上一题
                    if(Record.getIndex()==0){
                        pre.setEnabled(false);
                    }
                    answers.setText(record.getAns());
                    subject.setText(record.getContent());
                    hiddenLab.setText(record.getStatus());
                    board.repaint();
                }
            }
        });
        //交卷的事件
        finish.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //将上一题，下一题，提交按钮置为不可用
                pre.setEnabled(false);
                next.setEnabled(false);
                submit.setEnabled(false);
                //调用结果窗口
                new ResultFrame().showWindows();
            }
        });
        equal.setFont(font);
        subject.setFont(font);
        answers.setFont(font);
        hiddenLab.setFont(new Font("MS Song", Font.BOLD, 30));

        equal.setLocation(360, 200);
        answers.setLocation(410, 195);
        subject.setLocation(110, 195);
        next.setLocation(440, 350);
        submit.setLocation(330, 350);
        pre.setLocation(220, 350);
        finish.setLocation(550,350);

        panel.setLayout(null);
        panel.add(answers);
        panel.add(subject);
        panel.add(equal);
        panel.add(next);
        panel.add(pre);
        panel.add(submit);
        panel.add(finish);
    }
}
