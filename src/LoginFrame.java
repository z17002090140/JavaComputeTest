import com.alibaba.fastjson.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class LoginFrame {

    private static JFrame computeLogin;
    private static JTextField username;
    private static JTextField password;
    private static JLabel getreturn;

    public LoginFrame() {
        computeLogin = new JFrame("Login");
        JPanel loginPanel = new JPanel();
        JButton submit = new JButton("登录");
        username = new JTextField(10);
        password = new JTextField(10);
        JLabel user = new JLabel("User:");
        JLabel pwd  = new JLabel("Pwd:");
        getreturn = new JLabel("");

        JButton signin = new JButton("注册");
        Font newfont = new Font("宋体",0,12);
        signin.setFont(newfont);
        signin.setBorderPainted(false);
        signin.setFocusPainted(false);
        signin.setBackground(new Color(238, 238, 238));
        signin.setSize(60,20);
        signin.setLocation(110,190);
        signin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                submit.setText("注册");
                username.setText("");
                password.setText("");
                loginPanel.remove(signin);

                submit.removeActionListener(action);
                submit.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String user = username.getText();
                        String pwd  = password.getText();
                        if (user.length() > 5 && pwd.length() > 5) {
                            API.doPost(API.AddUser,"username="+user+"&password="+pwd);
                            Action(user, pwd);
                        }else {
                            JOptionPane.showMessageDialog(computeLogin,"错误的输入！");
                        }
                    }
                });
                loginPanel.repaint();
            }
        });

        computeLogin.setLocation(Toolkit.getDefaultToolkit().getScreenSize().width/2-220,Toolkit.getDefaultToolkit().getScreenSize().height/2-320);
        loginPanel.setSize(200,300);
        loginPanel.setLocation(10,10);
        computeLogin.setSize(220,300);
        computeLogin.setResizable(false);
        loginPanel.setLayout(null);

        submit.setSize(100,30);
        submit.setLocation(60,150);
        addActionListener(submit);

        user.setSize(30,30);
        pwd. setSize(30,30);
        user.setLocation(30,50);
        pwd. setLocation(30,90);
        username.setSize(105,30);
        password.setSize(105,30);
        username.setLocation(70,50);
        password.setLocation(70,90);
        username.setText("admin");
        password.setText("admin");

        getreturn.setSize(120,30);
        getreturn.setLocation(50,180);

        loginPanel.add(user);
        loginPanel.add(pwd);
        loginPanel.add(username);
        loginPanel.add(password);
        loginPanel.add(submit);
        loginPanel.add(getreturn);
        loginPanel.add(signin);
        computeLogin.add(loginPanel);

        computeLogin.addWindowListener(new WindowAdapter() {
            //窗体点击关闭时，要做的事
            @Override
            public void windowClosing(WindowEvent e) {
                //结束程序
                computeLogin.dispose();
            }
        });
    }

    private static void Action(String user, String pwd) {
        String get = API.doPost(API.LoginURL,"username="+user+"&password="+pwd);
        JSONObject strJSON = JSONObject.parseObject(get);
        getreturn.setText(strJSON.getString("data"));

        if(strJSON.getString("msg").equals("成功")){
            ComputeFrame.ID = strJSON.getString("data");
            ComputeFrame.name = user;
            computeLogin.setVisible(false);
            ComputeFrame a = new ComputeFrame();
            a.showWindows();
        }
    }

    public void showWindows(){
        computeLogin.setVisible(true);
    }

    private static ActionListener action;

    private static void addActionListener(JButton button){
         action = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String user = username.getText();
                String pwd  = password.getText();

                Action(user, pwd);
            }
        };
        button.addActionListener(action);
    }

    public static void main(String[] args) {
        new LoginFrame().showWindows();
    }

}
