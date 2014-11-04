<%@ include file="../header.jsp"%>
<%@ include file="../nav/start.jsp"%>
<ul class="nav navbar-nav">
</ul>
<%@ include file="../nav/userMenu.jsp"%>
<%@ include file="../nav/end.jsp"%>
<div class="container">
	<div class="row">
		<div class="col-lg-6">
			<c:if test="${user.admin}">
				<form:form role="form" action="app/movies/insert" method="post"
					commandName="movieForm">
					<div class="form-group">
						<label for="filmTitle">Film Title</label>
						<div class="input-group">
							<form:input type="text" path="filmTitle" class="form-control"
								name="filmTitle" id="filmTitle" placeholder="Enter Film's Title" />
							<span class="input-group-addon"><span
								class="glyphicon glyphicon-asterisk"></span> Required</span>
						</div>
						<form:errors path="filmTitle">
							<div class="alert alert-danger" role="alert">Invalid film
								name</div>
						</form:errors>
					</div>
					<div class="form-group">
						<label for="filmDirector">Enter Director</label>
						<div class="input-group">
							<form:input type="text" path="filmDirector" class="form-control"
								name="filmDirector" id="filmDirector"
								placeholder="Enter Director" />
							<span class="input-group-addon"><span
								class="glyphicon glyphicon-asterisk"></span> Required</span>
						</div>
						<form:errors path="filmDirector">
							<div class="alert alert-danger" role="alert">Invalid
								director's name</div>
						</form:errors>
					</div>
					<div class="form-group">
						<label for="filmSummary">Enter Summary</label>
						<div class="input-group">
							<form:input type="text" path="filmSummary" class="form-control"
								name="filmSummary" id="filmSummary" placeholder="Enter Summary" />
							<span class="input-group-addon"><span
								class="glyphicon glyphicon-asterisk"></span> Required</span>
						</div>
						<form:errors path="filmSummary">
							<div class="alert alert-danger" role="alert">Invalid
								Summary</div>
						</form:errors>
					</div>
					<div class="form-group">
						<label for="filmGenres">Enter Genres</label>
						<div class="input-group">
							<form:input type="text" path="filmGenres" class="form-control"
								name="filmGenres" id="filmGenres" placeholder="Action, Commedy" />
							<span class="input-group-addon"><span
								class="glyphicon glyphicon-asterisk"></span> Required</span>
						</div>
						<form:errors path="filmGenres">
							<div class="alert alert-danger" role="alert">Invalid Genres</div>
						</form:errors>
					</div>
					<div class="form-group">
						<label for="filmRuntimeInMins" class="control-label">Runtime
							In Minutes</label>
						<form:input type="number" min="1" path="filmRuntimeInMins"
							class="form-control" name="filmRuntimeInMins"
							id="filmRuntimeInMins" />
						<span class="input-group-addon"><span
							class="glyphicon glyphicon-asterisk"></span> Required</span>
					</div>
					<div class="form-group">
						<label for="filmReleaseDate">Enter Release Date</label>
						<form:input type="text" name="filmReleaseDate"
							path="filmReleaseDate" id="datepicker" />
						<form:errors path="filmReleaseDate">
							<div class="alert alert-danger" role="alert">Invalid
								Release Date</div>
						</form:errors>
					</div>
					<input type="submit" name="submit" id="submit" value="Submit"
						class="btn btn-primary pull-right">
				</form:form>
			</c:if>
		</div>
	</div>
</div>
<%@ include file="../footer.jsp"%>