package com.ismayfly.coins.tools.controller;

import com.ismayfly.coins.tools.core.response.HandleResponse;
import com.ismayfly.coins.tools.model.po.McShopCodeMapping;
import com.ismayfly.coins.tools.model.po.MdSocialInformation;
import com.ismayfly.coins.tools.model.po.MpPayConfigBank;
import com.ismayfly.coins.tools.model.query.BankMCCInfoQuery;
import com.ismayfly.coins.tools.service.JdbcTemplateService;
import com.ismayfly.coins.tools.service.McShopCodeMappingService;
import com.ismayfly.coins.tools.service.MdSocialInformationService;
import com.ismayfly.coins.tools.service.MpPayConfigBankService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;


@Slf4j
@RestController()
public class ParsController {

    @Autowired
    private McShopCodeMappingService mcShopCodeMappingService;

    @Autowired
    private MpPayConfigBankService mpPayConfigBankService;

    @Autowired
    private MdSocialInformationService mdSocialInformationService;

    @Autowired
    private JdbcTemplateService jdbcTemplateService;
    /**
     * 增加银行映射关系文件
     * @return
     */
    @PostMapping("/addBankMcc")
    public HandleResponse addBankMCCInfo(@RequestBody BankMCCInfoQuery bankMCCInfoQuery){
        //切割mcc
        String[] strList = bankMCCInfoQuery.getMccStr().split(",");
        for(String str:strList){
            MpPayConfigBank mpPayConfigBank = new MpPayConfigBank();
            mpPayConfigBank.setCardName(bankMCCInfoQuery.getBankName());
            mpPayConfigBank.setCode(Integer.parseInt(str));
            mpPayConfigBank.setCardType(bankMCCInfoQuery.getCardType());
//            mpPayConfigBank.setCodeType(bankMCCInfoQuery.getType());
            mpPayConfigBankService.save(mpPayConfigBank);
        }

        return  HandleResponse.successResponse("");

    }

    @GetMapping("/file")
    public HandleResponse parsingDataFile(@RequestParam String filePath){
        try{
            File file = new File(filePath);
            byte[] temp = new byte[0];
            FileChannel fileChannel = new RandomAccessFile(file, "r").getChannel();
            //一次性读取字节的长度
            ByteBuffer byteBuffer = ByteBuffer.allocate(1000000);
            List<String> dataList = new ArrayList<>();//存储读取的每行数据
            byte[] lineByte = new byte[0];
            Integer cut = 0;

            //从文件管道读取内容到缓冲区(byteBuffer)
            while (fileChannel.read(byteBuffer) != -1) {

                List<MdSocialInformation> list = new ArrayList<MdSocialInformation>();
                int size = byteBuffer.position();//读取结束后的位置，相当于读取的长度
                byte[] bs = new byte[size];//用来存放读取的内容的数组
                byteBuffer.rewind();//将position设回0,所以你可以重读Buffer中的所有数据,此处如果不设置,无法使用下面的get方法
                byteBuffer.get(bs);//byteBuffer.get(bs,0,bs.length())：从position初始位置开始相对读,读bs.length个byte,并写入bs[0]到bs[bs.length-1]的区域
                byteBuffer.clear();

                log.info("开始第:{}次循环，每次循环数量:{}",++cut,size);
                int startNum = 0;
                int LF = 10;//换行符
                int CR = 13;//回车符
                boolean hasLF = false;//是否有换行符
                for (int i = 0; i < size; i++) {
                    if (bs[i] == LF) {
                        hasLF = true;
                        int tempNum = temp.length;
                        int lineNum = i - startNum;
                        lineByte = new byte[tempNum + lineNum];//数组大小已经去掉换行符

                        System.arraycopy(temp, 0, lineByte, 0, tempNum);//填充了lineByte[0]~lineByte[tempNum-1]
                        temp = new byte[0];
                        System.arraycopy(bs, startNum, lineByte, tempNum, lineNum);//填充lineByte[tempNum]~lineByte[tempNum+lineNum-1]

                        String line = new String(lineByte, 0, lineByte.length);//一行完整的字符串(过滤了换行和回车)
                        MdSocialInformation information = strToDate(line);
                        if(information != null){
                            list.add(information);

                        }
                        //过滤回车符和换行符
                        if (i + 1 < size && bs[i + 1] == CR) {
                            startNum = i + 2;
                        } else {
                            startNum = i + 1;
                        }

                    }
                }
                //批量保存
                jdbcTemplateService.batchSaveWbDate(list);

                if (hasLF) {
                    temp = new byte[bs.length - startNum];
                    System.arraycopy(bs, startNum, temp, 0, temp.length);
                } else {//兼容单次读取的内容不足一行的情况
                    byte[] toTemp = new byte[temp.length + bs.length];
                    System.arraycopy(temp, 0, toTemp, 0, temp.length);
                    System.arraycopy(bs, 0, toTemp, temp.length, bs.length);
                    temp = toTemp;
                }

            }
            if (temp != null && temp.length > 0) {//兼容文件最后一行没有换行的情况
                String line = new String(temp, 0, temp.length);
                //逻辑
                mdSocialInformationService.save(strToDate(line));
            }
            fileChannel.close();
            fileChannel.close();
        }catch (Exception e){
            log.info("处理数据失败",e);
        }
        return HandleResponse.successResponse("");
    }

    private MdSocialInformation strToDate(String str){
        try{
            String phone =  str.substring(0,11).trim();
            String uid = str.substring(12).trim();
            MdSocialInformation mdSocialInformation = new MdSocialInformation();
            mdSocialInformation.setMobile(phone);
            mdSocialInformation.setWbUid(Long.parseLong(uid));
            mdSocialInformation.setWbUrl("https://weibo.com/u/"+uid);
            return mdSocialInformation;
        }catch (Exception e){
            log.info("系统异常:{}",str,e);
        }
        return  null;
    }


    /**
     * 解析MCC映射关系
     * @return
     * @throws IOException
     */
    @PostMapping("/excel")
    public HandleResponse parsingExcel() throws IOException {
        List<McShopCodeMapping> resultList = new ArrayList<McShopCodeMapping>();
        FileInputStream fileInputStream;
        Workbook workbook;
        Sheet sheet;
        try {
            fileInputStream = new FileInputStream(new File("/Users/jl/Downloads/MCC2.xls"));
            workbook = new HSSFWorkbook(fileInputStream);
            sheet = workbook.getSheetAt(1);
            String[][] cateList = new String[277][3];//合并类的值

            //遍历合并项目
            for (int i = 0; i < sheet.getNumMergedRegions(); i++) {
                CellRangeAddress region = sheet.getMergedRegion(i); //
                int colIndex = region.getFirstColumn();// 合并区域首列位置
                int colEnd = region.getLastColumn();
                int endRowNumber = region.getLastRow();//
                int firstRowNum = region.getFirstRow();// 合并区域首行位置
                String value = sheet.getRow(firstRowNum).getCell(colIndex).getStringCellValue();
                if (firstRowNum == 0 && colIndex == 0) {
                    continue;
                }
                while (colIndex <= colEnd) {
                    while (firstRowNum <= endRowNumber) {
                        cateList[firstRowNum][colEnd] = value;
                        firstRowNum++;
                    }
                    colIndex++;
                }
            }
            //全量遍历
            for (int i = 2; i <= sheet.getLastRowNum(); i++) {
                McShopCodeMapping mcShopCodeMapping = new McShopCodeMapping();

                Row row = sheet.getRow(i);    // 获取行
                // 获取单元格中的值
                Double mccCode = row.getCell(3).getNumericCellValue();
                String shopName = row.getCell(4).getStringCellValue();
                String desc = row.getCell(5).getStringCellValue();
                String mainCategory = cateList[i][0] != null ? cateList[i][0] : row.getCell(0).getStringCellValue();
                String secondaryCategory = cateList[i][1] != null ? cateList[i][1] : row.getCell(1).getStringCellValue();
                String categories = cateList[i][2] != null ? cateList[i][2] : row.getCell(2).getStringCellValue();
                mcShopCodeMapping.setMainCategory(mainCategory);
                mcShopCodeMapping.setSecondaryCategory(secondaryCategory);
                mcShopCodeMapping.setCategories(categories);
                mcShopCodeMapping.setCode(mccCode.intValue());
                mcShopCodeMapping.setShopDetails(desc);
                mcShopCodeMapping.setCodeValue(shopName);
                resultList.add(mcShopCodeMapping);
            }
            fileInputStream.close();
            workbook.close();
        } catch (Exception e) {
            log.error("red excl faild", e);
        }
        for (McShopCodeMapping mcShopCodeMapping : resultList) {
            mcShopCodeMappingService.save(mcShopCodeMapping);
        }
        return HandleResponse.successResponse("");
    }
}
