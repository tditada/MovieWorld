$(document).ready(function() {
    $("#datepicker").datepicker({
      changeMonth: true,
      changeYear: true,
      yearRange: "1900:2014",
	dateFormat: 'mm/dd/yy'
    });
});