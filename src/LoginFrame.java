import com.alibaba.fastjson.JSONObject;
import com.sun.deploy.net.HttpRequest;
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

                String get = doPost(DTO.targetURL,"username="+user+"&password="+pwd);
                JSONObject strJSON = JSONObject.parseObject(get);
                getreturn.setText(strJSON.getString("data"));

                if(strJSON.getString("msg").equals("成功")){
                    ComputeFrame.ID = strJSON.getString("data");
                    computeLogin.setVisible(false);
                    new ComputeFrame().showWindows();
                }
            }
        });
    }

    public static void main(String[] args) {
        new LoginFrame().showWindows();
    }

    public static String doPost(String httpUrl, String param) {
        HttpURLConnection connection = null;
        InputStream is = null;
        OutputStream os = null;
        BufferedReader br = null;
        String result = null;
        try {
            URL url = new URL(httpUrl);
            // 通过远程url连接对象打开连接
            connection = (HttpURLConnection) url.openConnection();
            // 设置连接请求方式
            connection.setRequestMethod("POST");
            // 设置连接主机服务器超时时间：15000毫秒
            connection.setConnectTimeout(15000);
            // 设置读取主机服务器返回数据超时时间：60000毫秒
            connection.setReadTimeout(60000);

            // 默认值为：false，当向远程服务器传送数据/写数据时，需要设置为true
            connection.setDoOutput(true);
            // 默认值为：true，当前向远程服务读取数据时，设置为true，该参数可有可无
            connection.setDoInput(true);
            // 设置传入参数的格式:请求参数应该是 name1=value1&name2=value2 的形式。
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            // 设置鉴权信息：Authorization: Bearer da3efcbf-0845-4fe3-8aba-ee040be542c0
            connection.setRequestProperty("Authorization", "Bearer da3efcbf-0845-4fe3-8aba-ee040be542c0");
            // 通过连接对象获取一个输出流
            os = connection.getOutputStream();
            // 通过输出流对象将参数写出去/传输出去,它是通过字节数组写出的
            os.write(param.getBytes());
            // 通过连接对象获取一个输入流，向远程读取
            if (connection.getResponseCode() == 200) {

                is = connection.getInputStream();
                // 对输入流对象进行包装:charset根据工作项目组的要求来设置
                br = new BufferedReader(new InputStreamReader(is, "UTF-8"));

                StringBuffer sbf = new StringBuffer();
                String temp = null;
                // 循环遍历一行一行读取数据
                while ((temp = br.readLine()) != null) {
                    sbf.append(temp);
                    sbf.append("\r\n");
                }
                result = sbf.toString();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 关闭资源
            if (null != br) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != os) {
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != is) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            // 断开与远程地址url的连接
            connection.disconnect();
        }
        return result;
    }
}
