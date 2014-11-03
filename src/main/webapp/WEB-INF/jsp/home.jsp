<%@ include file="header.jsp"%>

<%@ include file="nav/start.jsp"%>
<%@ include file="nav/links.jsp"%>
<%@ include file="nav/userMenu.jsp"%>
<%@ include file="nav/end.jsp"%>

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
								value="app/movies/detail?id=${topMovie.id}" />">
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
								value="app/movies/detail?id=${newAddition.id}" />">
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
								value="app/movies/detail?id=${release.id}" />">
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
			<c:if test="${not empty user}">
				<div class="panel panel-default">
					<div class="panel-heading text-center">
						<h2 class="panel-title">Interesting Users' Comments of the
							Last Week</h2>
					</div>
					<c:choose>
						<c:when test="${empty interestingComments}">
							<p class="text-center">
							No comments to show! </br>
							Maybe you want to add more users of your interest? </br>
							Go to <a
								href="<c:out
								value="app/users/list" />">
								All Users page </a> to look around
								</p>
						</c:when>
						<c:otherwise>
							<c:forEach items="${interestingComments}"
								var="interestingComment">
								<div class="panel panel-default">
									<div class="panel-heading">
										<a
											href="<c:out
								value="app/users/profile?id=${interestingComment.user.id}" />">
											<c:out
												value="${interestingComment.user.firstName.nameString}" />
											<c:out value="${interestingComment.user.lastName.nameString}" />
										</a> commented in <a
											href="<c:out
								value="app/movies/detail?id=${interestingComment.movie.id}" />">
											<c:out value="${interestingComment.movie.title}" />
										</a>
									</div>
									<div class="panel-body">
										<p>
											<c:choose>
												<c:when test="${fn:length(interestingComment.text) > 300}">
													<c:out
														value="${fn:substring(interestingComment.text, 0, 300)}..." />
												</c:when>
												<c:otherwise>
													<c:out value="${interestingComment.text}" />
												</c:otherwise>
											</c:choose>
										</p>
									</div>
								</div>
							</c:forEach>
						</c:otherwise>
					</c:choose>
				</div>
			</c:if>
		</div>
	</div>
</div>

<%@ include file="footer.jsp"%>