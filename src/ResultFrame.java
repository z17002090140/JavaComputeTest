import javax.swing.*;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.util.List;

public class ResultFrame {
    private static JFrame computeResult;
    private static JLabel label;
    private static JButton search;

    private static String[] columnNames={"编号","内容","结果"};
    private static Integer number = 1;
    public ResultFrame(){
        computeResult = new JFrame("结果");
        JPanel resultPanel = new JPanel();
        resultPanel.setSize(800,600);
        resultPanel.setBackground(Color.lightGray);
        resultPanel.setLocation(0,0);
        computeResult.setSize(800,600);
        computeResult.setLocation((Toolkit.getDefaultToolkit().getScreenSize().width - 800) / 2, (Toolkit.getDefaultToolkit().getScreenSize().height - 600) / 2);
        computeResult.setLayout(null);
        search = new JButton("查询");

        Record.recordList.add(new Record("1+1=","2","Ac",1));

        Integer len = Record.recordList.size();
        List<Record> list = Record.recordList;
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
        JTable table = new JTable(obj,columnNames);

        TableColumn column = null;
        int colunms = table.getColumnCount();
        for(int i=0;i<colunms;i++){
            column = table.getColumnModel().getColumn(i);
            column.setPreferredWidth(150);
        }
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        JScrollPane scroll = new JScrollPane(table);
        scroll.setSize(800,600);

        resultPanel.add(scroll);
        computeResult.add(resultPanel);
    }

    public void showWindows(){
        computeResult.setVisible(true);
    }
    public static void main(String[] args) {
        new ResultFrame().showWindows();
    }
}
