package com.ismayfly.coins.tools.core.utils;
import java.io.*;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;

@Slf4j
public class ExcelReader {
    private POIFSFileSystem fs;
    private HSSFWorkbook wb;
    private HSSFSheet sheet;
    private HSSFRow row;

    private String fileFullPath;

    private int sheetNo;



    public ExcelReader(String fileFullPath, int sheetNo) {
        super();
        this.fileFullPath = fileFullPath;
        this.sheetNo = sheetNo;
    }

    /**
     * 读取Excel数据内容
     * @param InputStream
     * @param sheetNo sheet 页号
     * @return Map 包含单元格数据内容的Map对象
     */
    public List<Map<String,Object>> readExcel() {
        log.info("开始解析xls...");
        sheetNo--;//从1开始及从0开始
        InputStream is = null;
        try {
            is = new FileInputStream(fileFullPath);
        } catch (FileNotFoundException e1) {
            log.error("",e1);
        }
        Map<String,Object> dataMap = null;
        List<Map<String,Object>> dataList= new ArrayList<>();
        String value = "";
        try {
            fs = new POIFSFileSystem(is);
            wb = new HSSFWorkbook(fs);
        } catch (IOException e) {
            log.error("",e);
        }
        sheet = wb.getSheetAt(sheetNo);
        row = sheet.getRow(0);
        // 标题总列数
        int colNum = row.getPhysicalNumberOfCells();
        String[] keyArray = new String[colNum];
        for (int i = 0; i < colNum; i++) {
            keyArray[i] = getCellFormatValue(row.getCell((short) i));
        }
        int rowNum = sheet.getLastRowNum();
        // 正文内容应该从第二行开始,第一行为表头的标题
        for (int i = 2; i <= rowNum; i++) {
            dataMap= new HashMap<>();
            row = sheet.getRow(i);
            if(row!=null){
                int j = 0;
                while (j < colNum) {
                    //这里把列循环到Map
                    if(row.getCell((short) j)!=null){
                        value = getCellFormatValue(row.getCell((short) j)).trim();
                        dataMap.put(keyArray[j],value);
                    }
                    j++;
                }
                value = "";
                dataList.add(dataMap);
            }
        }
        log.info("解析xls完成...");
        try {
            if(is!=null)
                is.close();
        } catch (IOException e) {
            log.error(e.toString());
        }
        return dataList;
    }

    /**
     * 根据HSSFCell类型设置数据
     * @param cell
     * @return
     */
    private String getCellFormatValue(HSSFCell cell) {
        String cellvalue = "";
        if (cell != null) {
            // 判断当前Cell的Type
            switch (cell.getCellType()) {
                // 如果当前Cell的Type为NUMERIC
                case HSSFCell.CELL_TYPE_NUMERIC:
                case HSSFCell.CELL_TYPE_FORMULA: {
                    // 判断当前的cell是否为Date
                    if (HSSFDateUtil.isCellDateFormatted(cell)) {
                        Date date = cell.getDateCellValue();
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        cellvalue = sdf.format(date);
                    }
                    // 如果是纯数字
                    else {
                        // 取得当前Cell的数值
                        DecimalFormat df = new DecimalFormat("0");
                        String dfStr = df.format(cell.getNumericCellValue());
                        cellvalue = dfStr;
                    }
                    break;
                }
                // 如果当前Cell的Type为STRIN
                case HSSFCell.CELL_TYPE_STRING:
                    // 取得当前的Cell字符串
                    cellvalue = cell.getRichStringCellValue().getString();
                    break;
                // 默认的Cell值
                default:
                    cellvalue = " ";
            }
        } else {
            cellvalue = "";
        }
        return cellvalue;

    }

    public static void main(String[] args) throws IOException {
//        List<Map<String, Object>> dataList;
//        // 对读取Excel表格标题测试
//        ExcelReader excelReader = new ExcelReader("",1);
//        dataList = excelReader.readExcel();
//        for(Map<String,Object> theMap:dataList){
//            System.out.println(theMap);
//        }

//        FileOutputStream  file = new FileOutputStream("");
        FileInputStream fileInputStream = new FileInputStream(new File("/Users/jl/Downloads/MCC2.xls"));
        Workbook workbook = new HSSFWorkbook(fileInputStream);
        Sheet sheet = workbook.getSheetAt(1);

// 获取每行中的字段
//        for (int i = 0; i <= sheet.getLastRowNum(); i++) {
//            Row row = sheet.getRow(i);    // 获取行
//
//            // 获取单元格中的值
//            String studentNum = row.getCell(0).getStringCellValue();
//            String name = row.getCell(1).getStringCellValue();
//            String phone = row.getCell(2).getStringCellValue();
//            System.out.println(studentNum);
//        }
        // 遍历合并区域

        for (int i = 0; i < sheet.getNumMergedRegions(); i++) {
            CellRangeAddress region = sheet.getMergedRegion(i); //
            int colIndex = region.getFirstColumn();// 合并区域首列位置
            int colEnd= region.getLastColumn();// 合并区域首列位置
            int EndRowNumber = region.getLastRow();//
            int firstRowNum = region.getFirstRow();// 合并区域首行位置
            System.out.println("合并区域：first:" +firstRowNum +" end:"+EndRowNumber +" colIndex:"+colIndex + " colEnd:"+colEnd+ " 值:" + sheet.getRow(firstRowNum).getCell(colIndex).getStringCellValue());


        }

        // 直接调用，我知道合并单元格的位置：
        System.out.println(sheet.getRow(0).getCell(0).getStringCellValue());

        System.out.println(sheet.getRow(3).getCell(2).getStringCellValue());

        workbook.close();
        fileInputStream.close();
    }
}
