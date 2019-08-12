package com.dell.anli08_util;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.*;

public class UploadUtils {
	/**
	 * 获取文件真实名称
	 * 由于浏览器的不同获取的名称可能为:c:/upload/1.jpg或者1.jpg 
	 * 最终获取的为  1.jpg
	 * @param name 上传上来的文件名称
	 * @return	真实名称
	 */
	public static String getRealName(String name){
		//获取最后一个"/"
		int index = name.lastIndexOf("\\");
		return name.substring(index+1);
	}
	
	
	/**
	 * 获取随机名称
	 * @param realName 真实名称
	 * @return uuid 随机名称
	 */
	public static String getUUIDName(String realName){
		//realname  可能是  1.jpg   也可能是  1
		//获取后缀名
		int index = realName.lastIndexOf(".");
		if(index==-1){
			return UUID.randomUUID().toString().replace("-", "").toUpperCase();
		}else{
			return UUID.randomUUID().toString().replace("-", "").toUpperCase()+realName.substring(index);
		}
	}
	
	/**
	 * 获取文件目录,可以获取256个随机目录
	 * @return 随机目录
	 */
	public static String getDir(){
		String s="0123456789ABCDEF";
		Random r = new Random();
		return "/"+s.charAt(r.nextInt(16))+"/"+s.charAt(r.nextInt(16));
	}
	//效果相当于request.getParameterMap方法,加上文件上传的效果;toDir是服务器的webContent目录下的文件夹名称
	public static Map<String,Object> upload(HttpServletRequest request, String toDir) throws Exception {
		//4.2:创建磁盘文件项工厂;
		DiskFileItemFactory f= new DiskFileItemFactory();
		//4.3:使用工厂创建一个核心上传对象;
		ServletFileUpload upload = new ServletFileUpload(f);
		//4.4:使用核心上传对象解析request对象
		List<FileItem> list=upload .parseRequest(request);
		Map<String,Object> mymap = new HashMap<String,Object>();
		//4.5:遍历list集合,获取每一个表单项;
		for (FileItem fi : list) {
			//获取该项的参数名
			String name = fi.getFieldName();
			String value =null;
			//4.6:判断每一个表单项是普通项还是文件项;
			if(fi.isFormField()){
				//4.6.1:普通项;取参数值;
				value = fi.getString("utf-8");
			}else{
				//4.6.2:文件项;获取文件名和文件内容
				String name2 = getRealName(fi.getName());//name2就是一个简短的文件名
				System.out.println("上传文件的原始文件名是:"+name2+"   将使用随机名替代...请知晓!");
				InputStream in = fi.getInputStream();
				String name3 = getUUIDName(name2);//name3就是一个随机名称,为了防止覆盖
				String dir = getDir();//为了提升服务器性能
				String path = request.getServletContext().getRealPath(toDir);//获取服务器的绝对路径
				File ff = new File(path+dir);
				ff.mkdirs();//创建dir文件夹;
				String zp=path+dir+"/"+name3;//将name3这个文件在磁盘绝对路径上,表示出来
				int i = zp.indexOf(toDir);
				value = zp.substring(i);//获取从指定的文件夹开始的相对路径
				System.out.println("数据库中保存的图片路径是:"+value);
				FileOutputStream out = new FileOutputStream(zp);
				IOUtils.copy(in, out);
				in.close();
				out.close();
				fi.delete();
			}
			mymap.put(name, value);
		}
		return mymap;
	}
	public static void main(String[] args) {
		//String s="G:\\day17-基础加强\\resource\\1.jpg";
		String s="1.jgp";
		String realName = getRealName(s);
		System.out.println(realName);
		
		String uuidName = getUUIDName(realName);
		System.out.println(uuidName);
		
		String dir = getDir();
		System.out.println(dir);
	}
}
