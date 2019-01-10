<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
	<%@include file="/header.jsp" %>
	<br/>
	<br/>
	<table border="1" width="538" align="center">
		
		<tr>
			<th>订单编号</th>
			<th>数量</th>
			<th>金额</th>
			<th>状态</th>
			<th>明细</th>
		</tr>
		<c:forEach items="${os }" var="s" varStatus="vs">
			<tr class="${vs.index%2==0?'odd':'even' }">
				<td>${s.ordernum}</td>
				<td>${s.quantity}</td>
				<td>${s.money }</td>
				<td>
					<c:choose>
						<c:when test="${s.status==0 }">
							未付款<a href="${pageContext.request.contextPath }/pay.jsp?ordernum=${s.ordernum}&money=${s.money}">付款</a>
						</c:when>
						<c:when test="${s.status==1 }">
							已付款
						</c:when>
						<c:when test="${s.status==2 }">
							已发货
						</c:when>
					</c:choose>
				</td>
				<td>
					<a href="javascript:alert('略')">查看明细</a>
				</td>
			
			</tr>
		
		</c:forEach>
		
	
	
	
	</table>
	

</body>
</html>