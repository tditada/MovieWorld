<%@ include file="../header.jsp"%>

<%@ include file="../nav/start.jsp"%>
<%@ include file="../nav/links.jsp" %>
<%@ include file="../nav/userMenu.jsp"%>
<%@ include file="../nav/end.jsp"%>

<div class="container">
	<div class="well">
		<table class="table table-striped">
			<thead>
				<tr>
					<th class="col-md-2">User</th>
					<th class="col-md-2">Comments</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${users}" var="user">
					<tr>
						<td class="col-md-2">
							<p>
								<c:out value="${user.firstName.nameString}" />
								<c:out value="${user.lastName.nameString}" />
							</p>
						</td>
						<td class="col-md-1"><a
							href="<c:out
								value="app/users/profile?id=${user.id}" />"><span
								class="glyphicon glyphicon-link"></span></a></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
</div>
<%@ include file="../footer.jsp"%>