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
						<label for="title">Enter title</label>
						<div class="input-group">
							<form:input type="text" path="title" class="form-control"
								name="title" id="title" value="${movie.title}"></form:input>
							<span class="input-group-addon"><span
								class="glyphicon glyphicon-asterisk"></span> Required</span>
						</div>
						<form:errors path="title">
							<div class="alert alert-danger" role="alert">Invalid movie
								name</div>
						</form:errors>
					</div>
					<div class="form-group">
						<label for="director">Enter Director</label>
						<div class="input-group">
							<form:input type="text" path="director" class="form-control"
								name="director" id="director" value="${movie.director.name}" />
							<span class="input-group-addon"><span
								class="glyphicon glyphicon-asterisk"></span> Required</span>
						</div>
						<form:errors path="director">
							<div class="alert alert-danger" role="alert">Invalid
								director's name</div>
						</form:errors>
					</div>
					<div class="form-group">
						<label for="summary">Enter Summary</label>
						<div class="input-group">
							<form:input type="text" path="summary" class="form-control"
								name="summary" id="summary" value="${movie.summary}" />
							<span class="input-group-addon"><span
								class="glyphicon glyphicon-asterisk"></span> Required</span>
						</div>
						<form:errors path="summary">
							<div class="alert alert-danger" role="alert">Invalid
								Summary</div>
						</form:errors>
					</div>
					<div class="form-group">
						<label for="genres">Enter Genres</label>
						<div class="input-group">
							<form:input type="text" path="genres" class="form-control"
								name="genres" id="genres" value="${movie.genres}" />
							<span class="input-group-addon"><span
								class="glyphicon glyphicon-asterisk"></span> Required</span>
						</div>
						<form:errors path="genres">
							<div class="alert alert-danger" role="alert">Invalid Genres</div>
						</form:errors>
					</div>
					<div class="form-group">
						<label for="runtimeInMins" class="control-label">Runtime
							In Minutes</label>
						<form:input type="number" min="1" path="runtimeInMins"
							class="form-control" name="runtimeInMins" id="runtimeInMins"
							value="${movie.runtimeInMins}" />
						<span class="input-group-addon"><span
							class="glyphicon glyphicon-asterisk"></span> Required</span>
					</div>
					<div class="form-group">
						<label for="releaseDate">Enter Release Date</label>
						<form:input type="text" name="releaseDate" path="releaseDate"
							id="movieDatepicker" value="${movie.releaseDate}" />
						<form:errors path="releaseDate">
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