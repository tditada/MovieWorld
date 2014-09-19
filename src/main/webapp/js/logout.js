$(document).ready(function() {
	$(".logout").click(function() {
		$.ajax({
			type : "POST",
			url : "logout"
		});
	});
});