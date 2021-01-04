import javax.swing.*;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class ResultFrame {
    private static JFrame computeResult;
    //表格
    private static JTable table;
    //标签
    private static JLabel label=new JLabel("讲骚话");
    //保存到本地的按钮
    private static JButton save=new JButton("保存");
    //表头
    public static String name = "结果";
    //标题
    private static String[] columnNames={"编号","内容","结果"};
    //编号
    private static Integer number = 1;

    public ResultFrame(String type){
        computeResult = new JFrame(name);
        JPanel resultPanel = new JPanel();
        resultPanel.setSize(800,600);
        resultPanel.setBackground(Color.lightGray);
        resultPanel.setLocation(0,0);
        computeResult.setSize(800,570);
        computeResult.setLocation((Toolkit.getDefaultToolkit().getScreenSize().width - 800) / 2, (Toolkit.getDefaultToolkit().getScreenSize().height - 600) / 2);
        computeResult.setLayout(null);
        resultPanel.setLayout(null);

        List<Record>list = Record.getRecordList();
        //生成JTable
        int len = list.size();
        Object[][] obj = new Object[len][3];
        for(int i=0;i<len;i++){
            for(int j=0;j<3;j++){
                switch(j){
                    case 0:
                        obj[i][j]=number++;
                        break;
                    case 1:
                        obj[i][j]=list.get(i).getContent()+list.get(i).getAns();
                        break;
                    case 2:
                        if(list.get(i).getFlag()==1)
                            obj[i][j]="正确";
                        else
                            obj[i][j]="错误";
                        break;
                }
            }
        }
        table = new JTable(obj,columnNames);
        table.setSize(600,450);
        table.setPreferredScrollableViewportSize(new Dimension(600,450));
        table.setEnabled(false);
        TableColumn column = null;
        int colunms = table.getColumnCount();
        for(int i=0;i<colunms;i++){
            column = table.getColumnModel().getColumn(i);
            column.setPreferredWidth(200);
        }
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        JScrollPane scroll = new JScrollPane(table);
        scroll.setSize(600,400);
        scroll.setLocation(90,30);

        resultPanel.add(scroll);

        if(type.equals("结果")){
            save.setSize(100,30);
            save.setLocation(345,460);
            //保存按钮的事件
            save.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    //选择文件保存的位置
                    JFileChooser chooser=new JFileChooser();
                    chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                    File file = null;
                    Integer flag=null;
                    try{
                        flag = chooser.showOpenDialog(null);
                    }catch (Exception e1){
                        System.out.println("Error");
                    }
                    if(Objects.equals(flag, JFileChooser.APPROVE_OPTION)){
                        file = chooser.getSelectedFile();
                    }
                    //文件名通过uuid生成
                    String  uuid = UUID.randomUUID().toString().replace("-","");
                    String path = file.getPath()+"\\"+uuid+".xls";
                    ExportExcel exportExcel = new ExportExcel(table,path);
                    exportExcel.export();
                }
            });
        }
        resultPanel.add(save);
        computeResult.add(resultPanel);
    }

    public void showWindows(){
        computeResult.setVisible(true);
    }

    public static JFrame getComputeResult() {
        return computeResult;
    }

    public static JTable getTable() {
        return table;
    }

    public static JButton getSave() {
        return save;
    }

    public static void main(String[] args) {
        new ResultFrame("结果").showWindows();
    }
}
