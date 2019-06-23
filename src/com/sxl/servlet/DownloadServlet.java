package com.sxl.servlet;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.sxl.service.*;

public class DownloadServlet extends HttpServlet{
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		this.doPost(req, resp);
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		ProductService ps=new ProductService();
		List<Object[]> list = null;
		try {
			list = ps.download();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		//下载文件的方法
		//创建excel文档对象
		HSSFWorkbook wb=new HSSFWorkbook();
		//创建表单
		HSSFSheet sheet=wb.createSheet();
		//创建第一行
		HSSFRow row1=sheet.createRow(0);
		//创建单元格
		HSSFCell cell1=row1.createCell((short) 0);
		cell1.setEncoding(HSSFCell.ENCODING_UTF_16);
		cell1.setCellValue("订单号");
		HSSFCell cell2=row1.createCell((short) 1);
		cell2.setEncoding(HSSFCell.ENCODING_UTF_16);
		cell2.setCellValue("药品名");
		HSSFCell cell3=row1.createCell((short) 2);
		cell3.setEncoding(HSSFCell.ENCODING_UTF_16);
		cell3.setCellValue("寄件人");
		HSSFCell cell4=row1.createCell((short) 3);
		cell4.setEncoding(HSSFCell.ENCODING_UTF_16);
		cell4.setCellValue("联系电话");
		HSSFCell cell5=row1.createCell((short) 4);
		cell5.setEncoding(HSSFCell.ENCODING_UTF_16);
		cell5.setCellValue("出发地");
		HSSFCell cell6=row1.createCell((short) 5);
		cell6.setEncoding(HSSFCell.ENCODING_UTF_16);
		cell6.setCellValue("目的地");
		//创建第n行
		System.out.println(list.size());
		for (int i = 0; i <list.size(); i++)
		{
			Object[] o=list.get(i);
			String b=(String) o[1];
			HSSFRow rowi=sheet.createRow(i+1);
			HSSFCell celli1=rowi.createCell((short) 0);
			celli1.setEncoding(HSSFCell.ENCODING_UTF_16);
			celli1.setCellValue(b);
			Object c=o[2];
			HSSFCell celli2=rowi.createCell((short) 1);
			celli2.setEncoding(HSSFCell.ENCODING_UTF_16);
			String c1=String.valueOf(c);
			celli2.setCellValue(String.valueOf(c1));
			Object d=o[5];
			HSSFCell celli3=rowi.createCell((short) 2);
			celli3.setEncoding(HSSFCell.ENCODING_UTF_16);
			String d1=String.valueOf(d);
			celli3.setCellValue(String.valueOf(d1));
			Object e=o[6];
			HSSFCell celli4=rowi.createCell((short) 3);
			celli4.setCellValue(String.valueOf(e));
			Object f=o[7];
			HSSFCell celli5=rowi.createCell((short) 4);
			celli5.setEncoding(HSSFCell.ENCODING_UTF_16);
			String f1=String.valueOf(f);
			celli5.setCellValue(String.valueOf(f1));
			Object g=o[8];
			HSSFCell celli6=rowi.createCell((short) 5);
			celli6.setEncoding(HSSFCell.ENCODING_UTF_16);
			String g1=String.valueOf(g);
			celli6.setCellValue(String.valueOf(g1));
		}
		
		//输出excel
		OutputStream output=resp.getOutputStream();
		resp.reset();
		resp.setHeader("Content-disposition", "attachment;filename=details.xls");
		resp.setContentType("application/msexcel");
		wb.write(output);
		
		output.close();
		
		

	}
}