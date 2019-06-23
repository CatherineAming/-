package com.sxl.taglib.display;




public class ExcelView extends org.displaytag.export.ExcelView{

	@Override
	public String getMimeType() {
		 return "application/vnd.ms-excel;charset=UTF-8";
	}

	
}
