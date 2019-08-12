package com.dell.anli06_daoImpl;

import com.dell.anli05_dao.CategoryDao;
import com.dell.anli07_domain.Category;
import com.dell.anli08_util.MyC3P0Utils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.SQLException;
import java.util.List;

public class CategoryDaoImpl implements CategoryDao {

	//1:同步的方法查询所有分类信息
	@Override
    public List<Category> findAllCategory() throws SQLException {
		QueryRunner q = new QueryRunner(MyC3P0Utils.getDataSource());
		return (List<Category>) q.query("select * from category",new BeanListHandler(Category.class));
	}

}
