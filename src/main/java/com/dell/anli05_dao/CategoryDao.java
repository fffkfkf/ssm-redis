package com.dell.anli05_dao;

import com.dell.anli07_domain.Category;
import java.sql.SQLException;
import java.util.List;

public interface CategoryDao {

	List<Category> findAllCategory() throws SQLException;

}
