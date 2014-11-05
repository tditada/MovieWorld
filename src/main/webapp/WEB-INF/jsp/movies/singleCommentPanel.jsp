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
				<c:otherwise>No Comments</c:otherwise>
			</c:choose>
		</h3>
	</div>
	<c:forEach items="${movie.comments}" var="comment">
		<c:set var="canScore" value="${true}" />
		<c:forEach items="${comment.usersThatScore}" var="userThatScore">
			<c:if
				test="${userThatScore.email.textAddress eq user.email.textAddress}">
				<c:set var="canScore" value="${false}" />
			</c:if>
		</c:forEach>
		<div class="panel-body">
			<dl class="dl-horizontal">
				<dt>User</dt>
				<dd>
					<c:out
						value="${comment.user.firstName.nameString} ${comment.user.lastName.nameString}" />
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
				<%@ include file="scoreComment.jsp"%>
				<dd>
					<c:if test="${not empty user and user.admin}">
						<dt></dt>
						<dd>
							<form:form role="form" action="app/comment/remove" method="POST"
								commandName="delete">
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
