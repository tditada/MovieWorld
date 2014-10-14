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
			<form:form role="form" action="app/user/register" method="post"
				commandName="registrationForm">
				<div class="form-group">
					<label for="firstName">Enter Name</label>
					<div class="input-group">
						<form:input type="text" path="firstName" class="form-control"
							name="firstName" id="firstName" placeholder="Enter Name" />
						<span class="input-group-addon"><span
							class="glyphicon glyphicon-asterisk"></span> Required</span>
					</div>
					<form:errors path="firstName" class="alert alert-danger"
						role="alert">Invalid first name</form:errors>
				</div>
				<div class="form-group">
					<label for="lastName">Enter Last Name</label>
					<div class="input-group">
						<form:input type="text" path="lastName" class="form-control"
							name="lastName" id="lastName" placeholder="Enter Name" />
						<span class="input-group-addon"><span
							class="glyphicon glyphicon-asterisk"></span> Required</span>
					</div>
					<form:errors path="lastName" class="alert alert-danger"
						role="alert">Invalid last name</form:errors>
				</div>
				<div class="form-group">
					<label for="email">Enter Email</label>
					<div class="input-group">
						<form:input type="text" path="email" class="form-control"
							name="email" id="email" placeholder="Enter Email" />
						<span class="input-group-addon"><span
							class="glyphicon glyphicon-asterisk"></span> Required</span>
					</div>
					<form:errors path="email" class="alert alert-danger" role="alert">Invalid email</form:errors>
				</div>
				<div class="form-group">
					<label for="password">Enter Password</label>
					<div class="input-group">
						<form:input type="password" path="password" class="form-control"
							name="password" id="password" placeholder="Enter Password" />
						<span class="input-group-addon"><span
							class="glyphicon glyphicon-asterisk"></span> Required</span>
					</div>
					<form:errors path="password" class="alert alert-danger"
						role="alert">Invalid Password: must be over 10 characters and less than 255 characters</form:errors>
				</div>
				<div class="form-group">
					<label for="passwordConfirmation">Confirm Password</label>
					<div class="input-group">
						<form:input type="password" path="passwordConfirmation"
							class="form-control" name="passwordConfirmation"
							id="passwordConfirmation" placeholder="Enter Password" />
						<span class="input-group-addon"><span
							class="glyphicon glyphicon-asterisk"></span> Required</span>
					</div>
					<form:errors path="passwordConfirmation" class="alert alert-danger"
						role="alert">Passwords don't match</form:errors>
				</div>
				<div class="form-group">
					<label for="birthDate">Enter Birthday</label>
					<form:input type="text" name="birthDate" path="birthDate"
						id="datepicker" />
					<form:errors path="birthDate" class="alert alert-danger"
						role="alert">Invalid Birthday</form:errors>
				</div>
				<input type="submit" name="submit" id="submit" value="Submit"
					class="btn btn-primary pull-right">
			</form:form>
		</div>
	</div>
</div>
<%@ include file="footer.jsp"%>