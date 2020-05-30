
function removeFromCart(button) {
	let src = $(button).parent().find(".song-image").children().prop("src");
	let track_id = src.substring(src.indexOf("id")+3,src.indexOf("&"));

	let request = $.ajax({
		"type" : "GET",
		"url" : encodeSessionId("/heaplay/addToCart")+"?track_id="+track_id+"&remove=true",
		"beforeSend": () => {
					$(".loading-cart").show();
					$(".cart").hide();
				},
		"complete"  : () => {
					$(".loading-cart").hide();
					$(".cart").show();
		}, 
		"success": () => {
			let price = $(button).parent().find(".price").html();
			price = price.replace(/\,/,'.');
			if(price == "Gratuita")
				price = 0;
			else {
				let int = price.substring(0,1) * 100;
				int = Number.parseInt(int) + Number.parseInt(price.substring(2,4));
				price = int;
			}
			
			let cartDiv = $(".cart");
			$(button).parent().remove();
			let numberOfElements = $(cartDiv).children().length;

			if( numberOfElements > 3 ) {
				let newValue = $("#sum").html().substring(0,1) * 100;
				newValue = Number.parseInt(newValue) + Number.parseInt($("#sum").html().substring(2,4));
				newValue = newValue - price;
				$("#sum").html(Math.floor(newValue/100)+","+newValue%100+" €");
			} else {
				$(cartDiv).empty();
				$("<p>Il tuo carrello è al momento vuoto</p>").appendTo(cartDiv);
			}
		},
		"error": (status,error) => console.log("Errore:"+error+" StatusCode:"+status)
	});
	setTimeout(() =>abortRequest(request),10000);
}

function purchase() {
	let request = $.ajax({
		"type" : "GET",
		"url" : encodeSessionId("/heaplay/purchase"),
		"beforeSend": () => {
					$(".loading-cart").show();
					$(".cart").hide();
				},
		"complete"  : () => {
					$(".loading-cart").hide();
					$(".cart").show();
		}, 
		"success": () => {
			$(".cart").empty();
			$("<p>Acquisto completato con successo</p>").appendTo($(".cart"));
		},
		"error": (status,error) => console.log("Errore:"+error+" StatusCode:"+status)
	});
	setTimeout(() =>abortRequest(request),10000);
}