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
<title><fmt:message key="administration.description.add" /></title>
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
				<form action="/CarRental/rental" method="POST" class="form">
					<tag:tokenInput />
					<input type="hidden" name="action" value="descriptionAdd" />
					<div class="panel panel-default text-center">
						<div class="panel-heading">
							<fmt:message key="administration.description.add" />
						</div>
						<div class="panel-body">
							<div class="text-center">
								<fmt:message key="administration.description.model" />
								<select name="modelId"
									class="btn btn-default btn-xs dropdown-toggle">
									<c:forEach var="model" items="${modelList }">
										<option value="${model.id }">
											<c:out value="${model.name }" />
										</option>
									</c:forEach>
								</select>
							</div>
							<br />
							<div class="input-group">
								<span class="input-group-addon"><span
									class="glyphicon glyphicon-picture"></span></span> <input type="text"
									name="imgUrl" class="form-control"
									placeholder="<fmt:message key="administration.description.img"/>">
							</div>
							<br />
							<div class="input-group">
								<span class="input-group-addon"><fmt:message
										key="administration.description.price" /></span> <input
									type="number" name="price" class="form-control" placeholder="0">
							</div>
							<br />
							<div class="input-group">
								<span class="input-group-addon"><fmt:message
										key="administration.description.doors" /></span> <input
									type="number" name="doors" class="form-control" placeholder="0">
							</div>
							<br />
							<div class="input-group">
								<span class="input-group-addon"><fmt:message
										key="administration.description.seats" /></span> <input
									type="number" name="seats" class="form-control" placeholder="0">
							</div>
							<br />
							<div class="input-group">
								<span class="input-group-addon"><fmt:message
										key="administration.description.consumption" /></span> <input
									type="number" name="consumption" class="form-control"
									placeholder="0">
							</div>
							<br />
							<div class="input-group">
								<span class="input-group-addon"><span class="pull-left"><fmt:message
											key="administration.description.condition" /></span></span> <span
									class="input-group-addon"><input class="pull-right"
									type="checkbox" name="airCondition" value="true" /></span>
							</div>
							<br />
							<div class="input-group">
								<span class="input-group-addon"><span class="pull-left"><fmt:message
											key="administration.description.bags" /></span></span> <span
									class="input-group-addon"><input class="pull-right"
									type="checkbox" name="airBags" value="true" /></span>
							</div>
							<br />
							<div class="input-group">
								<span class="input-group-addon"><span class="pull-left"><fmt:message
											key="administration.description.automatic" /></span></span> <span
									class="input-group-addon"><input class="pull-right"
									type="checkbox" name="automatic" value="true" /></span>
							</div>
							<br />
							<div class="input-group">
								<span class="input-group-addon"><fmt:message
										key="administration.description.info" /></span>
								<textarea name="description" class="form-control"
									placeholder="Info"></textarea>
							</div>
							<br /> <input type="submit"
								value="<fmt:message key="administration.button.add"/>"
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