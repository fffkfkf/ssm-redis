<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/jsp/header.jsp" %>

<!DOCTYPE html>
<html>
	<script type="text/javascript">
		$(function(){
			if (window.top.location.href!=location.href) {
				window.top.location.href=location.href;
			}
		})
	</script>
	<body>
		<div class="container-fluid">
			<div class="container-fluid">
				<div style="text-align: center;color: red;font-size: 40px">
					${msg}					
				</div>
			</div>

		</div>
		<div class="container-fluid">
				<div style="margin-top:50px;">
					<img src="${pageContext.request.contextPath}/img/footer.jpg" width="100%" height="78" alt="我们的优势" title="我们的优势" />
				</div>
		
				<div style="text-align: center;margin-top: 5px;">
					<ul class="list-inline">
						<li><a href="info.html">关于我们</a></li>
						<li><a>联系我们</a></li>
						<li><a>招贤纳士</a></li>
						<li><a>法律声明</a></li>
						<li><a>友情链接</a></li>
						<li><a>支付方式</a></li>
						<li><a>配送方式</a></li>
						<li><a>服务声明</a></li>
						<li><a>广告声明</a></li>
					</ul>
				</div>
				<div style="text-align: center;margin-top: 5px;margin-bottom:20px;">
					Copyright &copy; 2005-2016 传智商城 版权所有
				</div>
			</div>
		</div>

	</body>

</html>