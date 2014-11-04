<%@ include file="../header.jsp"%>

<%@ include file="../nav/start.jsp"%>
<ul class="nav navbar-nav">
	<li><a href="<c:out value="app/movies/list"/>">All movies</a></li>
</ul>
<%@ include file="../nav/userMenu.jsp"%>
<%@ include file="../nav/end.jsp"%>

<div class="container">
	<form>
		<input type="hidden" id="commentMovie" name="commentMovie"
			value="${movie.id}">
	</form>
	<div class="page-header">
		<h1>
			<c:out value="${movie.title}" />
			<c:if test="${movie.release}">
				<span class="label label-default">Release</span>
			</c:if>
		</h1>
	</div>
	<div class="panel panel-default">
		<div class="panel-heading">
			<h3 class="panel-title">Details</h3>
		</div>
		<div class="panel-body">
			<dl class="dl-horizontal">
				<dt>Genres</dt>
				<dd class="text-capitalize">
					<c:forEach items="${movie.genres}" var="genre" varStatus="status">
						<c:if test="${status.index > 0}">,</c:if>
						<a href="<c:url value="app/movies/list?genre=${genre}"/>"><c:out
								value="${genre.genreName}" /></a>
					</c:forEach>
				</dd>
				<dt>Director</dt>
				<dd>
					<c:url value="app/movies/list" var="directorUrl">
						<c:param name="director" value="${movie.director.name}" />
					</c:url>
					<a href="${directorUrl}"><c:out value="${movie.director.name}" /></a>
				</dd>
				<dt>Release date</dt>
				<dd>
					<c:out value="${movie.releaseDate}" />
				</dd>
				<dt>Runtime</dt>
				<dd>
					<c:out value="${movie.runtimeInMins}" />
					min
				</dd>
				<dt>Score</dt>
				<dd>
					<c:forEach begin="1" end="${movie.averageScore}">
						<span class="glyphicon glyphicon-star"></span>
					</c:forEach>
					<c:if test="${movie.averageScore < 5}">
						<c:forEach begin="${movie.averageScore}" end="4">
							<span class="glyphicon glyphicon-star-empty"></span>
						</c:forEach>
					</c:if>
					<small> <c:out value="(${movie.averageScore}/5)" /> <c:out
							value=" for ${movie.totalComments} comments"></c:out></small>
				</dd>
				<dt>Summary</dt>
				<dd>
					<p>
						<c:out value="${movie.summary}" />
					</p>
				</dd>
			</dl>

		</div>
	</div>
	<div class="panel panel-default">
		<div class="panel-heading">
			<h3 class="panel-title">
				<c:choose>
					<c:when test="${movie.totalComments > 0}">
						<c:out value="${movie.totalComments} Comments" />
					</c:when>
					<c:otherwise>No Comments</c:otherwise>
				</c:choose>
			</h3>
		</div>
		<c:forEach items="${movie.comments}" var="comment">
			<div class="panel-body">
				<dl class="dl-horizontal">
					<dt>User</dt>
					<dd>
						<a
							href="<c:out
								value="app/users/user/comments?id=${comment.user.id}" />">
							<c:out
								value="${comment.user.firstName.nameString} ${comment.user.lastName.nameString}" />
						</a>
					</dd>
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
						<small><c:out value="(${comment.score}/5)"></c:out></small>
					</dd>
					<dt>Date</dt>
					<dd>
						<joda:format value="${comment.creationDate}" style="M-" />
					</dd>
					<dt>Comment</dt>
					<dd>
						<c:out value="${comment.text}" />
					</dd>
					<dt>Comment score</dt>
					<dd>
						<p>
							<c:forEach begin="1" end="${comment.averageScore}">
								<span class="glyphicon glyphicon-star"></span>
							</c:forEach>
							<c:if test="${comment.averageScore < 5}">
								<c:forEach begin="${comment.averageScore}" end="4">
									<span class="glyphicon glyphicon-star-empty"></span>
								</c:forEach>
							</c:if>
							(
							<c:out value="${comment.averageScore}" />
							)
						</p>
					</dd>
					<c:if test="${not empty user and comment.user.id!=user.id}">
						<dt>¿Useful?</dt>
						<dd>
							<form:form role="form" action="app/comment/score" method="POST"
								commandName="commentScoreForm">
								<div class="input-group">
									<form:input type="number" min="1" max="5" class="form-control"
										name="commentScore" id="commentScore" path="commentScore" />
									<form:input type="hidden" path="userId"
										value="${comment.user.id}" />
									<form:input type="hidden" path="commentId"
										value="${comment.id}" />
									<input type="submit" name="submit" id="submit" value="Submit"
										class="btn btn-primary">
								</div>
							</form:form>
						</dd>
					</c:if>
					<dd>
						<c:if test="${not empty user and user.admin}">
							<dt></dt>
							<dd>
								<form:form role="form" action="app/comment/remove" method="POST"
									commandName="deleteForm">
									<form:input type="hidden" name="commentId" id="commentId"
										value="${comment.id}" path="commentId"></form:input>
									<form:input type="hidden" path="userId" name="userId"
										id="userId" value="${comment.user.id}"></form:input>
									<input type="submit" name="delete" id="delete" value="delete"
										class="btn btn-primary" />
								</form:form>
							</dd>
						</c:if>
					</dd>
				</dl>
			</div>
		</c:forEach>
	</div>
	<c:if test="${not empty user && ableToComment}">
		<div class="panel panel-default">
			<div class="panel-heading">
				<h4 class="panel-title">Write a Comment</h4>
			</div>
			<div class="panel-body">
				<form:form role="form" action="app/comment" method="POST"
					commandName="commentForm">
					<div class="form-group">
						<label for="filmScore" class="col-sm-2 control-label">Score</label>
						<div class="input-group">
							<form:input type="number" min="1" max="5" class="form-control"
								name="filmScore" id="filmScore" path="filmScore" />
						</div>
					</div>
					<div class="form-group">
						<label for="commentText" class="col-sm-2 control-label">Comment</label>
						<div class="input-group">
							<form:textarea path="commentText" rows="5" cols="50" />
							<%-- <form:input type="text" path="commentText" id="commentText"/> --%>
						</div>
					</div>
					<div class="form-group">
						<div class="col-sm-offset-2 col-sm-10">
							<input type="submit" name="submit" id="submit" value="Submit"
								class="btn btn-primary">
						</div>
					</div>
				</form:form>
			</div>
		</div>
	</c:if>
</div>

<%@ include file="../footer.jsp"%>