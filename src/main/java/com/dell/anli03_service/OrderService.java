package com.dell.anli03_service;

import com.dell.anli07_domain.Orders;
import com.dell.anli07_domain.User;
import com.dell.anli08_util.PageBean;

import java.sql.SQLException;

public interface OrderService {

	void createOrder(Orders o) throws SQLException;

	PageBean<Orders> findMyOrderList(User u, String pn) throws Exception;

	Orders findOrderByOid(String oid) throws Exception;

	void updateOrderInfo(Orders o) throws SQLException;

	void updateOrderState(String r6_Order, int i) throws SQLException;

}
