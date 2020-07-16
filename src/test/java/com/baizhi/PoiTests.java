package com.baizhi;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.GetObjectRequest;
import com.baizhi.dao.UserMapper;
import com.baizhi.entity.User;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest
public class PoiTests {

    @Resource
    private UserMapper userMapper;


    //POI导出Excel
    @Test
    public void test1(){

        //创建一个Excel文档
        HSSFWorkbook workbook = new HSSFWorkbook();

        //创建一个sheet  参数:表名字
        HSSFSheet sheet = workbook.createSheet("应学用户信息表");
//第一行操作
        CellRangeAddress cellAddresses = new CellRangeAddress(0,0,0,8); //单元格合并
        sheet.addMergedRegion(cellAddresses);  //将合并样式赋值给sheet
        HSSFRow titleRow = sheet.createRow(0);  //创建第一行
        titleRow.setHeight((short)(30*20));          //设置行高
        HSSFCell titleRowCellCell = titleRow.createCell(0); //创建单元格
        titleRowCellCell.setCellValue("应学用户信息表");  //单元格赋值

        HSSFFont titleFont = workbook.createFont();  //创建字体样式
        titleFont.setBold(true);  //加粗
        titleFont.setFontName("宋体"); //字体名字
        titleFont.setColor(Font.COLOR_RED); //字体颜色
        titleFont.setFontHeightInPoints((short)24);  //字体大小

        HSSFCellStyle titleCellStyle = workbook.createCellStyle(); //创建单元格样式
        titleCellStyle.setAlignment(HorizontalAlignment.CENTER);  //单元格字体居住
        titleCellStyle.setFont(titleFont);  //字体样式赋值单元格样式
        titleRowCellCell.setCellStyle(titleCellStyle);  //标题加上单元格样式

//第二行操作
        HSSFRow twoRow = sheet.createRow(1);
        twoRow.setHeight((short)(30*20));  //设置行高
        //创建标题数组
        String[] head = {"ID","手机号","昵称","头像","简介","微信","注册时间","状态"};
        HSSFFont twoRowFont = workbook.createFont();
        twoRowFont.setBold(true);
        twoRowFont.setFontName("宋体");
        twoRowFont.setFontHeightInPoints((short)18);
        twoRowFont.setColor(Font.COLOR_NORMAL);
        HSSFCellStyle twoRowCellStyle = workbook.createCellStyle();
        twoRowCellStyle.setAlignment(HorizontalAlignment.CENTER);
        twoRowCellStyle.setFont(twoRowFont);
        //遍历赋值
        for (int i = 0; i < head.length; i++) {
            sheet.setColumnWidth(i,20*256);//0-7设置列宽
            //创建单元格
            HSSFCell cell = twoRow.createCell(i);
            //添加单元格样式
            cell.setCellStyle(twoRowCellStyle);
            //给单元格赋值
            cell.setCellValue(head[i]);
        }


//查询数据遍历赋值
        List<User> list = userMapper.selectAll();
        //设置id列宽
        sheet.setColumnWidth(0,35*256);
        sheet.setColumnWidth(3,92*256);
        //设置时间日期格式
        HSSFDataFormat dataFormat = workbook.createDataFormat();
        HSSFCellStyle dataCellStyle = workbook.createCellStyle();
        dataCellStyle.setDataFormat(dataFormat.getFormat("yyyy年MM月dd日"));
        for (int i = 0; i < list.size(); i++) {
            HSSFRow row = sheet.createRow(i+2);
            row.createCell(0).setCellValue(list.get(i).getId());
            row.createCell(1).setCellValue(list.get(i).getPhone());
            row.createCell(2).setCellValue(list.get(i).getUsername());
            row.createCell(3).setCellValue(list.get(i).getHeadImg());
            row.createCell(4).setCellValue(list.get(i).getBrief());
            row.createCell(5).setCellValue(list.get(i).getWechat());
            HSSFCell dateCell = row.createCell(6);
            dateCell.setCellStyle(dataCellStyle);
            dateCell.setCellValue(list.get(i).getCreateDate());
            row.createCell(7).setCellValue(list.get(i).getStatus());
        }

        //输出文档
        try {
            workbook.write(new FileOutputStream(new File("D:\\EndItem\\12.xls")));
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    //POI导入Excel
    @Test
    public void test2(){
       //选择要导入的文件
        HSSFWorkbook workbook =null;
        try {
            workbook = new HSSFWorkbook(new FileInputStream(new File("D:\\EndItem\\12.xls")));
        } catch (IOException e) {
            e.printStackTrace();
        }

        //选择文件中的表单
        HSSFSheet sheet = workbook.getSheet("应学用户信息表");

        for (int i = 0; i < sheet.getLastRowNum(); i++) {

            HSSFRow row = sheet.getRow(2);

            HSSFCell cell = row.getCell(0);

            System.out.println("====="+cell.getStringCellValue());
        }
    }

    @Test
    public void test3(){

        //将阿里云的中的用户头像下载到本地


        List<User> list = userMapper.selectAll();
        ExportParams exportParams = new ExportParams("用户基本信息", "应学用户表");
        Workbook workbook = ExcelExportUtil.exportExcel( exportParams,User.class, list);

        try {
            workbook.write(new FileOutputStream(new File("D:\\EndItem\\1.xls")));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void test4(){
        // Endpoint以杭州为例，其它Region请按实际情况填写。
        String endpoint = "http://oss-cn-qingdao.aliyuncs.com";
// 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建RAM账号。
        String accessKeyId = "LTAI4G7kj7Hv38nYdySmeMYk";
        String accessKeySecret = "vjJR27b9AKPYnQX2OdTD7UWbXE06lm";
        String bucketName = "yingx-liub";
//<yourObjectName>表示从OSS下载文件时需要指定包含文件后缀在内的完整路径，例如abc/efg/123.jpg。
        String objectName = "userHeadImg/1594266510664-timgOBS8CQSB.jpg";
// 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
// 下载OSS文件到本地文件。如果指定的本地文件存在会覆盖，不存在则新建。
        //创建本地文件夹
        File file = new File("/upload/photo");
        if(!file.exists()){
            file.mkdirs();
        }
        ossClient.getObject(new GetObjectRequest(bucketName, objectName), new File("/upload/photo/1594266510664-timgOBS8CQSB.jpg"));
        System.out.println("下载成功");
// 关闭OSSClient。
        ossClient.shutdown();
    }


}
