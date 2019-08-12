package com.dell.anli04_serviceimple;

import com.dell.anli03_service.CategoryService;
import com.dell.anli05_dao.CategoryDao;
import com.dell.anli06_daoImpl.CategoryDaoImpl;
import com.dell.anli07_domain.Category;

import java.sql.SQLException;
import java.util.List;

public class CategoryServiceImpl implements CategoryService {
	//1:同步的方法查询所有分类信息
    @Override
	public List<Category> findAllCategory() throws SQLException {

		CategoryDao cd = (CategoryDao) new CategoryDaoImpl();
		return cd.findAllCategory();

	}

}
