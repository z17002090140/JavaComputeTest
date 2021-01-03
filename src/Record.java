import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Record {
    private Long ID;

    private Long userId;

    private String content;

    private Integer flag;

    public Record(Long ID, Long userId, String content, Integer flag) {
        this.ID = ID;
        this.userId = userId;
        this.content = content;
        this.flag = flag;
    }

    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
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

    private List<Record> recordList = new ArrayList<>();
    private Integer index = 0;

    public void add(Record record) {
        recordList.add(record);
    }

    public Record getCurrent() {
        return recordList.get(index);
    }

    public Integer getIndex() { return index; }

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
