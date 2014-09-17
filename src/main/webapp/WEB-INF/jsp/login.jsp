<%@ include file="header.jsp" %>
<div id="form">
<form class="col-md-5" action="login" method="POST">
    <div class="form-group">
        <input type="email" name="email" class="form-control input-lg" placeholder="Email">
    </div>
    <div class="form-group">
        <input type="password" name="password" class="form-control input-lg" placeholder="Password">
    </div>
    <div class="form-group">
        <button class="btn btn-primary btn-lg btn-block">Sign In</button>
        <span><a href="#">Need help?</a></span>
        <span class="pull-right"><a href="#">New Registration</a></span>
    </div>
</form>
</div>
<%@ include file="footer.jsp" %>