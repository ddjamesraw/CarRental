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
				<div class="row">
					<c:if test="${not empty requestScope.descriptionList }">
						<c:forEach var="description"
							items="${requestScope.descriptionList }">
							<div class="col-sm-6 col-md-6">
								<div class="thumbnail text-center">
									<div class="imgPicture">
										<c:if test="${!description.imgUrl.isEmpty() }">
											<img width="200px"
												src="<c:out value="${description.imgUrl }" />">
										</c:if>
										<c:if test="${description.imgUrl.isEmpty() }">
											<img width="200px" src="/CarRental/img/car.png">
										</c:if>
									</div>
									<div class="caption text-center">
										<form action="/CarRental/rental" method="POST">
											<h3>
												<span class="label label-red"><c:out
														value="${description.price }" />
													<fmt:message key="park.perday" /></span> <br />
												<br /> <input type="hidden" name="action" value="parkGet" />
												<input type="hidden" name="descriptionId"
													value="${description.id }" /> <input class="btn-link"
													type="submit"
													value="<c:out value="${description.model.brand.name }" /> <c:out value="${description.model.name }" />" />
											</h3>
										</form>
									</div>
								</div>
							</div>
						</c:forEach>
					</c:if>
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