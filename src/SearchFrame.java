import DTO.IPage;
import DTO.RecordDTO;
import DTO.RecordTableModel;
import DTO.Response;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import javax.swing.*;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.*;
import java.util.List;

//查询界面 分页查询
public class SearchFrame {
    private static JFrame thisFrame;
    private static JScrollPane scroll;
    //标题
    private static final String[] columnNames = {"内容", "结果", "完成时间"};

    static final RecordTableModel recordTableModel = new RecordTableModel(columnNames);
    private static JPanel resultPanel;
    //上一页
    private static JButton nextPage = new JButton("下一页");
    //下一页
    private static JButton prePage = new JButton("上一页");
    //查询按钮
    private static JButton refresh = new JButton("刷新");
    //默认分页参数
    private static Integer current = 1;
    private static Integer size = 10;
    private static Integer total = null;
    private static Integer pages = null;


    //用于界面的渲染用
    private static JTable thisTable = new JTable();
    //用于导出时使用，导出所有数据，所以不能分页进行导出，要重新加载一个table
    private static JTable ExcelTable;
    private JButton save;

    public SearchFrame() {
        thisFrame = new JFrame("查询");
        save = new JButton("保存");

        resultPanel = new JPanel();
        resultPanel.setSize(800, 600);
        resultPanel.setBackground(Color.lightGray);
        resultPanel.setLocation(0, 0);
        thisFrame.setSize(800, 570);
        thisFrame.setLocation((Toolkit.getDefaultToolkit().getScreenSize().width - 800) / 2, (Toolkit.getDefaultToolkit().getScreenSize().height - 600) / 2);
        thisFrame.setLayout(null);
        resultPanel.setLayout(null);

        List<RecordDTO> recordDTOS = getRecordByUserId().getRecords();

        changeCurrent(recordDTOS);

        scroll = new JScrollPane(thisTable);
        scroll.setSize(600, 400);
        scroll.setLocation(90, 30);

        thisFrame.add(scroll);

        nextPage.setSize(100, 30);
        prePage.setSize(100, 30);
        refresh.setSize(100, 30);
        save.setSize(100, 30);

        prePage.setLocation(200, 460);
        refresh.setLocation(300, 460);
        save.setLocation(400, 460);
        nextPage.setLocation(500, 460);

        thisFrame.add(nextPage);
        thisFrame.add(prePage);
        thisFrame.add(refresh);
        thisFrame.add(save);

        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //生成一个全部数据的JTable
                getTable(getAllRecords());
                //选择文件保存的位置
                JFileChooser chooser = new JFileChooser();
                chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                File file = null;
                Integer flag = null;
                try {
                    flag = chooser.showOpenDialog(null);
                } catch (Exception e1) {
                    System.out.println("Error");
                }
                if (Objects.equals(flag, JFileChooser.APPROVE_OPTION)) {
                    file = chooser.getSelectedFile();
                }
                //文件名通过uuid生成
                String uuid = UUID.randomUUID().toString().replace("-", "");
                if (file != null) {
                    String path = file.getPath() + "\\" + uuid + ".xls";
                    ExportExcel exportExcel = new ExportExcel(ExcelTable, path);
                    exportExcel.export();
                }
            }
        });

        nextPage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (current.equals(pages) || current.compareTo(pages) > 0) {
                    System.out.println("最后一页了");
                } else {
                    current++;
                    IPage page = getRecordByUserId();
                    changeCurrent(page.getRecords());
                }
            }
        });

        prePage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (current.equals(1) || current.compareTo(1) < 0) {
                    System.out.println("已经第一页了");
                } else {
                    current--;
                    IPage page = getRecordByUserId();
                    changeCurrent(page.getRecords());
                }
            }
        });

        refresh.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                current = 1;
                size = 10;
                IPage page = getRecordByUserId();
                changeCurrent(page.getRecords());
            }
        });

        thisFrame.addWindowListener(new WindowAdapter() {
            //窗体点击关闭时，要做的事
            @Override
            public void windowClosing(WindowEvent e) {
                //结束程序
                thisFrame.dispose();
            }
        });
    }

    //生成一个JTable用来导出全部数据到excel用
    public static void getTable(List<RecordDTO> recordDTOS) {
        List<RecordDTO> list = recordDTOS;
        //生成JTable
        int len = list.size();
        Object[][] obj = new Object[len][3];
        for (int i = 0; i < len; i++) {
            for (int j = 0; j < 3; j++) {
                switch (j) {
                    case 0:
                        obj[i][j] = recordDTOS.get(i).getContent();
                        break;
                    case 1:
                        if (recordDTOS.get(i).getFlag() == 1)
                            obj[i][j] = "正确";
                        else
                            obj[i][j] = "错误";
                        break;
                    case 2:
                        obj[i][j] = recordDTOS.get(i).getCreateTime();
                        break;
                }
            }
        }
        ExcelTable = new JTable(obj, columnNames);
        ExcelTable.setSize(600, 450);
        ExcelTable.setPreferredScrollableViewportSize(new Dimension(600, 450));
        ExcelTable.setEnabled(false);
        TableColumn column = null;
        int columns = ExcelTable.getColumnCount();
        for (int i = 0; i < columns; i++) {
            column = ExcelTable.getColumnModel().getColumn(i);
            column.setPreferredWidth(200);
        }
        ExcelTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
    }

    public static void changeCurrent(List<RecordDTO> recordDTOS) {
        recordTableModel.setRecordDTOS(recordDTOS);
        thisTable.setModel(recordTableModel);
    }

    public static IPage getRecordByUserId() {
        String userId = ComputeFrame.ID;
        String res = API.doPost(API.FindRecordByUserId, "userId=" + userId + "&current=" + current + "&size=" + size);
        System.out.println(res);
        IPage page = JSONObject.parseObject(res, Response.class).getData();
        System.out.println(page.getTotal());
        total = page.getTotal();
        pages = page.getPages();
        return page;
    }

    public static List<RecordDTO> getAllRecords() {
        String userId = ComputeFrame.ID;
        String res = API.doPost(API.FindAllRecord, "userId=" + userId);
        System.out.println(res);
        JSONObject obj = JSONObject.parseObject(res);
        List<RecordDTO> recordDTOS = JSONArray.parseArray(obj.get("data").toString(), RecordDTO.class);
        return recordDTOS;
    }

    public void showWindows() {
        thisFrame.setVisible(true);
    }


    public static void main(String[] args) {
        SearchFrame searchFrame = new SearchFrame();
        searchFrame.showWindows();
    }

}
