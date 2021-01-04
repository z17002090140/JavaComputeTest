import javax.swing.*;

//查询界面 分页查询
public class SearchFrame extends ResultFrame {
    //上一页
    private static JButton nextPage=new JButton("上一页");
    //下一页
    private static JButton prePage=new JButton("下一页");
    //查询按钮
    private static JButton search=new JButton("刷新");
    //默认分页参数
    private static Integer current=1;
    private static Integer size = 15;

    private JFrame thisFrame;
    private JTable thisTable;

    public SearchFrame(String type) {
        super(type);
        thisFrame = SearchFrame.getComputeResult();
        thisTable = SearchFrame.getTable();

        if (type.equals("查询")) {
            nextPage.setSize(100,30);
            prePage.setSize(100,30);
            search.setSize(100,30);

            nextPage.setLocation(465,460);
            prePage.setLocation(225,460);
            search.setLocation(345,460);

            thisFrame.add(nextPage);
            thisFrame.add(prePage);
            thisFrame.add(search);
        }
    }

    public static void main(String[] args) {
        SearchFrame.name = "Search";

        new SearchFrame("查询").showWindows();
    }
}
