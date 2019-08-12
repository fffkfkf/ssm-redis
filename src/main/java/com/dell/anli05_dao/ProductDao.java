package com.dell.anli05_dao;

import com.dell.anli07_domain.Product;

import java.sql.SQLException;
import java.util.List;

public interface ProductDao {

	List<Product> findHots9() throws SQLException;

	List<Product> findNews9() throws SQLException;

	Product findProductByPid(String pid) throws SQLException;

	int findCountByCid(String cid) throws SQLException;

	List<Product> findProductListByCidAndPage(int startIndex, int pageSize, String cid) throws SQLException;

}
