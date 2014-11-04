<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<nav class="navbar navbar-default navbar-fixed-top" role="navigation">

	<!-- Brand and toggle get grouped for better mobile display -->
	<div class="navbar-header">
		<a class="navbar-brand" href="app/home">MovieWorld</a>
	</div>
	<ul class="nav navbar-nav">
		<li><a href="<c:out value="app/movies/list"/>">All movies</a></li>
	</ul>
	<c:if test="${not empty user}">
		<c:if test="${user.admin}">
			<ul class="nav navbar-nav">
				<li><a href="<c:out value="app/movies/insert"/>">Insert
						Movie</a></li>
			</ul>
		</c:if>
		<ul class="nav navbar-nav">
			<li><a href="<c:out value="app/users/list"/>">All Users</a></li>
		</ul>
	</c:if>