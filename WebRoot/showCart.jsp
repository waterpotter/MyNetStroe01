<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
	<%@include file="/header.jsp" %>
	<br/>
	<c:if test="${empty sessionScope.cart.items }">
		<h3>您还没有购买任何商品</h3>
	</c:if>
	<c:if test="${!empty sessionScope.cart.items }">
		<h3>您购买商品如下</h3>
		<table border="1" width="738" align="center">
		<tr>
			<th>书名</th>
			<th>数量</th>
			<th>单价</th>
			<th>小计</th>
			<th>操作</th>
		</tr>
		<c:forEach items="${sessionScope.cart.items}" var="me" varStatus="vs">
			<tr class="${vs.index%2==0? 'odd':'even'}">
				<td>${me.value.book.name }</td>
				<td><input type="text" id="quantity" value=${me.value.quantity } size=2 onchange="changeNum(this,${me.value.quantity},'${me.value.book.id}')"/></td>
				<td>${me.value.book.price }</td>
				<td>${me.value.money}</td>
				<td>
					<a href="javascript:delOneItem('${me.key }')">删除</a>
					<a href="">修改</a>
				</td>
			</tr>
		</c:forEach>
			
		<tr>
			<td colspan="5" aligh="right">
				<a href="javascript:delAllItems()">清空购物车</a>
				总数量:${sessionScope.cart.totalQuantity }&nbsp;&nbsp;
				应付金额:${sessionScope.cart.totalMoney }&nbsp;&nbsp;
				<a href="${pageContext.request.contextPath}/client/ClientServlet?op=genOrder">去结算</a>	
			</td>
		</tr>
	
		</table>
	</c:if>
	
	<script type="text/javascript">
		
		function delAllItems(){
			var sure=window.confirm("确定要删除所有数据吗?");
			if(sure){
				window.location.href="${pageContext.request.contextPath}/client/ClientServlet?op=delAllItem";
			}
		}
		
		function delOneItem(bookId){
			var sure=window.confirm("确定要删除吗?");
			if(sure){
				window.location.href="${pageContext.request.contextPath}/client/ClientServlet?op=delOneItem&bookId="+bookId;
			}
		}
		
		function changeNum(inputObj,oldNum,bookId){
			var sure=window.confirm("确定要修改吗?");
			if(sure){
				//修改成新的值	
				var num=inputObj.value;
				if(!/^[1-9][0-9]*$/.test(num)){
					alert("请输入正确的数量!!");
					inputObj.value=oldNum;
					return;
				}
				
				//提交给服务器,修改该项的数量
				window.location.href="${pageContext.request.contextPath}/client/ClientServlet?op=changeNum&num="+num+"&bookId="+bookId;
				
			}else{
				//使用原来的值
				inputObj.value=oldNum;
			}
		}
		
		
	</script>
</body>
</html>