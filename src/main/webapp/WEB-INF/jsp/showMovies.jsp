<%@ include file="header.jsp"%>
<%@ include file="headerend.jsp"%>

<%@ include file="navbarStart.jsp"%>
<form class="navbar-form navbar-left" role="search" action="movies/list"
	method="get">
	<div class="form-group">
		<select class="selectpicker" title="Filter by genre"
			data-live-search="true" data-container="body" name="genre">
			<option disabled selected>Any genre</option>
			<c:forEach items="${genres}" var="genre">
				<option>
					<c:out value="${genre}" />
				</option>
			</c:forEach>
		</select>
		<button type="submit" class="btn btn-default">Apply filter</button>
	</div>
</form>
<%@ include file="userMenu.jsp"%>
<%@ include file="navbarEnd.jsp"%>

<div class="container">
	<div class="well">
		<table class="table table-striped">
			<thead>
				<tr>
					<th class="col-md-2">Release Date</th>
					<th class="col-md-2">Director</th>
					<th class="col-md-4">Title</th>
					<th class="col-md-1">Link</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${movies}" var="movie" varStatus="status">
					<%--<tr>TODO: queda mas lindo asi?	<td class="col-md-1">${status.index}</td> --%>
					<td class="col-md-2"><joda:format value="${movie.releaseDate}"
							style="M-" /></td>
					<td class="col-md-2"><c:out value="${movie.director.name}" /></td>
					<td class="col-md-4"><c:out value="${movie.title}" /></td>
					<td class="col-md-1"><a
						href="<c:out
								value="movies/detail?id=${movie.id}" />"><span
							class="glyphicon glyphicon-link"></span></a></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
</div>
<%@ include file="footer.jsp"%>