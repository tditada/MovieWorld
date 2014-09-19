<%@ include file="header.jsp" %>
<%@ include file="headerend.jsp"%>
<%@ include file="navbarStart.jsp"%>
<ul class="nav navbar-nav">
	<li><a href="<c:out value="movies/list"/>">All movies</a></li>
</ul>
<%@ include file="userMenu.jsp"%>
<%@ include file="navbarEnd.jsp"%>
<div class="container">
    <div class="row">
        <form role="form" action="registration" method="POST">
            <div class="col-lg-6">
            <c:if test="${error0}">
				<p> Invalid Name </p>
			</c:if>
			<c:if test="${error1}">
				<p> Invalid Last Name </p>
			</c:if>
			<c:if test="${error2}">
				<p> Invalid Email </p>
			</c:if>
			<c:if test="${error3}">
				<p> Invalid Password: must be over 10 characters and less than 255 characters</p>
			</c:if>
			<c:if test="${error4}">
				<p> Passwords don't match</p>
			</c:if>
			<c:if test="${error5}">
				<p>Invalid Birthday</p>
			</c:if>
                <div class="well well-sm"><strong><span class="glyphicon glyphicon-asterisk"></span>Required Field</strong></div>
                <div class="form-group">
                    <label for="firstname">Enter Name</label>
                    <div class="input-group">
                        <input type="text" class="form-control" name="firstname" id="firstname" <c:if test="${not empty firstname}"> value="${firstname}" </c:if> placeholder="Enter Name" required>
                        <span class="input-group-addon"><span class="glyphicon glyphicon-asterisk"></span></span>
                    </div>
                </div>
                <div class="form-group">
                    <label for="lastname">Enter Last Name</label>
                    <div class="input-group">
                        <input type="text" class="form-control" name="lastname" id="lastname" <c:if test="${not empty lastname}"> value="${lastname}" </c:if>  placeholder="Enter Last Name" required>
                        <span class="input-group-addon"><span class="glyphicon glyphicon-asterisk"></span></span>
                    </div>
                </div>
                <div class="form-group">
                    <label for="email">Enter Email</label>
                    <div class="input-group">
                        <input type="email" class="form-control" id="email" name="email" <c:if test="${not empty email}"> value="${email.asTextAddress}" </c:if>  placeholder="Enter Email" required>
                        <span class="input-group-addon"><span class="glyphicon glyphicon-asterisk"></span></span>
                    </div>
                </div>
                <div class="form-group">
                    <label for="password">Enter Password</label>
                    <div class="input-group">
                        <input type="password" class="form-control" id="password" name="password" placeholder="Enter Password" required>
                        <span class="input-group-addon"><span class="glyphicon glyphicon-asterisk"></span></span>
                    </div>
                </div>
                <div class="form-group">
                    <label for="secondPassword">Confirm Password</label>
                    <div class="input-group">
                        <input type="password" class="form-control" id="secondPassword" name="secondPassword" placeholder="Confirm Password" required>
                        <span class="input-group-addon"><span class="glyphicon glyphicon-asterisk"></span></span>
                    </div>
                </div>
                <div class="form-group">
	                <label for="birthday">Enter Birthday</label>
	                <input type="text" name="birthday" <c:if test="${not empty birthday}"> value="${birthday}" </c:if>  id="datepicker">
	            </div>
                <input type="submit" name="submit" id="submit" value="Submit" class="btn btn-info pull-right">
            </div>
        </form>
    </div>
</div>
<%@ include file="footer.jsp" %>