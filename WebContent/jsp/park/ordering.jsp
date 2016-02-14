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
				<tag:messages />
				<form action="/CarRental/rental" method="POST"
					class="form form-207px">
					<tag:tokenInput />
					<input type="hidden" name="action" value="ordering" /> <input
						type="hidden" name="descriptionId" value="${descriptionById.id }">
					<div class="panel panel-default text-center">
						<div class="panel-heading">
							<fmt:message key="park.ordering.order" />
						</div>
						<div class="panel-body">
							<div class="input-group">
								<span class="input-group-addon"> <fmt:message
										key="park.ordering.from" />
								</span> <input id="from" type="date" name="from" class="form-control" />
							</div>
							<br />
							<div class="input-group">
								<span class="input-group-addon"> <fmt:message
										key="park.ordering.to" />
								</span> <input id="to" type="date" name="to" class="form-control" />
							</div>
							<br /> <input type="submit" class="btn btn-m btn-success"
								value="<fmt:message key="park.ordering.order.submit" />" />
						</div>
					</div>
				</form>
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