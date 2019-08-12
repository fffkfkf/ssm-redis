package com.dell.anli08_util;

/*import com.itheima.anli07_domain.Product2;
import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;*/
import com.dell.anli07_domain.Product2;
import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import net.sf.json.processors.JsonValueProcessor;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class MyDateJson implements JsonValueProcessor{
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	@Override
	public Object processArrayValue(Object arg0, JsonConfig arg1) {
		return arg0;
	}

	@Override
	public Object processObjectValue(String arg0, Object arg1, JsonConfig arg2) {
		//arg0  字段名
		//arg1  字段值  如果是Date类型,我们需要自己转一下
		if(arg1 instanceof Date){
			String f = sdf.format((Date)arg1);
			return f;
		}
		return arg1;
	}
	
	public static void main(String[] args) throws Exception {
		QueryRunner q = new QueryRunner(MyC3P0Utils.getDataSource());
		List<Product2> list= (List<Product2>) q.query("select * from product",new BeanListHandler(Product2.class));
		//JsonConfig jc = new JsonConfig();
		//jc.registerJsonValueProcessor(Date.class,new MyDateJson());
		String s = JSONArray.fromObject(list).toString();
		System.out.println(s);
	}
}
