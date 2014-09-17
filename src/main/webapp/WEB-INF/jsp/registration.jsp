<%@ include file="header.jsp" %>
<div class="container">
    <div class="row">
        <form role="form" action="registration" method="POST">
            <div class="col-lg-6">
                <div class="well well-sm"><strong><span class="glyphicon glyphicon-asterisk"></span>Required Field</strong></div>
                <div class="form-group">
                    <label for="FirstName">Enter Name</label>
                    <div class="input-group">
                        <input type="text" class="form-control" name="FirstName" id="FirstName" placeholder="Enter Name" required>
                        <span class="input-group-addon"><span class="glyphicon glyphicon-asterisk"></span></span>
                    </div>
                </div>
                <div class="form-group">
                    <label for="LastName">Enter Last Name</label>
                    <div class="input-group">
                        <input type="text" class="form-control" name="LastName" id="LastName" placeholder="Enter Last Name" required>
                        <span class="input-group-addon"><span class="glyphicon glyphicon-asterisk"></span></span>
                    </div>
                </div>
                <div class="form-group">
                    <label for="InputEmail">Enter Email</label>
                    <div class="input-group">
                        <input type="email" class="form-control" id="InputEmailFirst" name="InputEmail" placeholder="Enter Email" required>
                        <span class="input-group-addon"><span class="glyphicon glyphicon-asterisk"></span></span>
                    </div>
                </div>
                <div class="form-group">
                    <label for="InputPasswordFirst">Enter Password</label>
                    <div class="input-group">
                        <input type="text" class="form-control" id="InputPasswordFirst" name="InputPasswordFirst" placeholder="Enter Password" required>
                        <span class="input-group-addon"><span class="glyphicon glyphicon-asterisk"></span></span>
                    </div>
                </div>
                <div class="form-group">
                    <label for="InputPasswordSecond">Confirm Password</label>
                    <div class="input-group">
                        <input type="text" class="form-control" id="InputPasswordSecond" name="InputPasswordSecond" placeholder="Confirm Password" required>
                        <span class="input-group-addon"><span class="glyphicon glyphicon-asterisk"></span></span>
                    </div>
                </div>
                <div class="form-group">
	                <label for="datepicker">Enter Birthday</label>
	               	<div id="datepicker"></div>
	            </div>
                <input type="submit" name="submit" id="submit" value="Submit" class="btn btn-info pull-right">
            </div>
        </form>
    </div>
</div>
<%@ include file="footer.jsp" %>