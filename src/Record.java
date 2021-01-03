import java.util.ArrayList;
import java.util.List;

public class Record {
    private Long userId;

    private String content;

    private String ans;

    private Integer flag;

    public Record(Long userId, String content, String ans, Integer flag) {
        this.userId = userId;
        this.content = content;
        this.flag = flag;
        this.ans = ans;
    }

    public Record() {
    }

    public String getAns() {
        return ans;
    }

    public void setAns(String ans) {
        this.ans = ans;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    //对象数组用来存储做过的题目
    public static List<Record> recordList = new ArrayList<>();

    //通过下标来确定访问的内容
    public static Integer index = 0;

    public static Integer getIndex() {
        return index;
    }

    public static void setIndex(Integer index) {
        Record.index = index;
    }

    public void add(Record record) {
        index++;
        recordList.add(record);
    }

    public Record getCurrent() {
        return recordList.get(index);
    }

    public Record getNext() {
        index++;
        if (index > recordList.size()) {
            index = recordList.size();
        }
        return recordList.get(index);
    }

    public Record getPre() {
        index--;
        if (index < 0) {
            index = 0;
        }
        return recordList.get(index);
    }
}
