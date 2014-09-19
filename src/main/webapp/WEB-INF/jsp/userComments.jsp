<%@ include file="header.jsp"%>
<%@ include file="headerend.jsp"%>
<%@ include file="navbarStart.jsp"%>
<ul class="nav navbar-nav">
	<li><a href="<c:out value="movies/list"/>">All movies</a></li>
</ul>
<%@ include file="userMenu.jsp"%>
<%@ include file="navbarEnd.jsp"%>
<div class="container">
	<div class="page-header">
		<h1>My Comments</h1>
	</div>
	<div class="col-md-8">
		<div class="panel panel-default">
			<c:forEach items="${comments}" var="comment">
			<div class="panel-heading">
					<a href="<c:out	value="movies/detail?id=${comment.movie.id}" />">
						<c:out value="${comment.movie.title}" />
					</a>
			</div>
			<div class="panel-body">
				<dl class="dl-horizontal">
				<dt>Score</dt>
					<dd><c:forEach begin="1" end="${comment.score}">
						<span class="glyphicon glyphicon-star"></span>
					</c:forEach> 
					<c:if test="${comment.score < 5}">
						<c:forEach begin="${comment.score}" end="4">
							<span class="glyphicon glyphicon-star-empty"></span>
						</c:forEach>
					</c:if></dd>
					<dt>Date</dt>
					<dd><joda:format value="${comment.creationDate}"style="M-" /></dd>
					<dt>Comment</dt>
					<dd><c:out value="${comment.text}"/></dd>
				</dl>
			</div>
			</c:forEach>
			</div>
		</div>
	</div>
</div>
<%@ include file="footer.jsp"%>