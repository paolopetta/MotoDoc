$(document).ready(() => {
	const hideSearchBar = () => {
		$("#search-box").fadeOut();
		$(".search-button").fadeOut();
	};
	
	const showSearchBar = () => {
		$("#search-box").delay(300).fadeIn();
		$(".search-button").delay(300).fadeIn();
	};
	
	let selectSelected = $("<div class='select-selected'></div>");
	let selectItems = $("<div class='select-items'></div>")
	let trueSelect = $(".search-select");
	
	selectSelected.appendTo(".custom-select");
	selectSelected.html(trueSelect.children().first().html());
	
	selectItems.appendTo(".custom-select");
	selectItems.hide();
	trueSelect.children().each((i, el) => {
		let div = $("<div></div>");
		div.appendTo(selectItems);
		div.html($(el).html());
		
		div.on("click", (e) => {
			selectItems.find(".list-selected").removeClass("list-selected");
			$(e.target).addClass("list-selected");
			selectSelected.html($(e.target).html());
			trueSelect.val(trueSelect.children(":nth-child(" + (i + 1) + ")").val());
			selectItems.hide("slide", () => {
				selectSelected.html(selectSelected.html()); 
				showSearchBar();
				});			
		});		
	});
	
	selectItems.children(":first-child").trigger("click");
	
	selectSelected.on("click", (e) => {
		if(selectItems.css("display") == "none") {
			hideSearchBar();
			selectItems.delay(300).show("slide", () => selectSelected.html(selectSelected.html()));
		}
		else {
			selectItems.hide("slide", () => selectSelected.html(selectSelected.html()));
			showSearchBar();
		}
	});
	
	$(document).click(() => selectItems.hide("slide", () => {
		selectSelected.html(selectSelected.html()); 
		showSearchBar();
		}));
	selectSelected.click((e) => e.stopPropagation());
	
});