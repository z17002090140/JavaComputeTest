package DTO;
/**
 * 用于接受后端返回的数据
 */
public class RecordDTO {
    private Long ID;

    private Long userId;

    private String content;

    private Integer flag;

    private String createTime;

    private String updateTime;

    public RecordDTO(Long ID, Long userId, String content, Integer flag, String createTime, String updateTime) {
        this.ID = ID;
        this.userId = userId;
        this.content = content;
        this.flag = flag;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public RecordDTO() {
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

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * tostring方法
     * @return
     */
    @Override
    public String toString() {
        return "RecordDTO{" +
                "ID=" + ID +
                ", userId=" + userId +
                ", content='" + content + '\'' +
                ", flag=" + flag +
                ", createTime='" + createTime + '\'' +
                ", updateTime='" + updateTime + '\'' +
                '}';
    }
}
