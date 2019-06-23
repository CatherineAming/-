package com.sxl.service;

import java.sql.SQLException;
import java.util.List;
import com.sxl.dao.ProductDao;
public class ProductService {

	public List<Object[]> download() throws SQLException {
		// TODO Auto-generated method stub
		List<Object[]> salesList = null;
		    ProductDao dao = new ProductDao();
			salesList = dao.salesList();
		
		return salesList;
	}

}
