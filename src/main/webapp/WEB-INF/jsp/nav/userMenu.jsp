<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<ul class="nav navbar-nav navbar-right">
	<c:if test="${not empty user}">
		<p class="navbar-text">
			<c:out
				value="Logged in as ${user.firstName.nameString} ${user.lastName.nameString}" />
		</p>
	</c:if>
	<li class="dropdown"><a href="#" class="dropdown-toggle"
		data-toggle="dropdown">Menu<span class="caret"></span></a>
		<ul class="dropdown-menu" role="menu">
			<c:choose>
				<c:when test="${empty user}">
					<li><a href="app/users/login">Login/Register</a></li>
				</c:when>
				<c:otherwise>
					<li><a href="app/users/profile?user=${user.id}">My Comments</a></li>
					<li class="divider"></li>
					<li><a class="btn-link black-link logout">Logout</a></li>
				</c:otherwise>
			</c:choose>
		</ul></li>
</ul>