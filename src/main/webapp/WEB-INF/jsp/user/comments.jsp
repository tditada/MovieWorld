<%@ include file="../header.jsp"%>
<%@ include file="../nav/start.jsp"%>
<%@ include file="../nav/links.jsp"%>
<%@ include file="../nav/userMenu.jsp"%>
<%@ include file="../nav/end.jsp"%>
<div class="container">
	<div class="row">
		<div class="page-header">
			<h1>Profile</h1>
		</div>
		<div class="panel panel-default">
			<div class="panel panel-default">
				<div class="panel-heading">
					<h3 class="panel-title">

						<c:choose>
							<c:when test="${not empty commentsUser.comments}">
								<c:out
									value="Comments by ${commentsUser.firstName.nameString} ${commentsUser.lastName.nameString }" />
							</c:when>
							<c:otherwise>
								<c:out
									value="No Comments by ${commentsUser.firstName.nameString} ${commentsUser.lastName.nameString }" />
							</c:otherwise>
						</c:choose>
					</h3>
				</div>
				<c:forEach items="${commentsUser.comments}" var="comment">
					<div class="panel-body">
						<dl class="dl-horizontal">
							<dt>Movie</dt>
							<dd>
								<a
									href="<c:out	value="app/movies/detail?id=${comment.movie.id}" />">
									<c:out value="${comment.movie.title}" />
								</a>
							</dd>
							<dt>Date</dt>
							<dd>
								<td class="col-md-2"><joda:format
										value="${comment.creationDate}" style="M-" />
							</dd>
						</dl>
					</div>
				</c:forEach>
			</div>

				<%-- <div class="panel-heading">
				<h3 class="panel-title">Comments</h3>
			</div>
			<div class="panel panel-default">
				<table class="table table-striped">
					<thead>
						<tr>
							<th class="col-md-2">Comment's Title</th>
							<th class="col-md-2">Comment's Film</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${commentsUser.comments}" var="comment">
							<tr>
								<td class="col-md-2"><a
									href="<c:out	value="app/movies/detail?id=${comment.movie.id}" />">
										<c:out value="${comment.movie.title}" />
								</a></td>
								<td class="col-md-2"><joda:format value="${comment.creationDate}" style="M-" /></a></td>
							</tr>
						</c:forEach>
					</tbody>
				</table> --%>
				<%-- 			<c:forEach items="${commentsUser.comments}" var="comment">
					<div class="panel-heading">
						<a
							href="<c:out	value="app/movies/detail?id=${comment.movie.id}" />">
							<c:out value="${comment.movie.title}" />
						</a>
					</div>
					<div class="panel-body">
						<dl class="dl-horizontal">
							<dt>Score</dt>
							<dd>
								<c:forEach begin="1" end="${comment.score}">
									<span class="glyphicon glyphicon-star"></span>
								</c:forEach>
								<c:if test="${comment.score < 5}">
									<c:forEach begin="${comment.score}" end="4">
										<span class="glyphicon glyphicon-star-empty"></span>
									</c:forEach>
								</c:if>
							</dd>
							<dt>Date</dt>
							<dd>
								<joda:format value="${comment.creationDate}" style="M-" />
							</dd>
							<dt>Comment</dt>
							<dd>
								<c:out value="${comment.text}" />
							</dd>
						</dl>
					</div>
				</c:forEach> --%>
			</div>
		</div>
	</div>
</div>
<%@ include file="../footer.jsp"%>