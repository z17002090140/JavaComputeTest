import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import javax.swing.*;
import javax.swing.table.TableModel;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
/**
 *通过resource文件夹中POI外部依赖来生成excel（字体、边框、背景色、居中）
 */
public class ExportExcel {
    JTable table;
    FileOutputStream fos;

    /**
     * 待导出的数据和路径
     * @param table Jtable表格
     * @param path 文件保存路径
     */
    public ExportExcel(JTable table,String path){
        this.table = table;
        File file = new File(path);

        try {
            this.fos = new FileOutputStream(file);
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }
    }

    /**
     * 具体的导出方法 设置Excel表格属性等
     */
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

        style1.setFillForegroundColor(HSSFColor.ORANGE.index);//设置导出excel表格背景色
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
                    HSSFRichTextString srts = new HSSFRichTextString(value);
                    HSSFCell hc = hr.createCell((short) j);//已过期的方法
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
