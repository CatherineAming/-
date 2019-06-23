
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
*单据查询验收
* @author Administratorxxxx
* @date2018-03-21
*/
@Controller("djcxysController")
@RequestMapping(value = "/admin/djcxys")
public class DjcxysController extends MyController {
	

/**
* 查询frame
*/
	@RequestMapping(value = "/frame")
	public String frame(Model model, HttpServletRequest request,String flag)throws Exception {
		return "/admin/djcxys/frame";
	}
	
/**
* 查询列表
*/
	@RequestMapping(value = "/list")
	public String list(Model model, HttpServletRequest request,String flag)throws Exception {
		//select date_format(insertDate, '%Y-%m-%d %H:%i:%s')
		//CONVERT(varchar, insertDate, 120 )
		//to_char(insertDate,'yyyy-mm-dd,hh24:mi:ss') 

		String sql="select a.*,(select orderbh from t_order b where a.orderId=b.id) orderbh  from t_djcxys a where 1=1 ";
		if("2".equals(flag)){
			sql+=" and exists(select * from t_order b where b.id=a.orderId and b.customerId='"+getCustomer(request).get("id")+"')";
		}

		sql+=" order by id desc";
		List list = db.queryForList(sql);
		request.setAttribute("list", list);
		return "/admin/djcxys/list";
	}
	
/**
* 编辑保存（包含修改和添加）
*/
	@RequestMapping(value = "/editSave")
	public ResponseEntity<String> editSave(Model model,HttpServletRequest request,Long id,String flag
		,Integer orderId,String types,String djpic,String nowDate,String remark) throws Exception{
		int result = 0;
		if(id!=null){
			String sql="update t_djcxys set orderId=?,types=?,djpic=?,nowDate=?,remark=? where id=?";
			result = db.update(sql, new Object[]{orderId,types,djpic,nowDate,remark,id});
		}else{
			String sql="insert into t_djcxys(orderId,types,djpic,nowDate,remark) values(?,?,?,?,?)";
			result = db.update(sql, new Object[]{orderId,types,djpic,nowDate,remark});
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
		
		String sql="delete from t_djcxys where id=?";
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
			String sql="select * from t_djcxys where id=?";
			Map map = db.queryForMap(sql,new Object[]{id});
			model.addAttribute("map", map);
		}String sql="";

 sql="select * from t_order";
model.addAttribute("orderList", db.queryForList(sql));

		return "/admin/djcxys/edit";
	}
}
