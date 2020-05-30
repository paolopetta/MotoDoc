const loadComments = () => {
	//Ajax che carica i commenti 
	let src = $(".audio").children().prop("src");
	let track_id = src.substring(src.indexOf("id")+3,src.indexOf("&"));
	let container = $(".comment-container");
	let begin = $(container).children().length;

	let request = $.ajax({
		"type":"GET",
		"url" : "/heaplay/getComments",
		"data": "track_id="+track_id+"&begin="+begin,
		"success": (data) => {
				let beans = data;
				if(begin <= 0) {
					$(container).empty();	
				}	
				if(beans.length < 10)
					$(window).off();
				//Creazione dei div
				for(let i = 0 ; i < beans.length; i++){
					//Estrazione del bean
					let bean = beans[i];
					createDiv(bean,container,"comment");
				}	
			},
		"error": (status,error) => console.log("Errore:"+error+" StatusCode:"+status)	
	});
	setTimeout(() =>abortRequest(request),10000);
};

function uploadComment(e) {
	let url = encodeSessionId("/heaplay/uploadComment");
	let comment = $(e).parent().find("textarea").val();
	let src = $(".audio").children().prop("src");
	let track_id = src.substring(src.indexOf("id")+3,src.indexOf("&"));
	
	if(comment != "") {
		$.ajax({
			"url":url,
			"type":"GET",
			"data": "track_id="+track_id+"&comment="+comment,
			"success" : () => {
				loadComments();
				$(e).parent().find("textarea").val("");
			},
			"error": (status,error) => console.log("Errore:"+error+" StatusCode:"+status)
		});
	}	
}


$(document).ready(() => {
	loadComments();
	$(window).scroll(() => {
		if($(window).scrollTop() + $(window).height() >= $(document).height()-1)
			loadComments();
	});
		
});
