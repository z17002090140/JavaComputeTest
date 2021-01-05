import com.alibaba.fastjson.JSONObject;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class CustomDialog {
    private static JLabel methor = new JLabel("符号:");
    private static JLabel times = new JLabel("次数:");
    private static JLabel levels = new JLabel("范围:");

    private static JPanel methorList = new JPanel();
    private static JTextField timesValue = new JTextField();
    private static JTextField levelsValue = new JTextField();
    private static JCheckBox addMethor = new JCheckBox("+");
    private static JCheckBox subMethor = new JCheckBox("-");
    private static JCheckBox mulMethor = new JCheckBox("*");
    private static JCheckBox divMethor = new JCheckBox("/");
    private static JButton submit = new JButton("提交");

    private static JDialog customFrame;

    private static JSONObject code = new JSONObject();
    private static JFrame father;

    public CustomDialog(JFrame f) {
        father = f;
        Font font = new Font("宋体",Font.BOLD,20);
        customFrame = new JDialog(father);
        customFrame.setModal(true);
        customFrame.setLayout(null);
        methorList.setLayout(null);
        customFrame.setSize(370,400);
        customFrame.setLocation((Toolkit.getDefaultToolkit().getScreenSize().width-370)/2,(Toolkit.getDefaultToolkit().getScreenSize().height-400)/2);

        methor.setSize(30,30);
        times.setSize(30,30);
        levels.setSize(30,30);
        methorList.setSize(150,90);
        timesValue.setSize(150,30);
        levelsValue.setSize(150,30);
        addMethor.setSize(40,20);
        subMethor.setSize(40,20);
        mulMethor.setSize(40,20);
        divMethor.setSize(40,20);
        submit.setSize(110,35);

        methor.setLocation(70,50);
        times.setLocation(70,170);
        levels.setLocation(70,210);
        methorList.setLocation(120,50);
        timesValue.setLocation(120,170);
        levelsValue.setLocation(120,210);
        addMethor.setLocation(20,20);
        subMethor.setLocation(90,20);
        mulMethor.setLocation(20,55);
        divMethor.setLocation(90,55);
        submit.setLocation(123,260);

        addMethor.setFont(font);
        subMethor.setFont(font);
        mulMethor.setFont(font);
        divMethor.setFont(font);

        addMethor.setSelected(true);
        subMethor.setSelected(true);
        mulMethor.setSelected(true);
        divMethor.setSelected(true);
        timesValue.setText(""+4);
        levelsValue.setText(""+100);

        Border border = BorderFactory.createBevelBorder(1);
        border = BorderFactory.createTitledBorder(border,"Box");
        methorList.setBorder(border);
        methorList.add(addMethor);
        methorList.add(subMethor);
        methorList.add(mulMethor);
        methorList.add(divMethor);

        customFrame.add(methor);
        customFrame.add(methorList);
        customFrame.add(times);
        customFrame.add(timesValue);
        customFrame.add(levels);
        customFrame.add(levelsValue);
        customFrame.add(submit);

        addActionListener(submit);

        customFrame.setVisible(true);

    }

    private static void addActionListener(JButton button){
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (levelsValue.getText().matches("[1-9]\\d+") && timesValue.getText().matches("[1-9]\\d*") && (
                        addMethor.isSelected() || subMethor.isSelected() ||
                        mulMethor.isSelected() || divMethor.isSelected()
                        )) {
                    String ans = "";
                    if (addMethor.isSelected()) { ans+="+";}
                    if (subMethor.isSelected()) { ans+="-";}
                    if (mulMethor.isSelected()) { ans+="*";}
                    if (divMethor.isSelected()) { ans+="/";}
                    code.put("methor",ans);
                    code.put("level",levelsValue.getText());
                    code.put("times",timesValue.getText());

                    customFrame.dispose();
                }else {
                    JOptionPane.showMessageDialog(null,"Illegal Input");
                }
            }
        });
    }

    public JSONObject getCode() {
        return code;
    }

    public static void main(String[] args) {
//        new CustomDialog();
    }
}
