<%@ include file="header.jsp"%>
<%@ include file="nav/start.jsp"%>
<%@ include file="nav/end.jsp"%>
<div class="container">
	<div class="row">
		<div class="span12">
			<div class="hero-unit center">
				<h1>
					Error<small><font face="Tahoma" color="red"></font></small>
				</h1>
				<br />
				<p>
					<c:out value="${error}" />
				</p>
				<!-- TODO: check! -->
				<a href="${header.referer}" class="btn btn-large btn-info"><i
					class="glyphicon glyphicon-arrow-left"></i>Go Back</a> <a
					href="app/home" class="btn btn-large btn-info"><i
					class="glyphicon glyphicon-home"></i> Take Me Home</a>
			</div>
		</div>
	</div>
</div>
<%@ include file="footer.jsp"%>