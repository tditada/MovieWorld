<%@ include file="../header.jsp"%>
<%@ include file="../nav/start.jsp"%>
<%@ include file="../nav/links.jsp"%>
<%@ include file="../nav/userMenu.jsp"%>
<%@ include file="../nav/end.jsp"%>
<div class="container">
	<div class="row">
		<div class="page-header">
			<h1>
				<c:out
					value="${commentsUser.firstName.nameString} ${commentsUser.lastName.nameString } Profile" />
				<form:form role="form" action="app/users/addInteresting"
					method="post" commandName="addInterestingForm">
					<form:input type="hidden" path="interestingUser"
						name="interestingUser" id="interestingUser"
						value="${commentsUser.id}"></form:input>
					<form:input type="hidden" path="interestedUser"
						name="interestedUser" id="interestedUser"
						value="${user.id}"></form:input>
					<input type="submit" name="addInteresting" id="addInteresting"
						value="Add As Interesting User" class="btn btn-primary pull-right">
				</form:form>
			</h1>
		</div>
		<div class="panel panel-default">
			<div class="panel panel-default">
				<div class="panel-heading">
					<h3 class="panel-title">
						<c:choose>
							<c:when test="${not empty commentsUser.comments}">
								<c:out value="Comments" />
							</c:when>
							<c:otherwise>
								<c:out value="No Comments" />
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
								<joda:format value="${comment.creationDate}" style="M-" />
							</dd>
						</dl>
					</div>
				</c:forEach>
			</div>
			<div class="panel-body">
				<dl class="dl-horizontal">

				</dl>
			</div>
		</div>
	</div>
</div>
</div>
<%@ include file="../footer.jsp"%>