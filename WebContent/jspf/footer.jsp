<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<c:set var="language"
	value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale.language}"
	scope="session" />
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="resources.i18n.strings" />
<div class="footer">
	<div class="pull-left">
		<form method="post">
			<select id="language" name="language" onchange="submit()"
				class="btn btn-default btn-xs dropdown-toggle">
				<option value="ru" ${language == 'ru' ? 'selected' : ''}><fmt:message
						key="main.footer.russian" /></option>
				<option value="en" ${language == 'en' ? 'selected' : ''}><fmt:message
						key="main.footer.english" /></option>
			</select>
		</form>
	</div>
	<div class="pull-right footerText">
		<small> <a href=""><fmt:message key="main.header.contacts" /></a>
			| <fmt:message key="main.footer.copyright" /> <fmt:message
				key="main.footer.rights" />
		</small>
	</div>
</div>

