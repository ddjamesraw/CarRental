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
<title><fmt:message key="main.title" /></title>
</head>
<body>
	<div class="wrapper">
		<jsp:include page="/jspf/header.jsp"></jsp:include>
		<div id="columns">
			<div class="column_1">
				<c:if test="${not empty user}">
					<c:if test="${user.admin}">
						<jsp:include page="/jspf/adminmenu.jsp" />
					</c:if>
				</c:if>
			</div>
			<div class="column_2">
				<div class="panel panel-default">
					<div class="panel-heading text-center">
						<fmt:message key="authentication.profile" />
						<a class="pull-right" href="/CarRental/rental?action=logout"><span
							class="glyphicon glyphicon-off"></span> <fmt:message
								key="authentication.logout" /></a>
					</div>
					<div class="panel-body">
						<ul class="list-group">
							<li class="list-group-item"><fmt:message
									key="authentication.profile.email" /><span class="pull-right"><c:out
										value="${user.email }"></c:out></span></li>
							<li class="list-group-item"><fmt:message
									key="authentication.profile.fname" /><span class="pull-right"><c:out
										value="${user.name }"></c:out></span></li>
							<li class="list-group-item"><fmt:message
									key="authentication.profile.lname" /><span class="pull-right"><c:out
										value="${user.surname }"></c:out></span></li>
							<li class="list-group-item"><fmt:message
									key="authentication.profile.address" /><span
								class="pull-right"><c:out value="${user.address }"></c:out></span></li>
							<li class="list-group-item"><fmt:message
									key="authentication.profile.phone" /><span class="pull-right"><c:out
										value="${user.phone }"></c:out></span></li>
							<li class="list-group-item"><fmt:message
									key="authentication.profile.passport" /><span
								class="pull-right"><c:out value="${user.passportNumber }"></c:out></span></li>
						</ul>
						<a
							href="/CarRental/rental?action=toPage&forward=jsp.authentication.changepassword"><fmt:message
								key="authentication.changepassword" /> <span
							class="glyphicon glyphicon-lock"></span></a> <a class="pull-right"
							href="/CarRental/rental?action=toPage&forward=jsp.authentication.editprofile"><span
							class="glyphicon glyphicon-pencil"></span> <fmt:message
								key="authentication.profile.edit" /></a> <br />
						<br />
						<div class="panel panel-default">
							<tag:messages />
							<div class="panel-heading text-center">
								<fmt:message key="authentication.profile.order.list" />
							</div>
							<table class="table">
								<thead>
									<tr>
										<th><fmt:message key="authentication.profile.order.from" /></th>
										<th><fmt:message key="authentication.profile.order.to" /></th>
										<th><fmt:message key="authentication.profile.order.total" /></th>
										<th><fmt:message
												key="authentication.profile.order.status" /></th>
										<th></th>
									</tr>
								</thead>
								<tbody>
									<c:if test="${not empty requestScope.orderList }">
										<c:forEach var="order" items="${requestScope.orderList }">
											<tr>
												<td><fmt:formatDate value="${order.from }" /></td>
												<td><fmt:formatDate value="${order.to }" /></td>
												<td><fmt:formatNumber value="${order.total }"
														type="currency" currencySymbol="$" /></td>
												<td><c:out value="${order.status.statusName }" /></td>
												<td>
													<form action="/CarRental/rental" method="POST">
														<input type="hidden" name="action" value="detail" /> <input
															type="hidden" name="orderId" value="${order.id }" /> <input
															type="submit"
															value='<fmt:message key="authentication.profile.order.detail" />'
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
			</div>
		</div>
		<div class="hFooter"></div>
	</div>
	<jsp:include page="/jspf/footer.jsp"></jsp:include>
	<div id="parent_popup">
		<p style="cursor: pointer;"
			onclick="document.getElementById('parent_popup').style.display='none';"></p>
		<div id="popup">
			<div id="login" class="well well-lg">
				<jsp:include page="/jspf/login.jsp"></jsp:include>
			</div>
			<div id="register" class="well well-lg">
				<jsp:include page="/jspf/register.jsp"></jsp:include>
			</div>
		</div>
	</div>

	<script src="/CarRental/js/jquerry.min.js" type="text/javascript"></script>
	<script src="/CarRental/js/popup.js" type="text/javascript"></script>
	<script src="/CarRental/js/bootstrap.min.js" type="text/javascript"></script>

</body>
</html>