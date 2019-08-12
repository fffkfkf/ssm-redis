package com.dell.anli02_web;

import com.dell.anli03_service.AdminService;
import com.dell.anli07_domain.Product;
import com.dell.anli08_util.MyBeanFactory;
import com.dell.anli08_util.UploadUtils;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 添加商品专用的servlet
 */
public class AddProductServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/*
		 * 1:在jsp页面将form表单的提交方式修改为method="post" enctype="multipart/form-data";
			2:导入file-upload.jar 工具包;
			3:在servlet中:
				1:创建本地磁盘工厂对象;DiskFileItemFactory ()
				2:使用磁盘工厂对象,创建一个文件上传对象;ServletFileUpload(DiskFileItemFactory)
				3:使用文件上传对象,解析request对象;parseRequest(HttpServletRequest request)  会得到一个装满了inptu选项(表单项)的list集合;
				4:遍历list集合,判断是普通的表单项还是文件表单项;
						1:如果是普通项;  	getFieldName()  获取input标签的name属性的值
											getString(String encoding)  获取input标签的value属性的值
						2:如果是文件上传项;  getFieldName()  获取input标签的name属性的值
											 getName()   获取文件名;
											 需要使用io流,获取文件的内容:
											 getInputStream()  使用该输入流对象,可以读取文件的内容;
		 */
		//1:创建本地磁盘工厂对象;DiskFileItemFactory ()
		DiskFileItemFactory dff = new DiskFileItemFactory();
		//2:使用磁盘工厂对象,创建一个文件上传对象;
		ServletFileUpload sfu = new ServletFileUpload(dff);
		//3:使用文件上传对象,解析request对象;
		try {
			List<FileItem> list = sfu.parseRequest(request);
			//创建一个map集合,用于保存所有参数名和参数值
			Map<String,Object> map = new HashMap<>();
			//4:遍历list集合,判断是普通的表单项还是文件表单项;
			for (FileItem fi : list) {
				//1:获取name属性的值
				String attrName = fi.getFieldName();
				//2:判断是不是普通组件
				if(fi.isFormField()){
					//说明是一个普通组件,获取value属性的值(相当于是输入框中的值)
					String attrValue = fi.getString("utf-8");
					//将参数名和参数值,保存到map集合中
					map.put(attrName, attrValue);
				}else{
					//说明是一个文件上传的组件,直接将图片的保存路径保存到map集合中即可
					//获取文件的名称
					String fileName = fi.getName();
					//使用工具类,将fileName变为一个短名称
					fileName=UploadUtils.getRealName(fileName);
					//使用文件夹的名称和fileName组成一个图片路径保存到map中;
					String path="upload/"+fileName;
					//将参数名和拼接后的路径名保存到map集合中
					map.put(attrName, path);
					//获取文件输入流
					InputStream in = fi.getInputStream();
					//使用ServletContext对象获取文件的磁盘绝对路径,并创建一个文件输出流对象
					String path2 = this.getServletContext().getRealPath("/upload");
					FileOutputStream fout = new FileOutputStream(path2+"/"+fileName);
					//进行文件复制
					IOUtils.copy(in, fout);
					//删除产生的临时文件
					fi.delete();
				}
			}
			//-----------------从这里开始,操作数据库-------------------------------
			//封装数据
			Product p = new Product();
			BeanUtils.populate(p, map);
			//调用业务层,保存数据到数据库中
			AdminService as = (AdminService)MyBeanFactory.getBean("AdminService");
			as.saveProduct(p);
			response.getWriter().print("添加商品成功!!!");
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
		
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
