package com.dell.anli07_domain;
/*
 * 订单项,核心数据就是购物项,多了一些业务数据
 */
public class OrderItem {
	
	private String itemid;//与数据库向对应的字段
	private Product product;//为了在jsp页面上显示数据而使用的字段
	private int count;//与数据库向对应的字段
	private double subtotal;//与数据库向对应的字段
	private String oid;//与数据库向对应的字段
	private String pid;//与数据库向对应的字段
	public String getItemid() {
		return itemid;
	}
	public void setItemid(String itemid) {
		this.itemid = itemid;
	}
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public double getSubtotal() {
		return subtotal;
	}
	public void setSubtotal(double subtotal) {
		this.subtotal = subtotal;
	}
	public String getOid() {
		return oid;
	}
	public void setOid(String oid) {
		this.oid = oid;
	}
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public OrderItem(Product product, int count, double subtotal, String oid, String pid) {
		super();
		this.product = product;
		this.count = count;
		this.subtotal = subtotal;
		this.oid = oid;
		this.pid = pid;
	}
	public OrderItem() {
		super();
	}
	@Override
	public String toString() {
		return "OrderItem [itemid=" + itemid + ", product=" + product + ", count=" + count + ", subtotal=" + subtotal
				+ ", oid=" + oid + ", pid=" + pid + "]";
	}
	
}
