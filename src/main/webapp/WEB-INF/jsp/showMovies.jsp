<%@ include file="header.jsp"%>

<%@ include file="navbarStart.jsp"%>

<!-- <p class="navbar-text">Filter by genre</p> -->
<form class="navbar-form navbar-left" role="search">
	<div class="form-group">
		<select class="selectpicker" title="Filter by genre"
			data-live-search="true" data-container="body">
			<option>Any genre</option>
			<c:forEach items="${genres}" var="genre">
				<option>
					<c:out value="${genre}" />
				</option>
			</c:forEach>
		</select>
		<button type="submit" class="btn btn-default">Apply filter</button>
	</div>
</form>

<%@ include file="navbarEnd.jsp"%>

<table class="table table-striped">
	<thead>
		<tr>
			<!-- <th>#</th> -->
			<th class="col-md-1">Release Date</th>
			<th class="col-md-1">Director</th>
			<th class="col-md-2">Title</th>
			<th class="col-md-5">Link</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach items="${movies}" var="movie">
			<tr>
				<!-- <td>1</td> -->
				<td class="col-md-1"><c:out
						value="${movie.releaseDate.dayOfMonth}" />/<c:out
						value="${movie.releaseDate.monthOfYear}" />/<c:out
						value="${movie.releaseDate.year}" /></td>
				<td class="col-md-1"><c:out value="${movie.director.name}" /></td>
				<td class="col-md-2"><c:out value="${movie.title}" /></td>
				<td class="col-md-5"><span class="glyphicon glyphicon-link"></span></td>
			</tr>
		</c:forEach>
	</tbody>
</table>
<%@ include file="footer.jsp"%>