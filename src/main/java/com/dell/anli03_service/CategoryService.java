package com.dell.anli03_service;


import com.dell.anli07_domain.Category;

import java.sql.SQLException;
import java.util.List;

public interface CategoryService {

	List<Category> findAllCategory() throws SQLException;

}
