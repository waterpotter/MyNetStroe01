<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
	<%@include file="/manage/header.jsp" %>
	<a href="${pageContext.request.contextPath}">所有分类:</a>
	<br/>
	<br/>
	<c:forEach items="${cs }" var="c" varStatus="vs">
		<a href="#">${c.name }</a>
	</c:forEach>
	
	<table border="1" width="738" align="center">
		<tr>
			<c:forEach items="${page.records }" var="b">
				<td>
					<img alt="${b.filename}" src="${pageContext.request.contextPath}/images/${b.path}/${b.filename}">
					书名:${b.name }<br>
					作者:${b.author }<br>
					价格:${b.price }<br>
				</td>
			</c:forEach>
		</tr>
	</table>
	<br/>
	<tr>
		<td colspan="8">
			<%@include file="/common/page.jsp" %>
		</td>
	</tr>

</body>
</html>