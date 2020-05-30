//Chiusura della finestra quando si clicka al di fuori della finestra
$(document).ready(() => {
	window.onclick = (event) => {
		let element = event.target;
		let play = $(".playlist-form");
		if($(element).is(play))
			showHide(play);
	};
});

function addToPlaylist(button) {
	let parent = $(button).parent().parent().parent();
	let audioSrc = $(parent).find(".audio").children().prop("src");
	let track_id = audioSrc.substring(audioSrc.indexOf("id")+3,audioSrc.indexOf("&"));
	$("#track_id").val(track_id);
	showHide($(".playlist-form"));
}

function validatePlaylist(form) {
	let input = form["playlistName"];
	let bool = $(input).val() != "";
	if(bool == false) 
		$(input).css("border-color","red");
	else
		$(input).css("border-color","");
		
	return bool;	
}

function autocompletePlaylist(el,suggestions) {
	let auto = $(el).val();
	let audioSrc = $('#track_id').val();
	let track_id = audioSrc.substring(audioSrc.indexOf("id")+3,audioSrc.indexOf("&"));
	let url = "/heaplay/getPlaylists";
	let data = "autocomplete="+auto+"&track_id="+track_id;
	autocomplete(el,suggestions,url,data);
}

function removeFromPlaylist(button) {
	let url = $("<form>").prop("action");
	let play_id = url.substring(url.indexOf("id")+3,url.length);
	let parent = $(button).parent();
	url = $(parent).find(".audio").children().prop("src");
	let track_id = url.substring(url.indexOf("id")+3,url.indexOf("&"));

	let request = $.ajax({
		"type":"GET",
		"url": encodeSessionId("/heaplay/removeFromPlaylist"),
		"data" : "track_id="+track_id+"&play_id="+play_id,
		"success" : () => {
			$(parent).remove();
		},
		"error": (status,error) => console.log("Errore:"+error+" StatusCode:"+status)
	});
	setTimeout(() =>abortRequest(request),10000);
}


