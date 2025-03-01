import javax.swing.*;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 *显示答案窗口
 */
public class ResultFrame {
    //主体
    private static JFrame computeResult;
    //文件选择框
    private static JFileChooser chooser;
    //表格
    private static JTable table;
    //标签
    private JButton save = new JButton("保存");
    //表头
    public static String name = "结果";
    //标题
    private static final String[] columnNames = {"编号", "内容", "结果"};
    //编号
    private static Integer number = 1;

    public ResultFrame(){

        computeResult = new JFrame(name);
        JPanel resultPanel = new JPanel();
        resultPanel.setSize(800, 600);
        resultPanel.setBackground(Color.lightGray);
        resultPanel.setLocation(0, 0);
        computeResult.setSize(800, 570);
        computeResult.setLocation((Toolkit.getDefaultToolkit().getScreenSize().width - 800) / 2, (Toolkit.getDefaultToolkit().getScreenSize().height - 600) / 2);
        computeResult.setLayout(null);
        resultPanel.setLayout(null);
        List<Record>list = Record.getRecordList();

        //生成JTable
        int len = list.size();
        Object[][] obj = new Object[len][3];
        for (int i = 0; i < len; i++) {
            for (int j = 0; j < 3; j++) {
                switch (j) {
                    case 0:
                        obj[i][j] = number++;
                        break;
                    case 1:
                        obj[i][j] = list.get(i).getContent() + " = " + list.get(i).getAns();
                        break;
                    case 2:
                        if (list.get(i).getFlag() == 1)
                            obj[i][j] = "正确";
                        else
                            obj[i][j] = "错误";
                        break;
                }
            }
        }
        table = new JTable(obj, columnNames);
        table.setSize(600, 450);
        table.setPreferredScrollableViewportSize(new Dimension(600, 450));
        table.setEnabled(false);
        table.setRowHeight(25);
        TableColumn column = null;
        int colunms = table.getColumnCount();
        for (int i = 0; i < colunms; i++) {
            column = table.getColumnModel().getColumn(i);
            column.setPreferredWidth(200);
        }
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        JScrollPane scroll = new JScrollPane(table);
        scroll.setSize(600, 400);
        scroll.setLocation(90, 30);

        resultPanel.add(scroll);

        save.setSize(100, 30);
        save.setLocation(345, 460);
        /**
         * 保存按钮的事件
         */
        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //选择文件保存的位置
                chooser = new JFileChooser();
                //文件选择器的模式为只选文件夹
                chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                File file = null;
                Integer flag = null;
                try {
                    flag = chooser.showOpenDialog(null);
                    if (Objects.equals(flag, JFileChooser.APPROVE_OPTION)) {
                        file = chooser.getSelectedFile();
                    }
                } catch (Exception e1) {
                    System.out.println("Error");
                }
                //文件名通过uuid生成
                String uuid = UUID.randomUUID().toString().replace("-", "");
                if (file != null) {
                    String path = file.getPath() + "\\" + uuid + ".xls";
                    ExportExcel exportExcel = new ExportExcel(table, path);
                    exportExcel.export();
                }
            }
        });
        resultPanel.add(save);
        computeResult.add(resultPanel);

        /**
         * 关闭窗体的事件 仅关闭当前窗体
         */
        computeResult.addWindowListener(new WindowAdapter() {
            //窗体点击关闭时，要做的事
            @Override
            public void windowClosing(WindowEvent e) {
                //结束程序
                computeResult.dispose();
            }
        });
    }

    public void showWindows() {
        computeResult.setVisible(true);

    }

    public static void main(String[] args) {
        new ResultFrame().showWindows();
    }
}
