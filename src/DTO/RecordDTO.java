package DTO;

public class RecordDTO {
    private Long ID;

    private Long userId;

    private String content;

    private Integer flag;

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

    public RecordDTO(Long ID, Long userId, String content, Integer flag) {
        this.ID = ID;
        this.userId = userId;
        this.content = content;
        this.flag = flag;
    }

    public RecordDTO() {
    }

    @Override
    public String toString() {
        return "RecordDTO{" +
                "ID=" + ID +
                ", userId=" + userId +
                ", content='" + content + '\'' +
                ", flag=" + flag +
                '}';
    }
}
