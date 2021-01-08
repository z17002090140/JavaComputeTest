import java.util.ArrayList;
import java.util.List;

/**
 * 做题记录类
 */
public class Record {
    //题目内容
    private String content;
    //自己输入的题目答案
    private String ans;
    //提交状态（通过或错误）
    private String status;

    private Integer flag;

    public Record(String content, String ans, String status, Integer flag) {
        this.content = content;
        this.ans = ans;
        this.status = status;
        this.flag = flag;
    }

    public Record() {
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAns() {
        return ans;
    }

    public void setAns(String ans) {
        this.ans = ans;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    @Override
    public String toString() {
        return "Record{" +
                "content='" + content + '\'' +
                ", ans='" + ans + '\'' +
                ", status='" + status + '\'' +
                ", flag=" + flag +
                '}';
    }

    //对象数组用来存储做过的题目
    public static List<Record> recordList = new ArrayList<>();

    public static List<Record> getRecordList() {
        return recordList;
    }

    //通过下标来确定访问的内容
    public static Integer index = -1;

    public static Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        Record.index = index;
    }

    /**
     * 本地新增一条做题记录
     * @param record
     */
    public static void add(Record record) {
        index++;
        recordList.add(record);
    }

    /**
     * 获取上一题的做题记录
     * @return Rcord 类
     */
    public static Record getNext() {
        if (index < recordList.size() - 1) {
            index++;
        }
        return recordList.get(index);
    }

    /**
     * 获取下一题的做题记录
     * @return
     */
    public static Record getPre() {
        if (index > 0) {
            index--;
        }
        return recordList.get(index);
    }

}
