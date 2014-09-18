<%@ include file="header.jsp" %>
<%@ include file="headerend.jsp"%>
<div class="container">
    <div class="row">
        <form role="form" action="registration" method="POST">
            <div class="col-lg-6">
                <div class="well well-sm"><strong><span class="glyphicon glyphicon-asterisk"></span>Required Field</strong></div>
                <div class="form-group">
                    <label for="firstname">Enter Name</label>
                    <div class="input-group">
                        <input type="text" class="form-control" name="firstname" id="firstname" placeholder="Enter Name" required>
                        <span class="input-group-addon"><span class="glyphicon glyphicon-asterisk"></span></span>
                    </div>
                </div>
                <div class="form-group">
                    <label for="lastname">Enter Last Name</label>
                    <div class="input-group">
                        <input type="text" class="form-control" name="lastname" id="lastname" placeholder="Enter Last Name" required>
                        <span class="input-group-addon"><span class="glyphicon glyphicon-asterisk"></span></span>
                    </div>
                </div>
                <div class="form-group">
                    <label for="email">Enter Email</label>
                    <div class="input-group">
                        <input type="email" class="form-control" id="email" name="email" placeholder="Enter Email" required>
                        <span class="input-group-addon"><span class="glyphicon glyphicon-asterisk"></span></span>
                    </div>
                </div>
                <div class="form-group">
                    <label for="password">Enter Password</label>
                    <div class="input-group">
                        <input type="text" class="form-control" id="password" name="password" placeholder="Enter Password" required>
                        <span class="input-group-addon"><span class="glyphicon glyphicon-asterisk"></span></span>
                    </div>
                </div>
                <div class="form-group">
                    <label for="secondPassword">Confirm Password</label>
                    <div class="input-group">
                        <input type="text" class="form-control" id="secondPassword" name="secondPassword" placeholder="Confirm Password" required>
                        <span class="input-group-addon"><span class="glyphicon glyphicon-asterisk"></span></span>
                    </div>
                </div>
                <div class="form-group">
	                <label for="birthday">Enter Birthday</label>
	                <input type="text" name="birthday" id="datepicker">
	            </div>
                <input type="submit" name="submit" id="submit" value="Submit" class="btn btn-info pull-right">
            </div>
        </form>
    </div>
</div>
<%@ include file="footer.jsp" %>