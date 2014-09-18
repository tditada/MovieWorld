<%@ include file="header.jsp"%>

<%@ include file="navbarStart.jsp"%>
<ul class="nav navbar-nav">
	<li><a href="<c:out value="movies/list"/>">All movies</a></li>
</ul>
<%@ include file="userMenu.jsp"%>
<%@ include file="navbarEnd.jsp"%>

<div class="container">
	<div class="row">
		<div class="col-md-4">
			<div class="panel panel-default">
				<div class="panel-heading text-center">
					<h3 class="panel-title">Top 5</h3>
				</div>
				<ul class="list-group">
					<li class="list-group-item">Cras justo odio <span
						class="glyphicon glyphicon-star"></span></li>
					<li class="list-group-item">Dapibus ac facilisis in</li>
					<li class="list-group-item">Morbi leo risus</li>
					<li class="list-group-item">Porta ac consectetur ac</li>
					<li class="list-group-item">Vestibulum at eros</li>
				</ul>
			</div>
			<div class="panel panel-default">
				<div class="panel-heading text-center">
					<h3 class="panel-title">New Additions</h3>
				</div>

				<table class="table">
					<thead>
						<tr>
							<th>#</th>
							<th>Added</th>
							<th>Title</th>
							<th># of comments</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td>1</td>
							<td>Mark</td>
							<td>Otto</td>
							<td>@mdo</td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
		<div class="col-md-8">
			<div class="panel panel-default">
				<div class="panel-heading text-center">
					<h2 class="panel-title">Releases</h2>
				</div>
				<!-- 				<ul> -->
				<!-- 					<li> -->
<!-- 				<div> -->
<!-- 					<div class="panel panel-default"> -->
<!-- 						<div class="panel-heading">Panel heading without title</div> -->
<!-- 						<div class="panel-body">Panel content</div> -->
<!-- 					</div> -->
<!-- 				</div> -->
				<!-- 					</li> -->
				<!-- 				</ul> -->
				<!-- 				<table class="table"> -->
				<!-- 					<thead> -->
				<!-- 						<tr> -->
				<!-- 							<th>#</th> -->
				<!-- 							<th>First Name</th> -->
				<!-- 							<th>Last Name</th> -->
				<!-- 							<th>Username</th> -->
				<!-- 						</tr> -->
				<!-- 					</thead> -->
				<!-- 					<tbody> -->
				<!-- 						<tr> -->
				<!-- 							<td>1</td> -->
				<!-- 							<td>Mark</td> -->
				<!-- 							<td>Otto</td> -->
				<!-- 							<td>@mdo</td> -->
				<!-- 						</tr> -->
				<!-- 						<tr> -->
				<!-- 							<td>2</td> -->
				<!-- 							<td>Jacob</td> -->
				<!-- 							<td>Thornton</td> -->
				<!-- 							<td>@fat</td> -->
				<!-- 						</tr> -->
				<!-- 						<tr> -->
				<!-- 							<td>3</td> -->
				<!-- 							<td>Larry</td> -->
				<!-- 							<td>the Bird</td> -->
				<!-- 							<td>@twitter</td> -->
				<!-- 						</tr> -->
				<!-- 					</tbody> -->
				<!-- 				</table> -->
			</div>
		</div>
	</div>
</div>

<%@ include file="footer.jsp"%>