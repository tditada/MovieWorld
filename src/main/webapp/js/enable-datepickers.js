$(document).ready(function() {
	$("#birthDatepicker").datepicker({
		changeMonth : true,
		changeYear : true,
		maxDate : 0,
		yearRange : "1900:2014",
		dateFormat : 'yy-mm-dd'
	});

	$("#releaseDatepicker").datepicker({
		changeMonth : true,
		changeYear : true,
		maxDate : 0,
		yearRange : "1900:2020",
		dateFormat : 'yy-mm-dd'
	});
});
