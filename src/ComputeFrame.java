import DTO.RecordDTO;
import com.alibaba.fastjson.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

//计算界面的主体
public class ComputeFrame {
    private static JFrame computeFrame;
    //计算的式子
    private static JTextField subject;
    //答案输入框
    private static JTextField answers;
    //ID
    static String ID = "0";
    static String name = null;
    private static JPanel board;
    private static Subjects subjects;
    private static JLabel hiddenLab;
    private static JMenu diff;

    //四个按钮
    private static JButton submit;
    private static JButton next;
    private static JButton pre;
    private static JButton finish;
    public static JSONObject object;

    //控件加载判断开关
    private static boolean flag = false;
    private static boolean key = true;

    /**
     * 显示窗体
     */
    public void showWindows() {
        computeFrame.setVisible(true);
    }

    /**
     * 初始化窗体 用于重新选择难度后一些控件的初始化
     */
    public static void initFrame() throws NullPointerException{
        //提示label默认不显示
        hiddenLab.setVisible(false);
        //上一题和下一题按钮默认不可用
        pre.setEnabled(false);
        next.setEnabled(false);
        //提交按钮默认可用
        submit.setEnabled(true);
        //答案输入框内容置空
        answers.setText("");
        //答案输入框默认可用
        answers.setEnabled(true);
        //交卷按钮默认可用
        finish.setEnabled(true);

        board.repaint();
        //记录数组初始化
        //Record.initRecordList();
    }

    public ComputeFrame() {
        computeFrame = new JFrame("欢迎使用四则运算训练器，您当前的账户是：" + name);
        JMenuBar menuBar = new JMenuBar();

        JButton result = new JButton("查看历史");
        result.setBorderPainted(false);
        result.setFocusPainted(false);
        result.setBackground(Color.lightGray);
        result.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SearchFrame searchFrame = new SearchFrame();
                searchFrame.showWindows();
            }
        });

        diff = new JMenu("难度:");
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
        menuBar.add(result);
        computeFrame.add(menuBar);
        computeFrame.add(board);

        computeFrame.addWindowListener(new WindowAdapter() {
            //窗体点击关闭时，要做的事
            @Override
            public void windowClosing(WindowEvent e) {
                //结束程序
                System.exit(0);
            }
        });
    }

    private static void addActionListener(JMenuItem item) {
        item.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                diff.setText("难度:  " + item.getText());
                Subject(board, item.getText());
                if (key) {
                    diff.setEnabled(false);
                }else {
                    key = true;
                }
            }
        });
    }

    private static void subjectSet(int level, int methor, JPanel panel, Subjects subjects) {
        globle(panel);
        subjects.level = level;
        subjects.methor = methor;
        subjects.subjects();
        subject.setText(subjects.subject);
        panel.repaint();
    }

    private static void subjectSet(int level, int methor,String banMessage, JPanel panel, Subjects subjects) {
        globle(panel);
        subjects.level = level;
        subjects.methor = methor;
        subjects.banMes = banMessage;
        subjects.subjects();
        subject.setText(subjects.subject);
        panel.repaint();
    }

    //提交事件
    private static void subjectSet(Subjects subjects) {
        String str = answers.getText();
        System.out.println(str);
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
        //recordDTO类用于与后端继续交互
        RecordDTO recordDTO = new RecordDTO();
        recordDTO.setUserId(Long.parseLong(ID));

        if (ans != null) {
            //获取算式和输入的答案
            record.setContent(subjects.subject);
            record.setAns(answers.getText());
            recordDTO.setContent(subjects.subject+"="+answers.getText());
            if (!ans.equals(subjects.answer)) {
                hiddenLab.setText("WrongAnswer 正确答案：" + subjects.answer);
                hiddenLab.setVisible(true);
                hiddenLab.setLocation(185, 240);
                board.add(hiddenLab);
                board.repaint();
                record.setFlag(0);
                recordDTO.setFlag(0);
                record.setStatus("WrongAnswer 正确答案：" + subjects.answer);
            } else {
                hiddenLab.setText("Accepted!!");
                hiddenLab.setVisible(true);
                hiddenLab.setLocation(250, 240);
                board.add(hiddenLab);
                board.repaint();
                record.setFlag(1);
                recordDTO.setFlag(1);
                record.setStatus("Accepted!!");
            }
            answers.setEnabled(false);
            submit.setEnabled(false);
            //将上一题按钮置为可用
            pre.setEnabled(true);
            next.setEnabled(true);

            Record.add(record);
            addRecord(recordDTO);
        }else{
            hiddenLab.setText("请输入一个数字作答");
            hiddenLab.setVisible(true);
            hiddenLab.setLocation(185, 240);
            board.add(hiddenLab);
            board.repaint();
        }
    }
    /**
     * 往后端新增一条记录
     * @param recordDTO recordDTO对象
     */
    private static void addRecord(RecordDTO recordDTO){
        API.doPost(API.AddRecord,"userId="+recordDTO.getUserId()+"&content="+recordDTO.getContent()+"&flag="+recordDTO.getFlag());

    }

    private static void Subject(JPanel panel, String type) {
        subjects = Subjects.getInstance();

        switch (type) {
            case "幼  儿": {
                subjectSet(0, 1, panel, subjects);
                initFrame();
            }
            break;
            case "小学生": {
                subjectSet(100, 2, panel, subjects);
                initFrame();
            }
            break;
            case "自定义": {
                CustomDialog customDialog = new CustomDialog(computeFrame);
                if (!customDialog.getCode().isEmpty()){
                    object = customDialog.getCode();
                    subjectSet(object.getIntValue("level"),object.getIntValue("times"),object.getString("methor"),panel,subjects);
                    panel.repaint();
                    initFrame();
                }else{
                    key = false;
                }
            }
        }
    }

    private static void globle(JPanel panel) {
        if (!flag) {
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
            answers.setDisabledTextColor(Color.BLACK);
            hiddenLab.setSize(600, 40);
            equal.setSize(60, 30);
            next.setSize(80, 30);
            pre.setSize(80, 30);
            submit.setSize(80, 30);
            finish.setSize(80,30);

            //给提交按钮添加事件
            submit.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    subjectSet(subjects);
                }
            });

            /**
             * 下一题按钮的事件
             * 如果提交按钮为不可用状态并且记录数组的当前索引等于记录数组的总长度减1 进行下一个题目的渲染 否则的话认为是在浏览过往题目，进行的上下题切换
             * 将记录数组的下标往下移 渲染题目
             *
             */
            next.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

                    if(!submit.isEnabled()&&Record.getIndex().equals(Record.recordList.size()-1)) {
                        //将上一题的结果隐藏
                        hiddenLab.setVisible(false);
                        //设置答案框为可编辑状态 设置答案为空
                        answers.setEnabled(true);
                        answers.setText("");
                        //启用提交按钮
                        submit.setEnabled(true);
                        //重新生成题目
                        subjects.subjects();
                        subject.setText(subjects.subject);
                        board.repaint();
                    }else if(next.isEnabled()==!submit.isEnabled()){
                        try{
                            Record record = Record.getNext();
                            pre.setEnabled(true);
                            answers.setText(record.getAns());
                            subject.setText(record.getContent());
                            hiddenLab.setText(record.getStatus());
                            board.repaint();
                        }catch (Exception exception){
                            System.out.println("Error");
                        }
                    }else if (submit.isEnabled()==next.isEnabled()){
                        hiddenLab.setText("请先提交再点击下一题");
                        hiddenLab.setVisible(true);
                    }
                }
            });

            /**
             * 上一题按钮的事件
             * 如果提交不可用 则可以点击上一题按钮，如果记录的当前索引等于0 那么上一题按钮不可用 点击完上一题按钮后根据记录的下标
             * 从记录的数组中读取对应的题目内容进行渲染
             */
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

            /**
             * 交卷按钮的事件
             * 将
             */
            finish.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    //输入框不在可以编辑
                    answers.setEnabled(false);
                    subject.setEnabled(false);
                    hiddenLab.setVisible(false);

                    //将上一题，下一题，提交按钮置为不可用
                    pre.setEnabled(false);
                    next.setEnabled(false);
                    submit.setEnabled(false);
                    //调用结果窗口
                    new ResultFrame("结果").showWindows();
                    //允许新游戏开始
                    diff.setText("难度:");
                    diff.setEnabled(true);
                    answers.setEnabled(true);
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

            flag = true;
        }
        else {
            subject.setText("");
            answers.setText("");
            answers.setEnabled(true);
        }
    }

    public static void main(String[] args) {
        ComputeFrame a = new ComputeFrame();
        a.showWindows();
    }

}
