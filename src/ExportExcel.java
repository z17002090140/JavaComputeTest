import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;

import javax.swing.*;
import javax.swing.table.TableModel;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

//导出为Excel
public class ExportExcel {

    JTable table;
    FileOutputStream fos;

    public ExportExcel(JTable table,String path){
        this.table = table;
        File file = new File(path);

        try {
            this.fos = new FileOutputStream(file);
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }
    }

    public void export(){
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet hs = wb.createSheet();
        hs.setDefaultColumnWidth(20);
        TableModel tm = table.getModel();
        int row = tm.getRowCount();
        int column = tm.getColumnCount();
        HSSFCellStyle style = wb.createCellStyle();
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);
        style.setFillForegroundColor(HSSFColor.LIGHT_GREEN.index);
        style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        HSSFFont font = wb.createFont();
        font.setFontHeightInPoints((short) 11);
        style.setFont(font);
        HSSFCellStyle style1 = wb.createCellStyle();
        style1.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style1.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        style1.setBorderRight(HSSFCellStyle.BORDER_THIN);
        style1.setBorderTop(HSSFCellStyle.BORDER_THIN);
        style1.setFillForegroundColor(HSSFColor.ORANGE.index);
        style1.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        HSSFFont font1 = wb.createFont();
        font1.setFontHeightInPoints((short) 15);
        font1.setBoldweight((short) 700);
        style1.setFont(font);

        for (int i = 0; i < row + 1; i++) {
            HSSFRow hr = hs.createRow(i);
            for (int j = 0; j < column; j++) {
                if (i == 0) {
                    String value = tm.getColumnName(j);
                    int len = value.length();
//                    hs.setColumnWidth((short) j, (short) (len * 400));
                    HSSFRichTextString srts = new HSSFRichTextString(value);
                    HSSFCell hc = hr.createCell((short) j);
                    //hc.setEncoding((short) 1);
                    hc.setCellStyle(style1);
                    hc.setCellValue(srts);
                } else {
                    if (tm.getValueAt(i - 1, j) != null) {
                        String value = tm.getValueAt(i - 1, j).toString();
                        HSSFRichTextString srts = new HSSFRichTextString(value);
                        HSSFCell hc = hr.createCell((short) j);
                        hc.setCellStyle(style);

                        if (value.equals("") || value == null) {
                            hc.setCellValue(new HSSFRichTextString(""));
                        } else {
                            hc.setCellValue(srts);
                        }
                    }
                }
            }
        }

        try {
            wb.write(fos);
            fos.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
