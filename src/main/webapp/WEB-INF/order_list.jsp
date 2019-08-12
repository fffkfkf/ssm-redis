<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/jsp/header.jsp" %>
<!doctype html>
<html>

	<head>
		<meta charset="utf-8" />
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<title>会员登录</title>
		<!-- 引入自定义css文件 style.css -->
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" type="text/css" />

		<style>
			body {
				margin-top: 20px;
				margin: 0 auto;
			}
			
			.carousel-inner .item img {
				width: 100%;
				height: 300px;
			}
		</style>
	</head>

	<body>
		<div class="container">
			<div class="row">

				<div style="margin:0 auto; margin-top:10px;width:950px;">
					<strong>我的订单</strong>
					<table class="table table-bordered">
						<!-- 此时的o代表的是订单对象 -->
						<c:forEach items="${pb.data }" var="o">
							<tbody>
								<tr class="success">
									<th colspan="5">
										订单编号:${o.oid }
										&nbsp;&nbsp;&nbsp;
										订单时间:${o.ordertime }
										&nbsp;&nbsp;&nbsp;
										订单状态:
										<c:if test="${o.state==0 }">未付款,<a href="${pageContext.request.contextPath}/OrderServlet?m=findOrderByOid&oid=${o.oid}">去支付</a></c:if>
										<c:if test="${o.state==1 }">已付款,<a href="javascript:alert('已提醒!!!')">提醒卖家发货</a></c:if>
										<c:if test="${o.state==2 }">已发货,<a href="javascript:">确认收货</a></c:if>
										<c:if test="${o.state==3 }">已完成,<a href="javascript:">删除订单</a></c:if>
										
									</th>
								</tr>
								<tr class="warning">
									<th>图片</th>
									<th>商品</th>
									<th>价格</th>
									<th>数量</th>
									<th>小计</th>
								</tr>
								<!-- 此时的oi代表的是订单项对象 -->
								<c:forEach items="${o.items }" var="oi">
									<tr class="active">
										<td width="60" width="40%">
											<img src="${pageContext.request.contextPath}/${oi.product.pimage}" width="70" height="60">
										</td>
										<td width="30%">
											<a target="_blank">${oi.product.pname}</a>
										</td>
										<td width="20%">
											￥${oi.product.shop_price}
										</td>
										<td width="10%">
											${oi.count}
										</td>
										<td width="15%">
											<span class="subtotal">￥${oi.subtotal}</span>
										</td>
									</tr>
								</c:forEach>
							</tbody>
						</c:forEach>
					</table>
				</div>
			</div>
			<div style="text-align: center;">
				<ul class="pagination">
				<c:if test="${pb.pageNumber!=1 }">
					<li><a href="${pageContext.request.contextPath}/OrderServlet?m=myOrderListByPagenumber&pageNumber=${pb.pageNumber-1}" aria-label="Previous"><span aria-hidden="true">&laquo;</span></a></li>
				</c:if>
						<c:forEach begin="1" end="${pb.totalPage }" var="i">
							<li     <c:if test="${pb.pageNumber==i }">class="active"</c:if>                   ><a href="${pageContext.request.contextPath}/OrderServlet?m=myOrderListByPagenumber&pageNumber=${i}">${i }</a></li>
						</c:forEach>
				<c:if test="${pb.pageNumber!=pb.totalPage }">
					<li>
						<a href="${pageContext.request.contextPath}/OrderServlet?m=myOrderListByPagenumber&pageNumber=${pb.pageNumber+1}" aria-label="Next">
							<span aria-hidden="true">&raquo;</span>
						</a>
					</li>
				</c:if>
				</ul>
			</div>
		</div>

		<div style="margin-top:50px;">
			<img src="${pageContext.request.contextPath}/image/footer.jpg" width="100%" height="78" alt="我们的优势" title="我们的优势" />
		</div>

		<div style="text-align: center;margin-top: 5px;">
			<ul class="list-inline">
				<li><a>关于我们</a></li>
				<li><a>联系我们</a></li>
				<li><a>招贤纳士</a></li>
				<li><a>法律声明</a></li>
				<li><a>友情链接</a></li>
				<li><a target="_blank">支付方式</a></li>
				<li><a target="_blank">配送方式</a></li>
				<li><a>服务声明</a></li>
				<li><a>广告声明</a></li>
			</ul>
		</div>
		<div style="text-align: center;margin-top: 5px;margin-bottom:20px;">
			Copyright &copy; 2005-2016 传智商城 版权所有
		</div>
	</body>

</html>