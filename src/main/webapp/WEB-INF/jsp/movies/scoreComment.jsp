<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<c:if test="${not empty user and comment.user.id!=user.id and canScore}">
	<dt>¿Useful?</dt>
	<dd>
		<form:form role="form" action="app/comment/score" method="POST"
			commandName="commentScoreForm">
			<div class="input-group">
				<form:input type="number" min="1" max="5" class="form-control"
					name="commentScore" id="commentScore" path="commentScore" />
				<form:input type="hidden" path="userId" value="${comment.user.id}" />
				<form:input type="hidden" path="commentId" value="${comment.id}" />
				<input type="submit" name="submit" id="submit" value="Submit"
					class="btn btn-primary">
			</div>
		</form:form>
	</dd>
</c:if>