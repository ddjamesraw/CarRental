<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<c:set var="language"
	value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale.language}"
	scope="session" />
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="resources.i18n.strings" />
<div class="header">
	<div class="menu">
		<div class="m_component1">
			<a href="/CarRental/rental"><fmt:message key="main.header.main" /></a>
		</div>
		<div class="m_component">
			<a href=""><fmt:message key="main.header.terms" /></a>
		</div>
		<div class="m_component">
			<a href=""><fmt:message key="main.header.about" /></a>
		</div>
		<div class="m_component">
			<a href=""><fmt:message key="main.header.contacts" /></a>
		</div>
		<div class="m_component">
			<c:if test="${not empty user}">
				<a href="/CarRental/rental?action=profile"><fmt:message
						key="authentication.profile" /></a>
			</c:if>
			<c:if test="${empty user}">
				<a style="cursor: pointer;" onclick="login_popup();"><fmt:message
						key="authentication.login.signin" /></a>
			</c:if>
		</div>
	</div>
</div>


