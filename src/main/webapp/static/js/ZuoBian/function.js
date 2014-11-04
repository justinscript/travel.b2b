jQuery.Menu = function (a, b) {
	jQuery("#main-nav").find("a<ul:contains('" + a + "')").addClass("current").next("ul").show().find("a:contains('" + b + "')").addClass("current")
}