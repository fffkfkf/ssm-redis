package com.dell.anli03_service;

import com.dell.anli07_domain.Product;
import com.dell.anli08_util.PageBean;

import java.sql.SQLException;
import java.util.List;

public interface ProductService {

	List<Product> findHots9() throws SQLException;

	List<Product> findNews9() throws SQLException;

	Product findProductByPid(String pid) throws SQLException;

	PageBean<Product> findProductListByPage(String pn, String cid) throws SQLException;

}
