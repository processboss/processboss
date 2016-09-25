<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 

<c:if test="${not empty loginBean.user}">
	<%response.sendRedirect("/processboss/dashboard/index.jsf"); %>
</c:if>

<c:if test="${empty loginBean.user}">
	<%response.sendRedirect("/processboss/login.jsf"); %>
</c:if>