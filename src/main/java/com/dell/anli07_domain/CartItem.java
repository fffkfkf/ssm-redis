package com.dell.anli07_domain;
/*
 * 购物项对象,仅仅是为了展示购物车而设计的一个逻辑模型类
 */
public class CartItem {
	private Product product;
	private int count;
	private double subMoney;//这种商品的小计
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
	//计算购物项的小计
	public double getSubMoney() {
		if(product==null){
			return 0;
		}
		return product.getShop_price()*count;
	}
	public void setSubMoney(double subMoney) {
		this.subMoney = subMoney;
	}
	public CartItem(Product product, int count) {
		super();
		this.product = product;
		this.count = count;
	}
	public CartItem() {
		super();
	}
	
	
}
