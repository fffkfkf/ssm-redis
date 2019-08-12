package com.dell.anli05_dao;

import com.dell.anli07_domain.OrderItem;
import com.dell.anli07_domain.Orders;
import com.dell.anli07_domain.User;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface OrderDao {

	void saveOrder(Connection conn, Orders o) throws SQLException;

	void saveOrderItem(Connection conn, OrderItem oi) throws SQLException;

	int findTotalCountByUid(String uid) throws SQLException;

	List<Orders> findMyOrderListByUidAndPagenumer(User u, int startIndex, int pageSize) throws SQLException, Exception;

	Orders findOrderByOid(String oid) throws Exception;

	void updateOrderInfo(Orders o) throws SQLException;

	void updateOrderState(String oid, int i) throws SQLException;

}
