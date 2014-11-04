<%@ include file="../header.jsp"%>
<%@ include file="../nav/start.jsp"%>
<ul class="nav navbar-nav">
</ul>
<%@ include file="../nav/userMenu.jsp"%>
<%@ include file="../nav/end.jsp"%>
<div class="container">
	<div class="row">
		<div class="col-lg-6">
			<c:if test="${not empty movie and user.admin}">
				<form:form role="form" action="app/movies/edit" method="post"
					commandName="MovieForm">
					<div class="form-group">
						<label for="movieTitle">Movie Title</label>
						<div class="input-group">
							<form:input type="text" path="movieTitle" class="form-control"
								name="movieTitle" id="movieTitle" value="${movie.title}"></form:input>
							<span class="input-group-addon"><span
								class="glyphicon glyphicon-asterisk"></span> Required</span>
						</div>
						<form:errors path="movieTitle">
							<div class="alert alert-danger" role="alert">Invalid movie
								name</div>
						</form:errors>
					</div>
					<div class="form-group">
						<label for="movieDirector">Enter Director</label>
						<div class="input-group">
							<form:input type="text" path="movieDirector" class="form-control"
								name="movieDirector" id="movieDirector"
								value="${movie.director.name}" />
							<span class="input-group-addon"><span
								class="glyphicon glyphicon-asterisk"></span> Required</span>
						</div>
						<form:errors path="movieDirector">
							<div class="alert alert-danger" role="alert">Invalid
								director's name</div>
						</form:errors>
					</div>
					<div class="form-group">
						<label for="movieSummary">Enter Summary</label>
						<div class="input-group">
							<form:input type="text" path="movieSummary" class="form-control"
								name="movieSummary" id="movieSummary" value="${movie.summary}" />
							<span class="input-group-addon"><span
								class="glyphicon glyphicon-asterisk"></span> Required</span>
						</div>
						<form:errors path="movieSummary">
							<div class="alert alert-danger" role="alert">Invalid
								Summary</div>
						</form:errors>
					</div>
					<div class="form-group">
						<label for="movieGenres">Enter Genres</label>
						<div class="input-group">
							<form:input type="text" path="movieGenres" class="form-control"
								name="movieGenres" id="movieGenres" value="${genres}" />
							<span class="input-group-addon"><span
								class="glyphicon glyphicon-asterisk"></span> Required</span>
						</div>
						<form:errors path="movieGenres">
							<div class="alert alert-danger" role="alert">Invalid Genres</div>
						</form:errors>
					</div>
					<div class="form-group">
						<label for="movieRuntimeInMins" class="control-label">Runtime
							In Minutes</label>
						<form:input type="number" min="1" path="movieRuntimeInMins"
							class="form-control" name="movieRuntimeInMins"
							id="movieRuntimeInMins" value="${movie.runtimeInMins}" />
						<span class="input-group-addon"><span
							class="glyphicon glyphicon-asterisk"></span> Required</span>
					</div>
					<div class="form-group">
						<label for="movieReleaseDate">Enter Release Date</label>
						<form:input type="text" name="movieReleaseDate"
							path="movieReleaseDate" id="movieDatepicker"
							value="${releaseDate}" />
						<form:errors path="movieReleaseDate">
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