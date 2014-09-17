<%@ page contentType="text/html"%>
<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="joda" uri="http://www.joda.org/joda/time/tags"%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>MovieWorld</title>
<base href="${pageContext.request.contextPath}/"/>
<!-- Bootstrap - Style -->
<link rel='stylesheet' href='lib/bootstrap/css/bootstrap.min.css' />
<link rel='stylesheet' href='lib/bootstrap/css/bootstrap-theme.min.css' />
<!-- JQuery -->
<script type='text/javascript' src='lib/jquery/js/jquery-1.11.1.min.js'></script>
<!-- Bootstrap - Logic -->
<script type='text/javascript' src='lib/bootstrap/js/bootstrap.min.js'></script>
<!-- Bootstrap Select -->
<link rel='stylesheet' href='lib/bootstrap-select/dist/css/bootstrap-select.min.css' />
<script type='text/javascript'
	src='lib/bootstrap-select/dist/js/bootstrap-select.min.js'></script>
<link rel='stylesheet' href='css/style.css' />
<!-- Enablers -->
<script type='text/javascript' src='js/enable-bootstrap-select.js'></script>
<link rel="stylesheet" href="//code.jquery.com/ui/1.11.1/themes/smoothness/jquery-ui.css">
<script src="//code.jquery.com/jquery-1.10.2.js"></script>
<script src="//code.jquery.com/ui/1.11.1/jquery-ui.js"></script>
<link rel="stylesheet" href="/resources/demos/style.css">
<script>
  $(function() {
    $( "#datepicker" ).datepicker({
      changeMonth: true,
      changeYear: true,
      yearRange: "1900:2014"
    });
  });
</script>  
</head>
<body>
