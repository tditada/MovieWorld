<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="joda" uri="http://www.joda.org/joda/time/tags"%>

<div class="panel panel-default">
	<div class="panel-heading">
		<h3 class="panel-title">
			<c:choose>
				<c:when test="${movie.totalComments > 0}">
					<c:out value="${movie.totalComments} Comments" />
				</c:when>
				<c:otherwise>No comments</c:otherwise>
			</c:choose>
		</h3>
	</div>
	<c:forEach items="${movie.comments}" var="comment">
		<div class="panel-body">
			<dl class="dl-horizontal">
				<dt>User</dt>
				<dd>
					<c:out
						value="${comment.user.firstName.nameString} ${comment.user.lastName.nameString}" />
				</dd>
				<dt>Score</dt>
				<dd>
					<c:forEach begin="1" end="${comment.movieScore.value}">
						<span class="glyphicon glyphicon-star"></span>
					</c:forEach>
					<c:if test="${comment.movieScore.value < 5}">
						<c:forEach begin="${comment.movieScore.value}" end="4">
							<span class="glyphicon glyphicon-star-empty"></span>
						</c:forEach>
					</c:if>
					<small><c:out value="(${comment.movieScore.value}/5)"></c:out></small>
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
						<c:forEach begin="1" end="${comment.averageCommentScore.value}">
							<span class="glyphicon glyphicon-star"></span>
						</c:forEach>
						<c:if test="${comment.averageCommentScore.value < 5}">
							<c:forEach begin="${comment.averageCommentScore.value}" end="4">
								<span class="glyphicon glyphicon-star-empty"></span>
							</c:forEach>
						</c:if>
						<small> <c:out
								value="(${comment.averageCommentScore.value}/5)" />
						</small>
					</p>
				</dd>
				<c:if test="${not empty user}">
					<c:if test="${user ne comment.user}">
						<c:forEach items="${scoreablesByUser}" var="scoreable">
							<c:if test="${comment eq scoreable}">
								<dt>Score this comment</dt>
								<dd>
									<div class="input-group">
										<form:form role="form" action="app/comments/score"
											method="post" commandName="scoreCommentForm">
											<form:input type="number" min="0" max="5"
												class="form-control" name="score" id="score" path="score" />
											<form:input type="hidden" path="comment"
												value="${comment.id}" />
											<input type="submit" name="submit" id="submit" value="Score"
												class="btn btn-primary">
										</form:form>
									</div>
								</dd>
							</c:if>
						</c:forEach>
						<dt>Report this comment</dt>
						<dd>
							<c:forEach items="${reportablesByUser}" var="reportable">
								<c:if test="${comment eq reportable}">
									<c:set var="reported" value="${true}" />
									<%-- href="<c:out value="app/comments/report?comment=${comment.id}"/>"> --%>

									<form role="form" action="app/comments/report" method="post">
										<input type="hidden" name="comment" id="comment"
											value="${comment.id}"></input> <input type="submit"
											name="report" id="report" value="Report"
											class="btn btn-primary" />
									</form>
								</c:if>
							</c:forEach>
							<c:if test="${empty reported}">
								<span class="label label-danger">Reported</span>
							</c:if>
						</dd>
					</c:if>
					<c:if test="${user.admin}">
						<dt>Delete this comment</dt>
						<dd>
							<form role="form" action="app/comments/remove" method="post">
								<input type="hidden" name="comment" id="comment"
									value="${comment.id}" /><input type="hidden" name="movie"
									id="movie" value="${movie.id}" /> <input type="submit"
									name="delete" id="delete" value="Delete"
									class="btn btn-primary">
							</form>
						</dd>
					</c:if>
				</c:if>
			</dl>
		</div>
	</c:forEach>
</div>
