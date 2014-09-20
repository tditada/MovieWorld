<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<ul class="nav navbar-nav navbar-right">
	<c:if test="${not empty user}">
		<p class="navbar-text">
			<c:out value="Logged in as ${user.firstName} ${user.lastName}" />
		</p>
	</c:if>
	<li class="dropdown"><a href="#" class="dropdown-toggle"
		data-toggle="dropdown">Menu<span class="caret"></span></a>
		<ul class="dropdown-menu" role="menu">
			<c:choose>
				<c:when test="${empty user}">
					<li><a href="login">Login</a></li>
				</c:when>
				<c:otherwise>
					<li><a href="me/comments/all">My Comments</a></li>
					<li class="divider"></li>
					<li><a class="btn-link black-link logout">Logout</a></li>
				</c:otherwise>
			</c:choose>
		</ul></li>
</ul>