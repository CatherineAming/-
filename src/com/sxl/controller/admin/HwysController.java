
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
*货物运收
* @author Administratorxxxx
* @date2018-03-21
*/
@Controller("hwysController")
@RequestMapping(value = "/admin/hwys")
public class HwysController extends MyController {
	

/**
* 查询frame
*/
	@RequestMapping(value = "/frame")
	public String frame(Model model, HttpServletRequest request,String flag)throws Exception {
		return "/admin/hwys/frame";
	}
	
/**
* 查询列表
*/
	@RequestMapping(value = "/list")
	public String list(Model model, HttpServletRequest request,String flag)throws Exception {
		//select date_format(insertDate, '%Y-%m-%d %H:%i:%s')
		//CONVERT(varchar, insertDate, 120 )
		//to_char(insertDate,'yyyy-mm-dd,hh24:mi:ss') 

		String sql="select a.*,(select orderbh from t_order b where a.orderId=b.id) orderbh ,(select max(employeeName) from t_employee b where a.employeeId=b.id) employeeName  from t_hwys a where 1=1 ";

if(1==2){sql+=" and employeeId="+getEmployee(request).get("id") +" ";}
if("2".equals(flag)){
	sql+=" and exists(select * from t_order b where b.id=a.orderId and b.customerId='"+getCustomer(request).get("id")+"')";
}


		sql+=" order by id desc";
		List list = db.queryForList(sql);
		request.setAttribute("list", list);
		return "/admin/hwys/list";
	}
	
/**
* 编辑保存（包含修改和添加）
*/
	@RequestMapping(value = "/editSave")
	public ResponseEntity<String> editSave(Model model,HttpServletRequest request,Long id,String flag
		,Integer orderId,String types,String pic,String nowDate,String ysjs,String ywgd,Integer employeeId) throws Exception{
		int result = 0;
		if(id!=null){
			String sql="update t_hwys set orderId=?,types=?,pic=?,nowDate=?,ysjs=?,ywgd=? where id=?";
			result = db.update(sql, new Object[]{orderId,types,pic,nowDate,ysjs,ywgd,id});
		}else{
			String sql="insert into t_hwys(orderId,types,pic,nowDate,ysjs,ywgd,employeeId) values(?,?,?,?,?,?,?)";
			result = db.update(sql, new Object[]{orderId,types,pic,nowDate,ysjs,ywgd,getEmployee(request).get("id")});
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
		
		String sql="delete from t_hwys where id=?";
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
			String sql="select * from t_hwys where id=?";
			Map map = db.queryForMap(sql,new Object[]{id});
			model.addAttribute("map", map);
		}String sql="";

 sql="select * from t_order";
model.addAttribute("orderList", db.queryForList(sql));

		return "/admin/hwys/edit";
	}
}
