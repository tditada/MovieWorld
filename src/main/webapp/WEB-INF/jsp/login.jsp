<%@ include file="header.jsp" %>
<%@ include file="navbarStart.jsp"%>
<ul class="nav navbar-nav">
	<li><a href="<c:out value="movies/list"/>">All movies</a></li>
</ul>
<%@ include file="userMenu.jsp"%>
<%@ include file="navbarEnd.jsp"%>
<div id="form" action="login" method="POST">
<form class="col-md-5" action="login" method="POST">
<c:if test="${error0 or error1}">
	<p> Invalid email or password </p>
</c:if>
<div class="form-group">
    <input type="email" name="email" class="form-control input-lg" placeholder="Email">
</div>
<div class="form-group">
    <input type="password" name="password" class="form-control input-lg" placeholder="Password">
</div>
<div class="form-group">
    <button class="btn btn-primary btn-lg btn-block">Sign In</button>
    <span class="pull-right"><a href="<c:url value="registration"/>">New Registration</a></span>
</div>
</form>
</div>
<%@ include file="footer.jsp" %>