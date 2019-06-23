package com.sxl.util;


public class Response {
private String statusCode;//业务处理状态，成功success，失败false
private String statusText;//状态描述
public String getStatusCode() {
	return statusCode;
}
public void setStatusCode(String statusCode) {
	this.statusCode = statusCode;
}
public String getStatusText() {
	return statusText;
}
public void setStatusText(String statusText) {
	this.statusText = statusText;
}

}
