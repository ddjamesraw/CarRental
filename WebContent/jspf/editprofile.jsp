<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/taglib.tld" prefix="tag"%>
<c:set var="language"
	value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale.language}"
	scope="session" />
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="resources.i18n.strings" />
<c:if test="${empty user}">
	<jsp:forward page="/rental">
		<jsp:param name="action" value="toPage" />
		<jsp:param name="forward" value="jsp.authentication.login" />
	</jsp:forward>
</c:if>
<form action="/CarRental/rental" method="POST" class="form">
	<tag:tokenInput />
	<input type="hidden" name="action" value="editProfile" />
   <tag:messages />
   <div class="panel panel-default text-center">
  <div class="panel-heading"><fmt:message key="authentication.profile.edit" /></div>
  <div class="panel-body">
     <div class="input-group">
  		<span class="input-group-addon"><span class="glyphicon glyphicon-user"></span></span>
  		<input id="name" type="text" name="name"
				value="${user.name }" class="form-control" placeholder="<fmt:message
						key="authentication.profile.fname" />">
	</div>
      <br />
      <div class="input-group">
  		<span class="input-group-addon"><span class="glyphicon glyphicon-user"></span></span>
  		<input id="surname" type="text" name="surname"
				value="${user.surname }" class="form-control" placeholder="<fmt:message
						key="authentication.profile.lname" />">
	</div>
      <br />
      <div class="input-group">
  		<span class="input-group-addon"><span class="glyphicon glyphicon-font"></span></span>
  		<input id="address" type="text" name="address"
				value="${user.address }" class="form-control" placeholder="<fmt:message
						key="authentication.profile.address" />">
	</div>
      <br />
      <div class="input-group">
  		<span class="input-group-addon"><span class="glyphicon glyphicon-phone"></span></span>
  		<input id="phone" type="tel" name="phone"
				value="${user.phone }" class="form-control" placeholder="<fmt:message
						key="authentication.profile.phone" />">
	</div>
      <br />
      <div class="input-group">
  		<span class="input-group-addon"><span class="glyphicon glyphicon-book"></span></span>
  		<input id="passport" type="text" name="passport"
				value="${user.passportNumber }" class="form-control" placeholder="<fmt:message
						key="authentication.profile.passport" />">
	</div>
    <br />
    <button class="btn btn-m btn-danger" type="submit">
			<fmt:message key="authentication.profile.edit.edit"/>
		</button>
  </div>
</div>
</form>