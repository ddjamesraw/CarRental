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
<title><fmt:message key="administration.description" /></title>
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
						<fmt:message key="administration.description" />
					</div>
					<div class="panel-body">
						<div class="col-md-6">
							<div class="thumbnail">
								<div class="imgPicture">
									<c:if test="${!descriptionById.imgUrl.isEmpty() }">
										<img width="200px"
											src="<c:out value="${descriptionById.imgUrl }" />">
									</c:if>
									<c:if test="${descriptionById.imgUrl.isEmpty() }">
										<img width="200px" src="/CarRental/img/car.png">
									</c:if>
								</div>
								<div class="caption text-center">
									<h3>
										<a
											href="/CarRental/rental?action=modelGet&modelId=${descriptionById.model.id }">
											<c:out
												value="${descriptionById.model.brand.name } ${descriptionById.model.name }" />
										</a>
									</h3>
								</div>
							</div>
						</div>
						<div class="col-md-6">
							<ul class="list-group">
								<li class="list-group-item"><span class="badge"> <c:out
											value="${descriptionById.id }" />
								</span> <fmt:message key="administration.id" /></li>
								<li class="list-group-item"><span class="badge"> <fmt:formatNumber
											value="${descriptionById.price }" type="currency"
											currencySymbol="$" />
								</span> <fmt:message key="administration.description.price" /></li>
								<li class="list-group-item"><span class="badge"> <c:out
											value="${descriptionById.doors }" />
								</span> <fmt:message key="administration.description.doors" /></li>
								<li class="list-group-item"><span class="badge"> <c:out
											value="${descriptionById.seats }" />
								</span> <fmt:message key="administration.description.seats" /></li>
								<li class="list-group-item"><span class="badge"> <c:out
											value="${descriptionById.consumption }" />
								</span> <fmt:message key="administration.description.consumption" /></li>
								<li class="list-group-item"><input type="checkbox"
									class="pull-right" disabled
									<c:if test="${descriptionById.airCondition }">checked</c:if>>
									<fmt:message key="administration.description.condition" /></li>
								<li class="list-group-item"><input type="checkbox"
									class="pull-right" disabled
									<c:if test="${descriptionById.airBags }">checked</c:if>>
									<fmt:message key="administration.description.bags" /></li>
								<li class="list-group-item"><input type="checkbox"
									class="pull-right" disabled
									<c:if test="${descriptionById.automatic }">checked</c:if>>
									<fmt:message key="administration.description.automatic" /></li>
							</ul>
						</div>
						<br />
						<div class="col-md-12">
							<div class="panel panel-default">
								<div class="panel-heading">
									<fmt:message key="administration.description.info" />
								</div>
								<div class="panel-body">
									<c:out value="${descriptionById.description }" />
								</div>
							</div>
						</div>
						<form action="/CarRental/rental" method="POST">
							<span class="pull-left"> <input type="hidden"
								name="action" value="descriptionToEdit" /> <input type="hidden"
								name="descriptionId" value="${descriptionById.id }" /> <input
								type="submit"
								value="<fmt:message key="administration.button.edit"/>"
								class="btn-link" />
							</span>
						</form>
						<form action="/CarRental/rental" method="POST">
							<span class="pull-right"> <tag:tokenInput /> <input
								type="hidden" name="action" value="descriptionDelete" /> <input
								type="hidden" name="descriptionId"
								value="${descriptionById.id }" /> <input type="submit"
								value="<fmt:message key="administration.button.delete"/>"
								class="btn-link" />
							</span>
						</form>
					</div>
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