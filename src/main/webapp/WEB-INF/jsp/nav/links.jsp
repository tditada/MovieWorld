<ul class="nav navbar-nav">
	<li><a href="<c:out value="app/movies/list"/>">All movies</a></li>
</ul>
<c:if test="${not empty user}">
	<ul class="nav navbar-nav">
		<li><a href="<c:out value="app/users/list"/>">All Users</a></li>
	</ul>
</c:if>