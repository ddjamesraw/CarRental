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
				<jsp:include page="/jspf/criterionlist.jsp" />
				<c:if test="${not empty user}">
					<c:if test="${user.admin}">
						<jsp:include page="/jspf/adminmenu.jsp" />
					</c:if>
				</c:if>
			</div>
			<div class="column_2">
				<tag:messages />
				<div class="panel panel-default">
					<div class="panel-heading">
						<h3 class="panel-title text-center">
							<c:out
								value="${descriptionById.model.brand.name } ${descriptionById.model.name }" />
							<span class="label label-red"><c:out
									value="${descriptionById.price }" /> <fmt:message
									key="park.perday" /></span>
						</h3>
					</div>
					<div class="panel-body">
						<div class="col-sm-6 col-md-6">
							<div class="thumbnail">
								<div class="imgPicture">
									<c:if test="${!requestScope.descriptionById.imgUrl.isEmpty() }">
										<img width="200px"
											src="<c:out value="${descriptionById.imgUrl }" />">
									</c:if>
									<c:if test="${requestScope.descriptionById.imgUrl.isEmpty() }">
										<img width="200px" src="/CarRental/img/car.png">
									</c:if>
								</div>
								<div class="caption text-center">
									<form action="/CarRental/rental" method="POST">
										<input type="hidden" name="action" value="toOrdering" /> <input
											type="hidden" name="descriptionId"
											value="${descriptionById.id }" /> <input type="submit"
											class="btn btn-success btn-block btn-lg"
											value='<fmt:message key="park.ordering.order.submit" />' />
									</form>
								</div>
							</div>
						</div>
						<div class="col-md-6">
							<div class="caption text-center">
								<h3 class="panel-title text-center">
									<fmt:message key="park.description" />
								</h3>
							</div>
							<ul class="list-group">
								<li class="list-group-item"><span class="badge"><c:out
											value="${descriptionById.doors }" /></span> <fmt:message
										key="park.doors" /></li>
								<li class="list-group-item"><span class="badge"><c:out
											value="${descriptionById.seats }" /></span> <fmt:message
										key="park.seats" /></li>
								<li class="list-group-item"><span class="badge"><c:out
											value="${descriptionById.consumption }" /></span> <fmt:message
										key="park.consumption" /></li>
								<li class="list-group-item"><input type="checkbox"
									class="pull-right" disabled
									<c:if test="${descriptionById.airCondition }">checked</c:if>>
									<fmt:message key="park.condition" /></li>
								<li class="list-group-item"><input type="checkbox"
									class="pull-right" disabled
									<c:if test="${descriptionById.airBags }">checked</c:if>>
									<fmt:message key="park.bags" /></li>
								<li class="list-group-item"><input type="checkbox"
									class="pull-right" disabled
									<c:if test="${descriptionById.automatic }">checked</c:if>>
									<fmt:message key="park.automatic" /></li>
							</ul>
						</div>
						<br />
						<div class="col-md-12">
							<div class="panel panel-default">
								<div class="panel-heading">
									<fmt:message key="park.info" />
								</div>
								<div class="panel-body">
									<c:out value="${descriptionById.description }" />
								</div>
							</div>
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