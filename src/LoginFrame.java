import com.alibaba.fastjson.JSONObject;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class LoginFrame {

    private static JFrame computeLogin;
    private static JTextField username;
    private static JTextField password;
    private static JLabel getreturn;

    public LoginFrame() {
        computeLogin = new JFrame("Login");
        JPanel loginPanel = new JPanel();
        JButton submit = new JButton("Submit");
        username = new JTextField(10);
        password = new JTextField(10);
        JLabel user = new JLabel("User:");
        JLabel pwd  = new JLabel("Pwd:");
        getreturn = new JLabel("");

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
        username.setSize(120,30);
        password.setSize(120,30);
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
        computeLogin.add(loginPanel);
    }

    public void showWindows(){
        computeLogin.setVisible(true);
    }

    private static void addActionListener(JButton button){
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String user = username.getText();
                String pwd  = password.getText();

                String get = API.doPost(API.LoginURL,"username="+user+"&password="+pwd);
                JSONObject strJSON = JSONObject.parseObject(get);
                getreturn.setText(strJSON.getString("data"));

                if(strJSON.getString("msg").equals("成功")){
                    ComputeFrame.ID = strJSON.getString("data");
                    computeLogin.setVisible(false);
                    ComputeFrame a = new ComputeFrame();
                    a.showWindows();
                }
            }
        });
    }

    public static void main(String[] args) {
        new LoginFrame().showWindows();
    }

}
