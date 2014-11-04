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
						<label for="filmTitle">Film Title</label>
						<div class="input-group">
							<form:input type="text" path="filmTitle" class="form-control"
								name="filmTitle" id="filmTitle" value="${movie.title}"></form:input>
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
								value="${movie.director.name}" />
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
								name="filmSummary" id="filmSummary" value="${movie.summary}" />
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
								name="filmGenres" id="filmGenres" value="${genres}" />
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
							id="filmRuntimeInMins" value="${movie.runtimeInMins}" />
						<span class="input-group-addon"><span
							class="glyphicon glyphicon-asterisk"></span> Required</span>
					</div>
					<div class="form-group">
						<label for="filmReleaseDate">Enter Release Date</label>
						<form:input type="text" name="filmReleaseDate"
							path="filmReleaseDate" id="filmDatepicker" value="${releaseDate}" />
						<form:errors path="filmReleaseDate">
							<div class="alert alert-danger" role="alert">Invalid
								Release Date</div>
						</form:errors>
					</div>
					<input type="submit" name="submit" id="submit" value="Submit"
						class="btn btn-primary pull-right">
				</form:form>
			</c:if>
			<c:if test="${empty movie}">
				<p>Ups! ha ocurrido un error</p>
			</c:if>
		</div>
	</div>
</div>
<%@ include file="../footer.jsp"%>