<%@ include file="../header.jsp"%>
<%@ include file="../nav/start.jsp"%>
<%@ include file="../nav/userMenu.jsp"%>
<%@ include file="../nav/end.jsp"%>

<div class="container">
	<div class="well">
		<table class="table table-striped">
			<thead>
				<tr>
					<th class="col-md-2">User</th>
					<th class="col-md-2">Movie</th>
					<th class="col-md-4">Text</th>
					<th class="col-md-2">Date</th>
					<th class="col-md-2"></th>
					<th class="col-md-2"></th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${reportedComments}" var="reportedComment">
					<tr>
						<td class="col-md-2"><a
							href="<c:out value="app/users/profile?user=${reportedComment.user.id}"/>"><c:out
									value="${reportedComment.user.firstName.nameString} ${reportedComment.user.lastName.nameString}" /></a></td>
						<td class="col-md-2"><a
							href="<c:out value="app/movies/detail?movie=${reportedComment.movie.id}"/>"><c:out
									value="${reportedComment.movie.title}" /></a></td>
						<td class="col-md-4"><c:out value="${reportedComment.text}" /></td>
						<td class="col-md-2"><joda:format
								value="${reportedComment.creationDate}" style="M-" /></td>
						<td class="col-md-1">
							<form role="form" action="app/comments/remove" method="post">
								<input type="hidden" name="comment" id="comment" value="${reportedComment.id}" /> <input
									type="submit" name="delete" id="delete" value="Delete"
									class="btn btn-primary pull-right">
							</form>
						</td>
						<td class="col-md-1">
							<form role="form" action="app/comments/dropReports" method="post">
								<input type="hidden" name="comment" id="comment"
									value="${reportedComment.id}" /> <input type="submit"
									name="drop" id="drop" value="Drop reports"
									class="btn btn-primary pull-right">
							</form>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
</div>
<%@ include file="../footer.jsp"%>