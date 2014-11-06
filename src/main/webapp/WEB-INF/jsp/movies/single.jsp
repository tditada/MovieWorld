<%@ include file="../header.jsp"%>
<%@ include file="../nav/start.jsp"%>
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
				<c:if test="${not empty movie.picture }">
					<dt>Picture</dt>
					<dd>
						<img alt="${movie.title}" id="actualPicture"
							class="profile_image img-circle"
							src="app/movies/getMoviePicture?movie=${movie.id}" />
					</dd>
				</c:if>
				<dt>Genres</dt>
				<dd class="text-capitalize">
					<c:forEach items="${movie.genres}" var="genre" varStatus="status">
						<c:if test="${status.index > 0}">,</c:if>
						<a href="<c:url value="app/movies/list?genre=${genre.name}"/>"><c:out
								value="${genre.name}" /></a>
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
					<joda:format value="${movie.releaseDate}" style="M-" />
				</dd>
				<dt>Runtime</dt>
				<dd>
					<c:out value="${movie.runtimeInMins}" />
					min
				</dd>
				<dt>Score</dt>
				<dd>
					<c:forEach begin="1" end="${movie.averageScore.value}">
						<span class="glyphicon glyphicon-star"></span>
					</c:forEach>
					<c:if test="${movie.averageScore.value < 5}">
						<c:forEach begin="${movie.averageScore.value}" end="4">
							<span class="glyphicon glyphicon-star-empty"></span>
						</c:forEach>
					</c:if>
					<small> <c:out value="(${movie.averageScore.value}/5)" />
						<c:out value=" for ${movie.totalComments} comments"></c:out></small>
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
	<%@include file="singleCommentPanel.jsp"%>
	<div class="panel panel-default">
		<c:if test="${not empty user }">
			<div class="panel-heading">
				<h4 class="panel-title">Write a Comment</h4>
			</div>
		</c:if>
		<c:choose>
			<c:when test="${not empty user && ableToComment}">
				<div class="panel-body">
					<form:form role="form" action="app/comments" method="POST"
						commandName="newCommentForm">
						<div class="form-group">
							<label for="movieScore" class="col-sm-2 control-label">Score</label>
							<div class="input-group">
								<form:input type="number" min="0" max="5" class="form-control"
									name="movieScore" id="movieScore" path="movieScore" />
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
			</c:when>
			<c:when test="${not empty user && not ableToComment}">
				<div class="panel-body">It seems you can't comment on this
					movie. Maybe you already commented here or this film hasn't been
					released yet?</div>
			</c:when>
		</c:choose>
	</div>
</div>

<%@ include file="../footer.jsp"%>