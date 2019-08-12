package com.dell.anli04_serviceimple;

import com.dell.anli03_service.OrderService;
import com.dell.anli05_dao.OrderDao;
import com.dell.anli06_daoImpl.OrderDaoImpl;
import com.dell.anli07_domain.OrderItem;
import com.dell.anli07_domain.Orders;
import com.dell.anli07_domain.User;
import com.dell.anli08_util.MyBeanFactory;
import com.dell.anli08_util.MyC3P0Utils;
import com.dell.anli08_util.PageBean;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class OrderServiceImpl implements OrderService {
	//1:添加订单的方法

	@Override
    public void createOrder(Orders o) throws SQLException {
		//1:必须使用事务控制订单项表中的数据与订单表中的数据同步操作;先加订单,后加订单项
		Connection conn = MyC3P0Utils.getConnection();
		conn.setAutoCommit(false);
		OrderDao od = (OrderDao) new OrderDaoImpl();
		//添加订单
		od.saveOrder(conn,o);
		//添加订单项
		List<OrderItem> list = o.getItems();
		for (OrderItem oi : list) {
			od.saveOrderItem(conn,oi);
		}
		conn.commit();
		conn.close();
	}

	//2:订单列表查询的方法;
	@Override
    public PageBean<Orders> findMyOrderList(User u, String pn) throws Exception {
		/*
		 * 1:创建一个分页对象
			2:设置分页对象的数据,根据当前用户对象查询数据库中订单数量;
			3:根据当前用户对象和当前页码值查询这一页订单对象集合;
			4:将订单集合保存到分页对象中;
			5:返回分页对象;

		 */
		//1:创建一个分页对象
		 PageBean<Orders> pb = new  PageBean<Orders>(Integer.parseInt(pn),2);
		 //2:设置分页对象的数据,根据当前用户对象查询数据库中订单数量;
		 OrderDao od = (OrderDao) MyBeanFactory.getBean("OrderDao");
		 int c=od.findTotalCountByUid(u.getUid());
		 pb.setTotalRecord(c);
		 //3:根据当前用户对象和当前页码值查询这一页订单对象集合;
		 List<Orders> li=od.findMyOrderListByUidAndPagenumer(u,pb.getStartIndex(),pb.getPageSize());
		//4:将订单集合保存到分页对象中;
		 pb.setData(li);
		 //5:返回分页对象;
		 return pb;
	}

	//4:根据oid获取订单对象;(包含订单项集合)
	@Override
    public Orders findOrderByOid(String oid) throws Exception {
		OrderDao od = (OrderDao) MyBeanFactory.getBean("OrderDao");
		return od.findOrderByOid(oid);
	}

	//5:更新订单的收货人信息即可
	@Override
    public void updateOrderInfo(Orders o) throws SQLException {
		OrderDao od = (OrderDao) MyBeanFactory.getBean("OrderDao");
		od.updateOrderInfo(o);
	}

	////6:修改订单状态
	@Override
    public void updateOrderState(String oid, int i) throws SQLException {
		OrderDao od = (OrderDao) MyBeanFactory.getBean("OrderDao");
		od.updateOrderState(oid,i);
	}

}
