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
<title>商品列表</title>
<script type="text/javascript">
	//当页面加载的时候,使用table作为一个数据表格使用,展示所有从数据库中查询的商品信息
	$(function(){
		$('#dg').datagrid({    
		    url:'${pageContext.request.contextPath }/admin/AdminServlet',    
		    columns:[[    
		        {field:'pid',title:'商品编号',width:100},    
		        {field:'pname',title:'商品名称',width:100},    
		        {field:'shop_price',title:'商城价',width:100},    
		        {field:'xxxxx',title:'图片',width:100,
		        	formatter: function(value,row,index){
						//从row对象中获取一个图片的路径,展示一个图片
						return '<img src="${pageContext.request.contextPath }/'+row.pimage+'" width="40px" height="40px">';
					}
		        },    
		        {field:'pdate',title:'上架日期',width:100},    
		        {field:'xxxx',title:'是否热门',width:100,
		        	formatter: function(value,row,index){
						//从row对象中获取一个是否热门的值,并显示汉字
						if(row.is_hot){
							return '热门';
						}else{
							return '不热';
						}
					}
		        },    
		        {field:'pdesc',title:'商品描述',width:100},    
		        {field:'market_price',title:'市场价',width:100,align:'right'}    
		    ]],
		    pagination:true,
		    fitColumns:true,
		    striped:true,
		    singleSelect:true,
		    pageNumber:1,
		    pageSize:10,
		    pageList:[5,10,15],
		    queryParams:{m:'findProductByPageNumberAjax'},
		    toolbar: [{
				iconCls: 'icon-add',
				text:'添加商品',
				handler: function(){
					//alert('编辑按钮111')
					//让添加商品的对话框显示出来
					$("#dd").dialog('open');
				}
			}]

		});  
		//需要发送一个ajax请求,查询所有分类信息,并动态的添加到下拉框中
		$.post('${pageContext.request.contextPath }/CategoryServlet',{m:'findAllCategoryByAjax'},function(data){
			//获取的data是一个json数组,迭代数组,添加到select下拉框中
			for(var i=0;i<data.length;i++){
				var j=data[i];
				var op='<option value="'+j.cid+'">'+j.cname+'</option>';
				$("#cid").append(op);
			}
		},"JSON");
		//需要给添加商品的对话框添加一个保存按钮和关闭按钮
		$("#dd").dialog({
			buttons:[{
				text:'确认添加',
				handler:function(){
					//提交form表单,刷新数据表格,关闭当前对话框
					//alert("提交表单...")
					$('#ff').form('submit', {    
					    url:'${pageContext.request.contextPath }/admin/AddProductServlet',    
					    success:function(data){    
					        alert(data);
					        //刷新数据表格,关闭当前对话框
					        $('#dg').datagrid('reload');
					        $("#dd").dialog('close');
					    }    
					}); 
				}
			},{
				text:'关闭窗口',
				handler:function(){
					//关闭当前对话框
					$("#dd").dialog('close');
				}
			}]
		});
	})
</script>
</head>
<body>
	<table id="dg"></table> 
	
	<!-- 隐藏一个用于添加商品的对话框 -->
	<div id="dd" class="easyui-dialog" title="添加商品" style="width:400px;height:400px;"   
	        data-options="iconCls:'icon-save',resizable:true,modal:true,closed:true">   
	   		<!--   要携带上传的组件,必须将form表单的enctype值,修改为multipart/form-data -->
	     <form id="ff" method="post" enctype="multipart/form-data"> 
	     	<!--  <input type="hidden" name="m" value="saveProduct"> -->
		    <div>   
		        <label for="name">商品名称:</label>   
		        <input class="easyui-validatebox" type="text" name="pname" data-options="required:true" />   
		    </div>   
		    <div>   
		        <label for="name">商品商城价:</label>   
		        <input class="easyui-validatebox" type="text" name="shop_price" data-options="required:true" />   
		    </div>   
		    <div>   
		        <label for="name">图片:</label>   
		        <input type="file" name="pimage"/>   
		    </div>   
		    <div>   
		        <label for="name">是否热门:</label>   
		        <input type="radio" name="is_hot" value="1"/>热   
		        <input type="radio" name="is_hot" value="0"/>凉   
		    </div>   
		    <div>   
		        <label for="name">描述:</label>   
		        <textarea rows="3" cols="50" name="pdesc"></textarea>
		    </div>
		    <div>   
		        <label for="name">所属分类:</label>   
		        <select name="cid" id="cid">
		        	<option value="">-----请选择分类-----</option>
		        </select>
		    </div>
		    
		</form>  
	</div>  

	
</body>
</html>