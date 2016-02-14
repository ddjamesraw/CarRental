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
<title><fmt:message key="administration.user.edit" /></title>
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
				<form action="/CarRental/rental" method="POST"
					class="form form-editProfile">
					<tag:tokenInput />
					<input type="hidden" name="action" value="userEdit" /> <input
						type="hidden" name="userId" value="${userById.id }" />
					<div class="panel panel-default text-center">
						<div class="panel-heading text-left">
							<fmt:message key="administration.user.edit" />
							<span class="pull-right"><fmt:message
									key="administration.user.admin" /> <input type="checkbox"
								name="admin" value="true"
								<c:if test="${userById.admin }">checked</c:if> /> </span>
						</div>
						<div class="panel-body">
							<div class="input-group">
								<span class="input-group-addon">@</span> <input type="email"
									name="email" value="${userById.email }" class="form-control"
									placeholder="<fmt:message key="administration.user.email" />">
							</div>
							<br />
							<div class="input-group">
								<span class="input-group-addon"><span
									class="glyphicon glyphicon-user"></span></span> <input type="text"
									name="name" value="${userById.name }" class="form-control"
									placeholder="<fmt:message key="administration.user.fname" />">
							</div>
							<br />
							<div class="input-group">
								<span class="input-group-addon"><span
									class="glyphicon glyphicon-user"></span></span> <input type="text"
									name="surname" value="${userById.surname }"
									class="form-control"
									placeholder="<fmt:message key="administration.user.lname" />">
							</div>
							<br />
							<div class="input-group">
								<span class="input-group-addon"><span
									class="glyphicon glyphicon-font"></span></span> <input type="text"
									name="address" value="${userById.address }"
									class="form-control"
									placeholder="<fmt:message key="administration.user.address" />">
							</div>
							<br />
							<div class="input-group">
								<span class="input-group-addon"><span
									class="glyphicon glyphicon-phone"></span></span> <input type="tel"
									name="phone" value="${userById.phone }" class="form-control"
									placeholder="<fmt:message key="administration.user.phone" />">
							</div>
							<br />
							<div class="input-group">
								<span class="input-group-addon"><span
									class="glyphicon glyphicon-book"></span></span> <input type="text"
									name="passport" value="${userById.passportNumber }"
									class="form-control"
									placeholder="<fmt:message key="administration.user.passport" />">
							</div>
							<br /> <input type="submit"
								value="<fmt:message key="administration.button.edit" />"
								class="btn btn-danger btn-m" />
						</div>
					</div>
				</form>
			</div>
		</div>
		<div class="hFooter"></div>
	</div>
	<jsp:include page="/jspf/footer.jsp"></jsp:include>

	<script src="/CarRental/js/jquerry.min.js" type="text/javascript"></script>
	<script src="/CarRental/js/bootstrap.min.js" type="text/javascript"></script>

</body>
</html>