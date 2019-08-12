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
<title>分类列表页面</title>
<script type="text/javascript">
	//页面加载的时候,使用数据表格展示数据库中的所有分类信息
	$(function(){
		$("#dg").datagrid({    
		    url:'${pageContext.request.contextPath }/CategoryServlet',    
		    columns:[[    
		        {field:'cid',title:'分类编号',width:100},    
		        {field:'cname',title:'分类名称',width:100},    
		        {field:'xxx',title:'操作',width:100,align:'right',
		        	formatter: function(value,row,index){
						return '<a href="JavaScript:del(\''+row.cid+'\')">删除分类</a>';
					}		
				}    
		    ]],
		    queryParams: {
				m:'findAllCategoryByAjax'
			},
			striped:true,
			singleSelect:true,
			fitColumns:true,
			toolbar: [{
				iconCls: 'icon-add',
				text:'添加分类',
				handler: function(){
					//让一个对话框显示出来
					$("#dd").dialog('open');
					$("#ff").form('reset');
				}
			},'-',{
				iconCls: 'icon-edit',
				text:'编辑分类',
				handler: function(){
					//从数据表格中获取选中的行对象
					var row =$("#dg").datagrid('getSelected');
					//回显到一个对话框的表单中
					$("#cid").val(row.cid);
					$("#cname").val(row.cname);
					//打开对话框
					$("#dd2").dialog('open');
				}
			}]

		});  
	})
	//定义一个删除分类信息的方法
	function del(cid){
		var f=confirm("确定要删除这个分类吗?");
		if(f){
			//发送ajax,删除分类信息,重新加载数据表格
			$.post('${pageContext.request.contextPath }/admin/AdminServlet',{m:'delCategoryByCid',cid:cid},function(d){
				//提示删除的结果
				alert(d);
				//重新加载数据表格
				$("#dg").datagrid('reload');
			});
		}
	}
	//当页面加载的时候,给添加分类的form表单添加连个底部的按钮
	$(function(){
		$("#dd").dialog({
			buttons:[{
				text:'保存',
				handler:function(){
					//1:让form表单提交
					$('#ff').form('submit', {    
					    url:'${pageContext.request.contextPath }/admin/AdminServlet',    
						//2:在回调函数中,提示添加结果,重写加载数据表格,关闭当前对话框
					    success:function(data){    
					        //提示添加结果
					    	alert(data);
					    	$("#dg").datagrid('reload');
					    	$("#dd").dialog('close');
					    }    
					});  

				}
			},{
				text:'关闭',
				handler:function(){
					//关闭对话框
					$("#dd").dialog('close');
				}
			}]
		});
	})
	//当页面加载的时候,给修改分类的dialog添加连个底部的按钮
	$(function(){
		$("#dd2").dialog({
			buttons:[{
				text:'保存',
				handler:function(){
					//1:让form表单提交
					$('#ff2').form('submit', {    
					    url:'${pageContext.request.contextPath }/admin/AdminServlet',    
						//2:在回调函数中,提示添加结果,重写加载数据表格,关闭当前对话框
					    success:function(data){    
					        //提示添加结果
					    	alert(data);
					    	$("#dg").datagrid('reload');
					    	$("#dd2").dialog('close');
					    }    
					});  

				}
			},{
				text:'关闭',
				handler:function(){
					//关闭对话框
					$("#dd2").dialog('close');
				}
			}]
		});
	})
</script>

</head>

<body>
<!-- 从数据库中读取所有的分类信息,使用数据表格展示展示出来... -->
	<table id="dg"></table>  
	
	<!-- 隐藏一个添加分类的对话框 -->
	<div id="dd" class="easyui-dialog" title="添加分类" style="width:400px;height:200px;"   
	        data-options="iconCls:'icon-save',resizable:true,modal:true,closed:true">   
	      	<form id="ff" method="post"> 
	      		<input type="hidden" name="m" value="saveCategory">  
			    <div>   
			        <label for="name">分类名称:</label>   
			        <input class="easyui-validatebox" type="text" name="cname" data-options="required:true" />   
			    </div>   
			</form> 
	</div> 
	
	
	
	<!-- 隐藏一个修改分类的对话框 -->
	<div id="dd2" class="easyui-dialog" title="修改分类" style="width:400px;height:200px;"   
	        data-options="iconCls:'icon-save',resizable:true,modal:true,closed:true">   
	      	<form id="ff2" method="post"> 
	      		<input type="hidden" name="m" value="updateCategory">  
	      		<input type="hidden" name="cid" id="cid">  
			    <div>   
			        <label for="name">分类名称:</label>   
			        <input class="easyui-validatebox" type="text" name="cname" id="cname" data-options="required:true" />   
			    </div>   
			</form> 
	</div> 
</body>
</html>