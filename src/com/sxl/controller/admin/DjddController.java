
package com.sxl.controller.admin;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sxl.controller.MyController;

/**
*登记调度
* @author Administratorxxxx
* @date2018-03-21
*/
@Controller("djddController")
@RequestMapping(value = "/admin/djdd")
public class DjddController extends MyController {
	

/**
* 查询frame
*/
	@RequestMapping(value = "/frame")
	public String frame(Model model, HttpServletRequest request,String flag)throws Exception {
		return "/admin/djdd/frame";
	}
	
/**
* 查询列表
*/
	@RequestMapping(value = "/list")
	public String list(Model model, HttpServletRequest request,String flag,String mdd)throws Exception {
		//select date_format(insertDate, '%Y-%m-%d %H:%i:%s')
		//CONVERT(varchar, insertDate, 120 )
		//to_char(insertDate,'yyyy-mm-dd,hh24:mi:ss') 

		String sql="SELECT a.*,b.orderbh,(select max(employeeName) from t_employee c where a.employeeId=c.id) employeeName,(select max(phone) from t_employee c where a.employeeId=c.id) tele  FROM t_djdd a LEFT JOIN t_order b ON a.orderId=b.id WHERE 1=1 ";
		if("2".equals(flag)){
			sql+=" and exists(select * from t_order b where b.id=a.orderId and b.customerId='"+getCustomer(request).get("id")+"')";
		}

	if(mdd!=null&&!"".equals(mdd)){
			sql+=" and orderbh like '%"+mdd+"%'";
		}
		sql+=" order by id desc";
		List list = db.queryForList(sql);
		request.setAttribute("list", list);
		return "/admin/djdd/list";
	}
	
/**
* 编辑保存（包含修改和添加）
*/
	@RequestMapping(value = "/editSave")
	public ResponseEntity<String> editSave(Model model,HttpServletRequest request,Long id,String flag
		,Integer orderId,String mdd,String psjd,String nowDate,String remark) throws Exception{
		int result = 0;
		if(id!=null){
			String sql="update t_djdd set orderId=?,mdd=?,psjd=?,nowDate=?,remark=? where id=?";
			result = db.update(sql, new Object[]{orderId,mdd,psjd,nowDate,remark,id});
		}else{
			String sql="insert into t_djdd(orderId,mdd,psjd,nowDate,remark,employeeId) values(?,?,?,?,?,?)";
			result = db.update(sql, new Object[]{orderId,mdd,psjd,nowDate,remark,getEmployee(request).get("id")});
		}
		if(result==1){
			return renderData(true,"操作成功",null);
		}else{
			return renderData(false,"操作失败",null);
		}
	}
	
/**
* 删除信息
*/
	@RequestMapping(value = "/editDelete")
	public ResponseEntity<String> editDelete(Model model,HttpServletRequest request,Long id,String flag) throws Exception {
		
		String sql="delete from t_djdd where id=?";
		int result = db.update(sql, new Object[]{id});
		if(result==1){
			return renderData(true,"操作成功",null);
		}else{
			return renderData(false,"操作失败",null);
		}
		
	}
	
/**
* 跳转到编辑页面
*/
	@RequestMapping(value = "/edit")
	public String edit(Model model, HttpServletRequest request,Long id,String flag)throws Exception {
		if(id!=null){
			//修改
			String sql="select * from t_djdd where id=?";
			Map map = db.queryForMap(sql,new Object[]{id});
			model.addAttribute("map", map);
		}String sql="";

 sql="select * from t_order";
model.addAttribute("orderList", db.queryForList(sql));

		return "/admin/djdd/edit";
	}
}
