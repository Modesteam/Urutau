$(document).ready(function() {
	$(".close").click(function() {
		$(this).closest("div").slideUp("slow");
	});
});