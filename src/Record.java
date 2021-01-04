import java.util.ArrayList;
import java.util.List;

public class Record {
    private String content;

    private String ans;

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

    public static void add(Record record) {
        index++;
        recordList.add(record);
    }

    public static Record getCurrent() {
        return recordList.get(index);
    }

    public static Record getNext() {
        if (index < recordList.size() - 1) {
            index++;
        }
        return recordList.get(index);
    }

    public static Record getPre() {
        if (index > 0) {
            index--;
        }
        return recordList.get(index);
    }

    public static void initRecordList() {
        recordList = new ArrayList<>();
        index = -1;
    }
}
