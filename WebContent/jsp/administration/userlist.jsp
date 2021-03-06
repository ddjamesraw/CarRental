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
<title><fmt:message key="administration.user.list" /></title>
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
						<fmt:message key="administration.user.list" />
					</div>
					<table class="table">
						<thead>
							<tr>
								<th><fmt:message key="administration.id" /></th>
								<th><fmt:message key="administration.user.email" /></th>
								<th><fmt:message key="administration.user.admin" /></th>
								<th>
									<form action="/CarRental/rental" method="POST">
										<input type="hidden" name="action" value="userToAdd" /> <input
											type="submit"
											value="<fmt:message key="administration.button.add" />"
											class="btn-link" />
									</form>
								</th>
							</tr>
						</thead>
						<tbody>
							<c:if test="${not empty requestScope.userList }">
								<c:forEach var="user" items="${requestScope.userList }">
									<tr>
										<td><c:out value="${user.id }" /></td>
										<td><c:out value="${user.email }" /></td>
										<td><input type="checkbox" disabled
											<c:if test="${user.admin }">checked</c:if>></td>
										<td>
											<form action="/CarRental/rental" method="POST">
												<input type="hidden" name="action" value="userGet" /> <input
													type="hidden" name="userId" value="${user.id }" /> <input
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