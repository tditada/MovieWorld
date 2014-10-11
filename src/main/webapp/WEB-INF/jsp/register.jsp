<%@ include file="header.jsp"%>
<%@ include file="nav/start.jsp"%>
<ul class="nav navbar-nav">
	<li><a href="<c:out value="app/movies/list"/>">All movies</a></li>
</ul>
<%@ include file="nav/userMenu.jsp"%>
<%@ include file="nav/end.jsp"%>
<div class="container">
	<div class="row">
		<div class="col-lg-6">
			<form role="form" action="register" method="POST">
				<div class="form-group">
					<label for="firstname">Enter Name</label>
					<div class="input-group">
						<input type="text" class="form-control" name="firstname"
							id="firstname"
							<c:if test="${not empty firstname}"> value="${firstname}" </c:if>
							placeholder="Enter Name" required> <span
							class="input-group-addon"><span
							class="glyphicon glyphicon-asterisk"></span> Required</span>
					</div>
					<c:if test="${error0}">
						<div class="alert alert-danger" role="alert">Invalid first
							name</div>
					</c:if>
				</div>
				<div class="form-group">
					<label for="lastname">Enter Last Name</label>
					<div class="input-group">
						<input type="text" class="form-control" name="lastname"
							id="lastname"
							<c:if test="${not empty lastname}"> value="${lastname}" </c:if>
							placeholder="Enter Last Name" required> <span
							class="input-group-addon"><span
							class="glyphicon glyphicon-asterisk"></span> Required</span>
					</div>
					<c:if test="${error1}">
						<div class="alert alert-danger" role="alert">Invalid last
							name</div>
					</c:if>
				</div>
				<div class="form-group">
					<label for="email">Enter Email</label>
					<div class="input-group">
						<input type="email" class="form-control" id="email" name="email"
							<c:if test="${not empty email}"> value="${email}" </c:if>
							placeholder="Enter Email" required> <span
							class="input-group-addon"><span
							class="glyphicon glyphicon-asterisk"></span> Required</span>
					</div>
					<c:if test="${error2}">
						<div class="alert alert-danger" role="alert">Invalid Email</div>
					</c:if>
				</div>
				<div class="form-group">
					<label for="password">Enter Password</label>
					<div class="input-group">
						<input type="password" class="form-control" id="password"
							name="password" placeholder="Enter Password" required> <span
							class="input-group-addon"><span
							class="glyphicon glyphicon-asterisk"></span> Required</span>
					</div>
					<c:if test="${error3}">
						<div class="alert alert-danger" role="alert">Invalid
							Password: must be over 10 characters and less than 255 characters</div>
					</c:if>
				</div>
				<div class="form-group">
					<label for="secondPassword">Confirm Password</label>
					<div class="input-group">
						<input type="password" class="form-control" id="secondPassword"
							name="secondPassword" placeholder="Confirm Password" required>
						<span class="input-group-addon"><span
							class="glyphicon glyphicon-asterisk"></span> Required</span>
					</div>
					<c:if test="${error4}">
						<div class="alert alert-danger" role="alert">Passwords don't
							match</div>
					</c:if>
				</div>
				<div class="form-group">
					<label for="birthday">Enter Birthday</label> <input type="text"
						name="birthday"
						<c:if test="${not empty birthday}"> value="${birthday}" </c:if>
						id="datepicker">
					<c:if test="${error5}">
						<div class="alert alert-danger" role="alert">Invalid
							Birthday</div>
					</c:if>
				</div>
				<input type="submit" name="submit" id="submit" value="Submit"
					class="btn btn-primary pull-right">
			</form>
		</div>
	</div>
</div>
<%@ include file="footer.jsp"%>