<%@ include file="header.jsp"%>
<%@ include file="headerend.jsp"%>

<%@ include file="navbarStart.jsp"%>
<%@ include file="userMenu.jsp"%>
<%@ include file="navbarEnd.jsp"%>

<div class="container">
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
				<dd>
					<c:forEach items="${movie.genres}" var="genre" varStatus="status">
						<c:if test="${status.index > 0}">,</c:if>
						<a href="<c:url value="movies/list?genre=${genre}"/>"><c:out
								value="${genre}" /></a>
					</c:forEach>
				</dd>
				<dt>Director</dt>
				<dd>
					<a
						href="<c:url value="movies/list?director=${movie.director.name}"/>"><c:out
							value="${movie.director.name}" /></a>
					<%-- 	<c:url value="/yourClient" var="url"> --%>
					<%-- 		<c:param name="yourParamName" value="http://google.com/index.html" /> --%>
					<%-- 	</c:url> --%>
					<%--	<a href="${url}">Link to your client</a> --%>
				</dd>
				<dt>Runtime</dt>
				<dd>
					<c:out value="${movie.runtimeInMins}" />
					min
				</dd>
				<dt>Score</dt>
				<dd>
					<c:choose>
						<c:when test="${movie.totalComments == 0}">
							<c:forEach begin="1" end="5">
								<span class="glyphicon glyphicon-star-empty"></span>
							</c:forEach>
						</c:when>
						<c:otherwise>
							<c:forEach begin="1" end="${movie.averageScore}">
								<span class="glyphicon glyphicon-star"></span>
							</c:forEach>
							<c:if test="${movie.averageScore} < 5">
								<c:forEach begin="${movie.averageScore}" end="5">
									<span class="glyphicon glyphicon-star-empty"></span>
								</c:forEach>
							</c:if>
						</c:otherwise>
					</c:choose>
					<small> <c:choose>
							<c:when test="${movie.totalComments == 0}">
								<c:out value="(0/0)" />
							</c:when>
							<c:otherwise>
								<c:out value="(${movie.averageScore}/5)" />
							</c:otherwise>
						</c:choose> <c:out value=" for ${movie.totalComments} comments"></c:out></small>
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
</div>

<%@ include file="footer.jsp"%>