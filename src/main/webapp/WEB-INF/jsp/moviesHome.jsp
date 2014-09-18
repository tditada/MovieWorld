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
				<table class="table">
					<thead />
					<tbody>
						<c:forEach items="${topMovies}" var="topMovie">
							<tr>
								<td><a
									href="<c:out
								value="movies/detail?id=${topMovie.id}" />">
										<c:out value="${topMovie.title}" />
								</a></td>
								<td><c:forEach begin="1" end="${topMovie.averageScore}">
										<span class="glyphicon glyphicon-star"></span>
									</c:forEach> <c:if test="${topMovie.averageScore < 5}">
										<c:forEach begin="${topMovie.averageScore}" end="4">
											<span class="glyphicon glyphicon-star-empty"></span>
										</c:forEach>
									</c:if><small> <c:out value="(${topMovie.averageScore}/5)" />
								</small></td>
							</tr>
						</c:forEach>
						<!-- 				<ul class="list-group"> -->
						<%-- 					<c:forEach items="${topMovies}" var="topMovie"> --%>
						<!-- 						<li class="list-group-item"><a -->
						<%-- 							href="<c:out --%>
						<%-- 								value="movies/detail?id=${topMovie.id}" />"> --%>
						<%-- 								<c:out value="${topMovie.title}" /> --%>
						<%-- 						</a> <c:forEach begin="1" end="${topMovie.averageScore}"> --%>
						<!-- 								<span class="glyphicon glyphicon-star"></span> -->
						<%-- 							</c:forEach> <c:if test="${topMovie.averageScore < 5}"> --%>
						<%-- 								<c:forEach begin="${topMovie.averageScore}" end="4"> --%>
						<!-- 									<span class="glyphicon glyphicon-star-empty"></span> -->
						<%-- 								</c:forEach> --%>
						<%-- 							</c:if> <small> <c:out value="(${topMovie.averageScore}/5)" /> --%>
						<!-- 						</small></li> -->
						<%-- 					</c:forEach> --%>
						<!-- 				</ul> -->
					</tbody>
				</table>
			</div>
			<div class="panel panel-default">
				<div class="panel-heading text-center">
					<h3 class="panel-title">New Additions</h3>
				</div>
				<table class="table">
					<thead>
						<tr>
							<th class="text-center">Added</th>
							<th class="text-center">Title</th>
							<th class="text-center">Comments</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${newAdditions}" var="newAddition">
							<tr>
								<td><joda:format value="${newAddition.creationDate}"
										style="M-" /></td>
								<td class="text-center"><a
									href="<c:out
								value="movies/detail?id=${newAddition.id}" />">
										<c:out value="${newAddition.title}" />
								</a></td>
								<td class="text-center"><c:out
										value="${newAddition.totalComments}" /></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</div>
		<div class="col-md-8">
			<div class="panel panel-default">
				<div class="panel-heading text-center">
					<h2 class="panel-title">Releases</h2>
				</div>
				<c:forEach items="${releases}" var="release">
					<div class="panel panel-default">
						<div class="panel-heading">
							<a
								href="<c:out
								value="movies/detail?id=${release.id}" />">
								<c:out value="${release.title}" />
							</a>
						</div>
						<div class="panel-body">
							<p>
								<c:choose>
									<c:when test="${fn:length(release.summary) > 300}">
										<c:out value="${fn:substring(release.summary, 0, 300)}..." />
									</c:when>
									<c:otherwise>
										<c:out value="${release.summary}" />
									</c:otherwise>
								</c:choose>
							</p>
						</div>
					</div>
				</c:forEach>
			</div>
		</div>
	</div>
</div>

<%@ include file="footer.jsp"%>