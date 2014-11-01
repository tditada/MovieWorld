$(document).ready(function() {
	$("#datepicker").datepicker({
		changeMonth : true,
		changeYear : true,
		maxDate : 0,
		yearRange : "1900:2014",
		dateFormat : 'yy-mm-dd'
	});
	$("#filmDatepicker").datepicker({
		changeMonth : true,
		changeYear : true,
		yearRange : "1900:2020",
		dateFormat : 'yy-mm-dd'
	});
});
