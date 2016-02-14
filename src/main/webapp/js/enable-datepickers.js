$(document).ready(function() {
	$(".birthDatepicker").datepicker({
		changeMonth : true,
		changeYear : true,
		maxDate : 0,
		yearRange : "1900:2014",
		dateFormat : 'dd-mm-yy'
	});

	$(".releaseDatepicker").datepicker({
		changeMonth : true,
		changeYear : true,
		dateFormat : 'dd-mm-yy'
	});
});
