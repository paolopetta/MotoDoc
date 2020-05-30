var prevScrollpos = window.pageYOffset;

const cartDrop = (flag) => {
	if(flag == true) {
		let div = $(".fa-shopping-cart");
		
		if(div.length) {
			//Rimozione precedente
			$(div.parent()).remove();
			//Creazione del nuovo
			let parent = $(".dropdown-content");
			$($(parent).children()[$(parent).children().length-1]).before("<a href='"+encodeSessionId("/heaplay/cart")+"'>Carrello</a>");
		}
	} else {
		let div = $(".dropdown-content").children();
		
		if(div.length > 3) {
			//Rimozione precedente
			$(div[2]).remove();
			//Creazione nuovo
			$("<a href='"+encodeSessionId("/heaplay/cart")+"'><i class='fa fa-shopping-cart'></i></a>").appendTo($("nav.user"));
		}
	}
};




$(document).ready(() => {

	let dropdown = $(".dropdown");
	let dropbtn = $(".dropbtn");
	let dropContent = $(".dropdown-content");
	
	const setPadding = () => {
		let header = $("header");
		let headerHeight = header.height();

		$(".content-wrapper").css("padding-top", headerHeight + 10);
	};
	
	const drop = () => {
		if(dropContent.css("display") == "none") 
			dropContent.css("display", "flex");
		else 
			dropContent.hide(400)
	};
	
	setPadding();
	
	
	let dropContentHide = () => dropContent.hide(400);
	let mqListTablet = window.matchMedia("(max-width: 1024px)");
	if(mqListTablet.matches) {
		dropdown.on("click", drop);
		$(document).on("click", dropContentHide);
		dropdown.click((e) => e.stopPropagation());
	}
	
	mqListTablet.addListener((e) => {
		if(e.matches) {
			dropdown.on("click", drop);
			$(document).on("click", dropContentHide);
			dropdown.click((e) => e.stopPropagation());
		} else {
			dropdown.off("click");
			$(document).off("click", dropContentHide);
			dropContent.css("display", "");
		}
	});
	
	let mqListMobile = window.matchMedia("(max-width: 426px)");
	if(mqListMobile.matches)
		cartDrop(true);
	
	mqListMobile.addListener((e) => {
		if(e.matches) {
			cartDrop(true);
		} else {
			cartDrop(false);
		}
	});
	
	let mqListSmallTablet = window.matchMedia("(max-width: 560px)");
	if(mqListSmallTablet.matches) {
		dropbtn.find("a i").removeClass("fa fa-caret-down");
	}
	
	mqListSmallTablet.addListener((e) => {
		if(e.matches) {
			dropbtn.find("a i").removeClass("fa fa-caret-down");
		} else {
			dropbtn.find("a i").addClass("fa fa-caret-down");
		}
	});
	
	window.onscroll = () => {
		let header = $("header");
		let headerHeight = header.height();
		let currentScrollPos = window.pageYOffset;
			
		if (prevScrollpos > currentScrollPos) {
			header.css("top", "0")
		} else if(currentScrollPos > headerHeight) {
			if(dropContent.css("display") == "flex")
				dropdown.trigger("click");
			header.css("top", -(headerHeight-5));
		}
		prevScrollpos = currentScrollPos;
	};
	
	window.onresize = setPadding;

});