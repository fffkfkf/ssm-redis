package com.dell.anli05_dao;

import com.dell.anli07_domain.Product;
import com.dell.anli07_domain.Product2;
import com.dell.anli07_domain.User;

import java.sql.SQLException;
import java.util.List;

public interface AdminDao {

	User login(String un, String ps) throws SQLException;

	void delCategoryByCid(String cid) throws SQLException;

	int findProductCountByCid(String cid) throws Exception;

	void saveCategory(String id, String cname) throws SQLException;

	void updateCategory(String cid, String cname) throws SQLException;

	int findProductTotalCount() throws SQLException;

	List<Product2> findProductListByPageNumber(int i, int j) throws SQLException;

	void saveProduct(Product p) throws SQLException;

}
