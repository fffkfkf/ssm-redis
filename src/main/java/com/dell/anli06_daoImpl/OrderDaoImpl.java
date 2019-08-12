package com.dell.anli06_daoImpl;
/*
import com.itheima.anli05_dao.OrderDao;
import com.itheima.anli07_domain.OrderItem;
import com.itheima.anli07_domain.Orders;
import com.itheima.anli07_domain.Product;
import com.itheima.anli07_domain.User;
import com.itheima.anli08_utils.MyC3P0Utils;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;*/

import com.dell.anli05_dao.OrderDao;
import com.dell.anli07_domain.OrderItem;
import com.dell.anli07_domain.Orders;
import com.dell.anli07_domain.Product;
import com.dell.anli07_domain.User;
import com.dell.anli08_util.MyC3P0Utils;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@SuppressWarnings("all")
public class OrderDaoImpl implements OrderDao {
//添加订单
	@Override
    public void saveOrder(Connection conn, Orders o) throws SQLException {
		QueryRunner q = new QueryRunner();
		Object[] param={o.getOid(),o.getOrdertime(),o.getTotal(),o.getState(),o.getAddress(),o.getName(),o.getTelephone(),o.getUid()};
		q.update(conn, "insert into orders values(?,?,?,?,?,?,?,?)",param);
	}

	//添加订单项
    @Override
	public void saveOrderItem(Connection conn, OrderItem oi) throws SQLException {
		QueryRunner q = new QueryRunner();
		Object[] param={oi.getItemid(),oi.getCount(),oi.getSubtotal(),oi.getPid(),oi.getOid()};
		q.update(conn, "insert into orderitem values(?,?,?,?,?)",param);
	}

	//3:根据用户id,查询订单数量
    @Override
	public int findTotalCountByUid(String uid) throws SQLException {
		QueryRunner q = new QueryRunner(MyC3P0Utils.getDataSource());
		Long l = (Long) q.query("select count(*) from orders where uid = ?",new ScalarHandler(), uid);
		return l.intValue();
	}
	//4:根据当前用户分页查询订单列表的方法,   注意:订单列表中的每一个订单对象中必须包含相应的订单项集合和商品对象
    @Override
	public List<Orders> findMyOrderListByUidAndPagenumer(User u, int startIndex, int pageSize) throws Exception {
		/*
			2:根据用户编号当前页码值每页条数查询订单集合;(注意,这个集合中,需要我们手动设置集合中的User对象,订单项集合)
			3:给集合中的每一个订单对象,查询出对应的订单项对象和包含的商品对象,然后将订单项集合保存到订单对象中;
		 */
		//1:仅查询订单表中的信息
		QueryRunner q = new QueryRunner(MyC3P0Utils.getDataSource());
		List<Orders> olist= (List<Orders>) q.query("select * from orders where uid=? order by ordertime desc limit ?,?", new BeanListHandler(Orders.class),u.getUid(),startIndex,pageSize);
		//2:遍历olist集合,根据olist集合中的每一个订单对象的oid的值,查数据库中查询相应的订单项集合和商品集合
		//2.1:写一个关联查询的sql语句
		String sql = "select * from orderitem oi,product p where oi.pid=p.pid and oi.oid=?";
		//2.2:选择一个MapListHandler结果集
		MapListHandler rsh = new MapListHandler();
		//2.3:循环迭代olist,根据每一个订单对象的oid的值,都查询一次
		for (Orders o : olist) {
			ArrayList<OrderItem> oiList = new ArrayList<OrderItem>();
			//此时的list中的每一个map集合,都包含了我们的订单项和商品的信息
			List<Map<String,Object>> liMap=q.query(sql, rsh, o.getOid());
			for (Map<String, Object> map : liMap) {
				//每一个map对象,都要封装成两个对象,一个是订单项对象,另一是商品对象
				OrderItem oi = new OrderItem();
				BeanUtils.populate(oi,map );
				Product p = new Product();
				BeanUtils.populate(p, map);
				//System.out.println(oi+"===>"+p);
				oi.setProduct(p);
				oiList.add(oi);
			}
			o.setItems(oiList);
		}
		//返回一个装满了数据的集合对象
		return olist;
	}

	////4:根据oid获取订单对象;(包含订单项集合)
    @Override
	public Orders findOrderByOid(String oid) throws Exception {
		//1:先根据oid,查询一个订单对象
		QueryRunner q = new QueryRunner(MyC3P0Utils.getDataSource());
		Orders o = (Orders) q.query("select * from orders where oid = ?",new BeanHandler(Orders.class),oid);
		//2:根据订单id,查询对应的订单项集合和商品对象
		//2.1:写一个关联查询的sql语句
		String sql = "select * from orderitem oi,product p where oi.pid=p.pid and oi.oid=?";
		//2.2:选择一个MapListHandler结果集
		MapListHandler rsh = new MapListHandler();
		//2.3:执行,得到一个list集合,list中装的是map集合
		List<Map<String,Object>> liMap=q.query(sql, rsh,oid);
		//2.4:迭代list集合,将map的数据封装到订单项和商品对象中
		ArrayList<OrderItem> oiList = new ArrayList<OrderItem>();
		for (Map<String, Object> map : liMap) {
			//每一个map对象,都要封装成两个对象,一个是订单项对象,另一是商品对象
			OrderItem oi = new OrderItem();
			BeanUtils.populate(oi,map );
			Product p = new Product();
			BeanUtils.populate(p, map);
			//System.out.println(oi+"===>"+p);
			oi.setProduct(p);
			oiList.add(oi);
		}
		o.setItems(oiList);
		//3:返回装满了数据的订单对象
		return o;
	}

	////5:更新订单的收货人信息即可
    @Override
	public void updateOrderInfo(Orders o) throws SQLException {
		QueryRunner q = new QueryRunner(MyC3P0Utils.getDataSource());
		q.update("update orders set name=?,address=?,telephone=? where oid=?",o.getName(),o.getAddress(),o.getTelephone(),o.getOid());
	}

	////6:修改订单状态
    @Override
	public void updateOrderState(String oid, int i) throws SQLException {
		QueryRunner q = new QueryRunner(MyC3P0Utils.getDataSource());
		q.update("update orders set state=? where oid=?",i,oid);
	}

}
