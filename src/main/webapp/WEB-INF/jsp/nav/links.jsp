<ul class="nav navbar-nav">
	<li><a href="<c:out value="app/movies/list"/>">All movies</a></li>
</ul>
<c:if test="${user.isAdmin}">
	<ul class="nav navbar-nav">
		<li><a href="<c:out value="app/movies/insert"/>">Insert Movie</a></li>
	</ul>
</c:if>
<c:if test="${not empty user}">
	<ul class="nav navbar-nav">
		<li><a href="<c:out value="app/users/list"/>">All Users</a></li>
	</ul>
</c:if>