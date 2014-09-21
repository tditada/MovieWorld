$(document).ready(function() {
	$(".logout").click(function() {
		$.ajax({
			type : "POST",
			url : "logout",
			dataType:'html',
			success : function(data, textStatus, jqxhr) {
				var newDoc = document.open("text/html", "replace");
				newDoc.write(data);
				newDoc.close();
			}
		});
	});
});