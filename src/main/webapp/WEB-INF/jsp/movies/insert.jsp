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
						<label for="title">Enter title</label>
						<div class="input-group">
							<form:input type="text" path="title" class="form-control"
								name="title" id="title" placeholder="Enter Movie's Title" />
							<span class="input-group-addon"><span
								class="glyphicon glyphicon-asterisk"></span> Required</span>
						</div>
						<form:errors path="title">
							<div class="alert alert-danger" role="alert">Invalid movie
								name</div>
						</form:errors>
					</div>
					<div class="form-group">
						<label for="director">Enter director</label>
						<div class="input-group">
							<form:input type="text" path="director" class="form-control"
								name="director" id="director" placeholder="Enter Director" />
							<span class="input-group-addon"><span
								class="glyphicon glyphicon-asterisk"></span> Required</span>
						</div>
						<form:errors path="director">
							<div class="alert alert-danger" role="alert">Invalid
								director's name</div>
						</form:errors>
					</div>
					<div class="form-group">
						<label for="summary">Enter summary</label>
						<div class="input-group">
							<form:input type="text" path="summary" class="form-control"
								name="summary" id="summary" placeholder="Enter Summary" />
							<span class="input-group-addon"><span
								class="glyphicon glyphicon-asterisk"></span> Required</span>
						</div>
						<form:errors path="summary">
							<div class="alert alert-danger" role="alert">Invalid
								Summary</div>
						</form:errors>
					</div>
					<div class="form-group">
						<label for="genres">Enter genre(s)</label>
						<div class="input-group">
							<form:input type="text" path="genres" class="form-control"
								name="genres" id="genres" placeholder="Action, Comedy, ..." />
							<span class="input-group-addon"><span
								class="glyphicon glyphicon-asterisk"></span> Required</span>
						</div>
						<form:errors path="genres">
							<div class="alert alert-danger" role="alert">Invalid
								genre(s)</div>
						</form:errors>
					</div>
					<div class="form-group">
						<label for="runtimeInMins" class="control-label">Enter
							runtime (in minutes)</label>
						<form:input type="number" min="1" path="runtimeInMins"
							class="form-control" name="runtimeInMins" id="runtimeInMins" />
						<span class="input-group-addon"><span
							class="glyphicon glyphicon-asterisk"></span> Required</span>
					</div>
					<div class="form-group">
						<label for="releaseDate">Enter release date</label>
						<form:input type="text" name="releaseDate" path="releaseDate"
							id="releaseDatepicker" cssClass="releaseDatepicker" />
						<form:errors path="releaseDate">
							<div class="alert alert-danger" role="alert">Invalid
								release date</div>
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