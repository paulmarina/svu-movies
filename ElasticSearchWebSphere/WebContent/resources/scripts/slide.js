$(document).ready(function() {

	$('body').waitForImages(function() {

		// Activate Carousel
		$("#myCarousel").carousel();

		// Enable Carousel Controls
		$(".left").click(function() {
			$("#myCarousel").carousel("prev");
		});
		$(".right").click(function() {
			$("#myCarousel").carousel("next");
		});

	});
});