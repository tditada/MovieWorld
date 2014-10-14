<%@ include file="header.jsp"%>
<%@ include file="nav/start.jsp"%>
<ul class="nav navbar-nav">
	<li><a href="<c:out value="app/movies/list"/>">All movies</a></li>
</ul>
<%@ include file="nav/userMenu.jsp"%>
<%@ include file="nav/end.jsp"%>
<div class="container">
	<div class="row">
		<div class="col-md-6">
			<div id="form">
				<form:form action="app/user/login" method="post"
					commandName="loginForm">
					<form:errors path="*" class="alert alert-danger" role="alert">Invalid
						email or password</form:errors>
					<div class="form-group">
						<form:input path="email" name="email" type="text"
							class="form-control input-lg" placeholder="Email"/>
					</div>
					<div class="form-group">
						<form:input path="password" name="password" type="password"
							class="form-control input-lg" placeholder="Password"/>
					</div>
					<div class="form-group">
						<button class="btn btn-primary btn-lg btn-block">Sign In</button>
						<span class="pull-right"><a
							href="<c:url value="app/user/register"/>">Register</a></span>
					</div>
				</form:form>
			</div>
		</div>
	</div>
</div>
<%@ include file="footer.jsp"%>