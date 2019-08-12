package com.dell.anli03_service;


import com.dell.anli07_domain.Product;
import com.dell.anli07_domain.Product2;
import com.dell.anli07_domain.User;
import com.dell.anli08_util.Pager;

import java.sql.SQLException;

public interface AdminService {

	User login(String un, String ps) throws SQLException;

	String delCategoryByCid(String cid) throws SQLException, Exception;

	void saveCategory(String cname) throws SQLException;

	void updateCategory(String cid, String cname) throws SQLException;

	Pager<Product2> findProductByPageNumberAjax(String pageNumber, String pageSize) throws SQLException;

	void saveProduct(Product p) throws SQLException;

}
