<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/taglib.tld" prefix="tag"%>
<tag:tokenGenerate />
<c:set var="language"
	value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale.language}"
	scope="session" />
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="resources.i18n.strings" />
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css"
	href="/CarRental/css/bootstrap.min.css" />
<link rel="stylesheet" type="text/css" href="/CarRental/css/site.css" />
<title><fmt:message key="administration.order.list" /></title>
</head>
<body>
	<div class="wrapper">
		<jsp:include page="/jspf/header.jsp"></jsp:include>
		<div id="columns">
			<div class="column_1">
				<jsp:include page="/jspf/adminmenu.jsp" />
			</div>
			<div class="column_2">
				<tag:messages />
				<div class="panel panel-default">
					<div class="panel-heading text-center">
						<fmt:message key="administration.order.list" />
					</div>
					<table class="table">
						<thead>
							<tr>
								<th><fmt:message key="administration.id" /></th>
								<th><fmt:message key="administration.order.user.email" /></th>
								<th><fmt:message key="administration.order.from" /></th>
								<th><fmt:message key="administration.order.to" /></th>
								<th><fmt:message key="administration.order.status" /></th>
								<th>
									<form action="/CarRental/rental" method="POST">
										<input type="hidden" name="action" value="orderToAdd" /> <input
											type="submit"
											value="<fmt:message key="administration.button.add" />"
											class="btn-link" />
									</form>
								</th>
							</tr>
						</thead>
						<tbody>
							<c:if test="${not empty requestScope.orderList }">
								<c:forEach var="order" items="${requestScope.orderList }">
									<tr>
										<td><c:out value="${order.id }" /></td>
										<td><c:out value="${order.user.email }" /></td>
										<td><fmt:formatDate value="${order.from }" /></td>
										<td><fmt:formatDate value="${order.to }" /></td>
										<td><c:out value="${order.status.statusName }" /></td>
										<td>
											<form action="/CarRental/rental" method="POST">
												<input type="hidden" name="action" value="orderGet" /> <input
													type="hidden" name="orderId" value="${order.id }" /> <input
													type="submit"
													value="<fmt:message key="administration.button.detail" />"
													class="btn-link" />
											</form>
										</td>
									</tr>
								</c:forEach>
							</c:if>
						</tbody>
					</table>
				</div>
			</div>
		</div>
		<div class="hFooter"></div>
	</div>
	<jsp:include page="/jspf/footer.jsp"></jsp:include>

	<script src="/CarRental/js/jquerry.min.js" type="text/javascript"></script>
	<script src="/CarRental/js/bootstrap.min.js" type="text/javascript"></script>

</body>
</html>