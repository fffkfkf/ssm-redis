package com.dell.anli07_domain;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/*
 * 购物车对象,包含:一个核心的map集合  和一些方法;(1:添加商品,2:移除商品,3:计算总金额4:获取所有的购物项的单列集合)
 */
public class Cart {
	//定义个map集合,用于保存商品编号和购物项对象
	private Map<String,CartItem> map = new HashMap<String,CartItem>();
	//编写一些方法
	//1:添加商品到购物车中的方法
	public void addProductToCart(Product p,int count){
		/*
		 * 将count个p保存到map集合中,注意:需要考虑map中是否已经包含了p对象,如果已经有了,则修改数量,否则创建新的购物项键值对数据
		 */
		CartItem ci = map.get(p.getPid());
		//判断ci的值,如果ci是null,证明map中尚没有这种商品,需要直接添加即可
		if(ci==null){
			ci=new CartItem(p,count);
			map.put(p.getPid(),ci);
		}else{
			//说明map中已经包含了这种商品,需要修改ci对象中的商品数量即可
			ci.setCount(ci.getCount()+count);
		}
	}
	//2:移除一个购物项的方法
	public void removeProductFromCart(String pid){
		map.remove(pid);
	}
	//3:获取所有商品的总金额
	public double getTotalMoney(){
		double sum = 0;
		Collection<CartItem> values = map.values();
		for (CartItem cartItem : values) {
			sum+=cartItem.getSubMoney();
		}
		return sum;
	}
	//4:获取所有的购物项的单列集合的方法.仅仅是为了jsp页面展示购物项方便
	public Collection<CartItem> getCartItemList(){
		return map.values();
	}
}
