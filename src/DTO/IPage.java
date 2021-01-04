package DTO;

import java.util.Arrays;
import java.util.List;

//用于接受后端传过来的记录
public class IPage {
    private Integer current;
    private Integer total;
    private Boolean hitCount;
    private Integer pages;
    private Integer size;
    private List<RecordDTO> records;
    private Boolean searchCount;
    private Object[] orders;

    public Integer getCurrent() {
        return current;
    }

    public void setCurrent(Integer current) {
        this.current = current;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Boolean getHitCount() {
        return hitCount;
    }

    public void setHitCount(Boolean hitCount) {
        this.hitCount = hitCount;
    }

    public Integer getPages() {
        return pages;
    }

    public void setPages(Integer pages) {
        this.pages = pages;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public List<RecordDTO> getRecords() {
        return records;
    }

    public void setRecords(List<RecordDTO> records) {
        this.records = records;
    }

    public Boolean getSearchCount() {
        return searchCount;
    }

    public void setSearchCount(Boolean searchCount) {
        this.searchCount = searchCount;
    }

    public Object[] getOrders() {
        return orders;
    }

    public void setOrders(Object[] orders) {
        this.orders = orders;
    }

    public IPage(Integer current, Integer total, Boolean hitCount, Integer pages, Integer size, List<RecordDTO> records, Boolean searchCount, Object[] orders) {
        this.current = current;
        this.total = total;
        this.hitCount = hitCount;
        this.pages = pages;
        this.size = size;
        this.records = records;
        this.searchCount = searchCount;
        this.orders = orders;
    }

    public IPage() {
    }

    @Override
    public String toString() {
        return "IPage{" +
                "current=" + current +
                ", total=" + total +
                ", hitCount=" + hitCount +
                ", pages=" + pages +
                ", size=" + size +
                ", records=" + records +
                ", searchCount=" + searchCount +
                ", orders=" + Arrays.toString(orders) +
                '}';
    }
}
