<%@ include file="../header.jsp"%>

<%@ include file="../nav/start.jsp"%>
<form class="navbar-form navbar-left" role="search"
	action="app/movies/list" method="get">
	<div class="form-group">
		<select class="selectpicker" title="Filter by genre"
			data-live-search="true" data-container="body" name="genre">
			<option disabled selected>Any genre</option>
			<c:forEach items="${genres}" var="genre">
				<option value="${genre}">
					<c:out value="${genre.genreName}" />
				</option>
			</c:forEach>
		</select>
		<%-- <select class="selectpicker" title="Filter by director"
			data-live-search="true" data-container="body" name="director">
			<option disabled selected>Any director</option>
			<c:forEach items="${directors}" var="director">
				<option value="${director.name}">
					<c:out value="${director.name}" />
				</option>
			</c:forEach>
		</select> --%>
		<button type="submit" class="btn btn-default">Apply filter</button>
	</div>
</form>
<%@ include file="../nav/userMenu.jsp"%>
<%@ include file="../nav/end.jsp"%>

<div class="container">
	<div class="well">
		<table class="table table-striped">
			<thead>
				<tr>
					<th class="col-md-2">Release Date</th>
					<th class="col-md-2">Director</th>
					<th class="col-md-4">Title</th>
					<th class="col-md-1">Detail</th>
					<c:if test="${user.isAdmin}">
						<th class="col-md-1">Edit</th>
						<th class="col-md-1"></th>
					</c:if>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${movies}" var="movie" varStatus="status">
					<tr>
						<td class="col-md-2"><joda:format
								value="${movie.releaseDate}" style="M-" /></td>
						<td class="col-md-2"><c:out value="${movie.director.name}" /></td>
						<td class="col-md-4"><c:out value="${movie.title}" /></td>
						<td class="col-md-1"><a
							href="<c:out
								value="app/movies/detail?id=${movie.id}" />"><span
								class="glyphicon glyphicon-link"></span></a></td>
						<c:if test="${user.isAdmin}">
						<td class="col-md-1"><a
							href="<c:out
								value="app/movies/edit?id=${movie.id}" />"><span
								class="glyphicon glyphicon-pencil text-center"></span></a></td>
						<td class="col-md-1"><form:form role="form"
								action="app/movies/remove" method="post" commandName="remove">
								<input type="hidden" name="id" id="id" value="${movie.id}"></input>
								<input type="submit" name="delete" id="delete" value="delete"
									class="btn btn-primary pull-right">
							</form:form></td>
						</c:if>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
</div>
<%@ include file="../footer.jsp"%>