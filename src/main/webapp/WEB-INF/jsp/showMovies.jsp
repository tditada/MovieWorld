<%@ include file="header.jsp"%>

<%@ include file="navbarStart.jsp"%>

<!-- Genre chooser -->
<!-- <select style="display: none;" class="selectpicker" title="Tu carrera" -->
<!-- 	data-live-search="true" data-container="body" data-width="25%"> -->
<%-- 	<c:forEach items="${genres}" var="genre"> --%>
<!-- 		<option> -->
<%-- 			<c:out value="${genre}" /> --%>
<!-- 		</option> -->
<%-- 	</c:forEach> --%>
<!-- </select> -->


<form class="navbar-form navbar-left" role="search">
	<div class="form-group">
		<select class="selectpicker" title="Filter by genre"
			data-live-search="true" data-container="body">
			<option>All</option>
			<c:forEach items="${genres}" var="genre">
				<option>
					<c:out value="${genre}" />
				</option>
			</c:forEach>
		</select>
	</div>
	<span class="glyphicon glyphicon-filter navbar-button"></span>
</form>
<!-- Collect the nav links, forms, and other content for toggling -->
<!-- <ul class="nav navbar-nav"> -->
<!-- 	<li class="active"><a href="#">Link</a></li> -->
<!-- 	<li><a href="#">Link</a></li> -->
<!-- 	<li class="dropdown"><a href="#" class="dropdown-toggle" -->
<!-- 		data-toggle="dropdown">Dropdown <span class="caret"></span></a> -->
<!-- 		<ul class="dropdown-menu" role="menu"> -->
<!-- 			<li><a href="#">Action</a></li> -->
<!-- 			<li><a href="#">Another action</a></li> -->
<!-- 			<li><a href="#">Something else here</a></li> -->
<!-- 			<li class="divider"></li> -->
<!-- 			<li><a href="#">Separated link</a></li> -->
<!-- 			<li class="divider"></li> -->
<!-- 			<li><a href="#">One more separated link</a></li> -->
<!-- 		</ul></li> -->
<!-- </ul> -->

<!-- <form class="navbar-form navbar-left" role="search"> -->
<!-- 	<div class="form-group"> -->
<!-- 		<input class="form-control" placeholder="Search" type="text"> -->
<!-- 	</div> -->
<!-- 	<button type="submit" class="btn btn-default">Submit</button> -->
<!-- </form> -->

<!-- Genre chooser -->
<!-- <select style="display: none;" class="selectpicker" title="Tu carrera" -->
<!-- 	data-live-search="true" data-container="body" data-width="25%"> -->
<%-- 	<c:forEach items="${genres}" var="genre"> --%>
<!-- 		<option> -->
<%-- 			<c:out value="${genre}" /> --%>
<!-- 		</option> -->
<%-- 	</c:forEach> --%>
<!-- </select> -->

<%@ include file="navbarEnd.jsp"%>

<table class="table table-striped">
	<thead>
		<tr>
			<!-- <th>#</th> -->
			<th class="col-md-1">Release Date</th>
			<th class="col-md-1">Director</th>
			<th class="col-md-2">Title</th>
			<th class="col-md-5">(Link)</th>
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
				<td class="col-md-5">@mdo</td>
			</tr>
		</c:forEach>
	</tbody>
</table>
<%@ include file="footer.jsp"%>