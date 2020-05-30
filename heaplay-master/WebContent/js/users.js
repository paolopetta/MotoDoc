let prevImage;
//Setta il valore per la prossima chiamata
function beginValue(button) {
	let value = $(button).html()*9 - 9;
	$(button).parent().find($("input[type=hidden]")).prop("value",value);
}
//Mette il focus alla pagina corrente
function focusButton() {
	let val = $("#currentPage").val();
	$("#"+val).addClass("selected");
	$("#"+val).prop("type","button");
}

function selection(selected,prev,toHide,toShow){
	$(selected).addClass("selected");
	$(prev).removeClass("selected");
	$(toShow).removeClass("hidden");
	$(toHide).addClass("hidden");
}

//Upload image for user
function inputFile(input) {
	$(input).trigger("click");
}

//Show and hide confirm button
function hideConfirmButton(el) {
	if(!$(el).hasClass("hidden"))
		$(el).addClass("hidden");	
}

function showConfirmButton(el) {
	if($("#image")[0].files.length > 0)
		$(el).removeClass("hidden");
}

$(document).ready(() => {
	//To show the image preview
	$("#image").change(function(e){
		let file = e.currentTarget.files["0"];
		if(file != undefined) {
			let objectUrl = window.URL.createObjectURL(file);
			if(prevImage == undefined)
				prevImage = $(".page-image").prop("src");
			$(".page-image").prop("src", objectUrl);
			showConfirmButton($("#srcImg"));
		} else {
			hideConfirmButton($("#srcImg"));
			if(prevImage != undefined)
				$(".page-image").prop("src", prevImage);
		}
	});		
});

function checkImage(form) {
	let input = form["image"];
	return (input.files[0] != null && input.files[0] != undefined && input.files[0] != "");
}

//Cart
function addToCart(button) {
	let src = $(".audio").children().prop("src");
	let track_id = src.substring(src.indexOf("id")+3,src.indexOf("&"));
	$.ajax({
		"type":"GET",
		"url": encodeSessionId("/heaplay/addToCart")+"?track_id="+track_id,
		"success": () => {
			$(button).off();
			let div = $(button).parent();
			$(div).empty();
			$("<span>Aggiunto al carrello</span>").appendTo(div);
		}
	});
}

//Playlist
function getPlaylist(container) {
	let src = $('.page-image').prop("src");
	let user_id = src.substring(src.indexOf("id")+3,src.indexOf("&"));
	let request = $.ajax({
		"type" : "GET",
		"url" : encodeSessionId("/heaplay/getPlaylists"),
		"data": "id="+user_id,
		"success" : (data) => {
			$(container).empty();
			for(let i = 0 ; i < data.length; i++){
				//Estrazione del bean
				let bean = data[i];
				createDiv(bean,container,"playlist");
			}
			
			if(data.length == 0)
				$("<p>Non sono presenti playlist</p>").appendTo(container);
		},
		"error": (status,error) => console.log("Errore:"+error+" StatusCode:"+status)
	});
	setTimeout(() =>{abortRequest(request);},10000);
}
