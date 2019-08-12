package com.dell.anli07_domain;

import java.util.Date;
import java.util.List;

/*
 * 与数据库向对应的订单模型类
 */
public class Orders {
	private List<OrderItem> items;//在jsp页面展示订单项数据使用
	private String oid;//与数据库相关的字段
	private Date ordertime;//与数据库相关的字段
	private double total;//与数据库相关的字段
	private int state;//与数据库相关的字段
	private String address;//与数据库相关的字段
	private String name;//与数据库相关的字段
	private String telephone;//与数据库相关的字段
	private String uid;//与数据库相关的字段  用户外键
	public List<OrderItem> getItems() {
		return items;
	}
	public void setItems(List<OrderItem> items) {
		this.items = items;
	}
	public String getOid() {
		return oid;
	}
	public void setOid(String oid) {
		this.oid = oid;
	}
	public Date getOrdertime() {
		return ordertime;
	}
	public void setOrdertime(Date ordertime) {
		this.ordertime = ordertime;
	}
	public double getTotal() {
		return total;
	}
	public void setTotal(double total) {
		this.total = total;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public Orders(List<OrderItem> items, String oid, Date ordertime, double total, int state, String address,
                  String name, String telephone, String uid) {
		super();
		this.items = items;
		this.oid = oid;
		this.ordertime = ordertime;
		this.total = total;
		this.state = state;
		this.address = address;
		this.name = name;
		this.telephone = telephone;
		this.uid = uid;
	}
	public Orders() {
		super();
	}
}
