package DTO;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

/**
 * 用于装填表格内容
 */
public class RecordTableModel extends AbstractTableModel {

    private static final long serialVersionUID = 1L;

    private String[] title;

    public RecordTableModel(String[] title) {
        this.title = title;
    }

    private List<RecordDTO>recordDTOS = new ArrayList<RecordDTO>();

    public void setRecordDTOS(List<RecordDTO> dtos){
        this.fireTableDataChanged();
        this.recordDTOS = dtos;
    }
    @Override
    public int getRowCount() {
        return recordDTOS.size();
    }

    @Override
    public int getColumnCount() {
        return 3;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Object obj = null;
        RecordDTO recordDTO = recordDTOS.get(rowIndex);
        switch (columnIndex) {
            case 0:
                obj= recordDTO.getContent();
                break;
            case 1:
                if (recordDTO.getFlag() == 1)
                    obj= "正确";
                else
                    obj= "错误";
                break;
            case 2:
                obj= recordDTO.getCreateTime();
                break;
        }
        return obj;
    }

    public String getColumnName(int column){
        return title[column];}
}
