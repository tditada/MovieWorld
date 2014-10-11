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
		<c:forEach items="${comments}" var="comment">
			<div class="panel-body">
				<dl class="dl-horizontal">
					<dt>User</dt>
					<dd>
						<c:out value="${comment.user.firstName} ${comment.user.lastName}" />
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
				<form role="form" action="comment" method="POST">
					<div class="form-group">
						<label for="commentScore" class="col-sm-2 control-label">Score</label>
						<div class="input-group">
							<input type="number" min="1" max="5" class="form-control"
								name="commentScore" id="commentScore" required>
						</div>
					</div>
					<div class="form-group">
						<label for="commentText" class="col-sm-2 control-label">Comment</label>
						<div class="input-group">
							<textarea name="commentText" id="commentText"
								class="form-control" placeholder="Comment" rows=5
								style="width: 399px; height: 120px;"></textarea>
						</div>
					</div>
					<div class="form-group">
						<div class="col-sm-offset-2 col-sm-10">
							<input type="submit" name="submit" id="submit" value="Submit"
								class="btn btn-primary">
						</div>
					</div>
				</form>
			</div>
		</div>
	</c:if>
</div>

<%@ include file="../footer.jsp"%>