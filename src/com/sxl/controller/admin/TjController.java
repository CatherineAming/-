
package com.sxl.controller.admin;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sxl.controller.MyController;

@Controller("TjController")
@RequestMapping(value = "/admin/tj")
public class TjController extends MyController {
	

	@RequestMapping(value = "/tj1")
	public String tj1(Model model, HttpServletRequest request,String flag)throws Exception {
		
		String sql="select date_format(insertDate,'%y-%m-%d') days,count(1) price from t_order group by date_format(insertDate,'%y-%m-%d')";
		List<Map> list = db.queryForList(sql);
		String aa="";
		String bb="";
		if(list!=null&&list.size()>0){
			for (int i = 0; i < list.size(); i++) {
				if(i==0){
					aa="'"+list.get(i).get("days")+"'";
					bb = list.get(i).get("price")+"";
				}else{
					aa+=",'"+list.get(i).get("days")+"'";
					bb +=","+ list.get(i).get("price")+"";
				}
			}
			
		}
		model.addAttribute("aa", aa);
		model.addAttribute("bb", bb);
		return "/admin/tj/tj1";
	}
	
	@RequestMapping(value = "/tj2")
	public String tj2(Model model, HttpServletRequest request)throws Exception {
		
		String sql="select date_format(nowDate,'%y-%m-%d') days,sum((case when types='出库' then 1 else 0 end)) cp,sum((case when types='入库' then 1 else 0 end)) zd  from t_djcxys group by date_format(nowDate,'%y-%m-%d')";
		List<Map> list = db.queryForList(sql);
		System.out.println(sql);
		System.out.println(list);
		String a="{name: 出库',data: [";
		String b="{name: '入库',data: [";
		String days="";
		for (int i = 0; i < list.size(); i++) {
			if(i==0){
				a+=""+list.get(i).get("cp");
				b+=""+list.get(i).get("zd");
				days+=""+list.get(i).get("days");
			}else{
				a+=","+list.get(i).get("cp");
				b+=","+list.get(i).get("zd");
				days+=","+list.get(i).get("days");
			}
		}
		a+="]}";
		b+="]}";
		model.addAttribute("a", a);
		model.addAttribute("b", b);
		model.addAttribute("days", days);
		return "/admin/tj/tj2";
	}
	
	@RequestMapping(value = "/tj3")
	public String tj3(Model model, HttpServletRequest request)throws Exception {
		String sql="select (select max(employeeName) from t_employee b where a.employeeId=b.id ) names,count(1) counts from t_djdd a group by employeeId ";
		List<Map> list = db.queryForList(sql);
		int count = 0;
		for (int i = 0; i < list.size(); i++) {
			count+=Integer.parseInt(list.get(i).get("counts").toString());
		}
		String out = "";
		for (int i = 0; i < list.size(); i++) {
			if(i==0){
				out+="{name: '"+list.get(i).get("names")+"',y: "+toPercent(Integer.parseInt(list.get(i).get("counts").toString()), count)+",sliced: true,selected: true}";
			}else{
				out+=",['"+list.get(i).get("names")+"',"+toPercent(Integer.parseInt(list.get(i).get("counts").toString()), count)+"]";
			}
		}
		model.addAttribute("out", out);
		return "/admin/tj/tj3";
	}
	
	public String toPercent(int value,int count){
		DecimalFormat df =new DecimalFormat("######0.00"); 
		int intValue = value;
		return df.format((intValue*100.00d)/count);
	}
}
