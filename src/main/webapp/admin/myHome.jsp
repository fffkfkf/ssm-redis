<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/css/easyui.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/css/icon.css">
<script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/easyui-lang-zh_CN.js"></script>
<script type="text/javascript">
	//当页面加载的时候,给左侧列表中的所有超链接,添加点击事件,当点击的时候,动态的在中间区域添加选项卡
	$(function(){
		//获取id为aa的div下面所有的a标签,并添加点击事件
		$("#aa a").click(function(){
			//通过this,获取当前触发点击事件的超链接对象,并获取元素体和href属性值
			var t=$(this).html();
			//alert(t)
			if(!t){
				return false;
			}
			//alert(t)
			var url=$(this).prop("href");
			//alert(url);
			//要操作选项卡,判断名称为t的选项卡中的小项是否存在,如果存在会返回true
			var flag=$("#tt").tabs('exists',t);
			if(flag){
				//说明存在,选中即可
				$("#tt").tabs('select',t);
			}else{
				//说明不存在,添加即可
				$('#tt').tabs('add',{
					title: t,
					selected: true,
					closable:true,
					content:'<iframe src="'+url+'" width="99%" height="99%" style="border:0px"></iframe>'
				});
			}
			return false;
		});
	})
</script>
<title>Insert title here</title>
</head>
<body class="easyui-layout"> 
    <div data-options="region:'north',split:true" style="height:100px;">
    	<center>
    		<font style="color: red;font-size: 50px">欢迎光临后台管理系统</font>
    	</center>
    </div>   
    <div data-options="region:'south',split:true" style="height:30px;text-align: center;background-color: #0099ff">商城管理平台</div>   
    <div data-options="region:'west',title:'菜单管理',split:true" style="width:200px;">
    		<div id="aa" class="easyui-accordion" data-options="fit:true">   
			    <div title="分类管理" data-options="iconCls:'icon-search'" style="overflow:auto;padding:10px;">   
			        <h3 style="color:#0099FF;"><a href="${pageContext.request.contextPath }/admin/MyCategory/list.jsp">分类列表</a></h3>   
			    </div>   
			    <div title="商品管理" data-options="iconCls:'icon-search'" style="overflow:auto;padding:10px;">   
			        <h3 style="color:#0099FF;"><a href="${pageContext.request.contextPath }/admin/MyProduct/list.jsp">商品列表</a></h3>   
			    </div>   
			    <div title="订单管理" data-options="iconCls:'icon-search'" style="overflow:auto;padding:10px;">   
			        <h3 style="color:#0099FF;"><a href="#">订单列表</a></h3>   
			    </div>   
			    
			</div>  
    </div>   
    <div data-options="region:'center'" style="padding:5px;background:#eee;width: 100%;height: 100%">
    	<div id="tt" class="easyui-tabs" data-options="fit:true">   
		    <div title="首页" data-options="closable:true" style="overflow:auto;padding:20px;display:none;">   
		       		 欢迎界面
		    </div>   
		</div>  
    	
    </div>   
</body>
</html>