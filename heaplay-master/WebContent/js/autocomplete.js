//Esegue una chiamata ad ogni pressione del tasto 

function autocompleteSearch(el,suggestions) {
	let url = encodeSessionId("/heaplay/search")+"?q="+$(el).val()+"&filter="+$(".search-select").val()+"&auto=true";
	autocomplete(el,suggestions,url);
}


function autocomplete(el,suggestions,url,data) {
	if($(el).val().length > 1) {
		let request = $.ajax({
			"type":"GET",
			"url" : url,
			"data": data,
			"cache":false,
			"success": (data) => {
				$(suggestions).empty();
				data = data.filter((item,pos,array) => {
					return array.indexOf(item) == pos;
				});
				for(let i=0;i<data.length;i++) {
					let option = "<option value='"+data[i]+"'>"+data[i]+"</option>";
					$(option).appendTo(suggestions);
				}	
			},
			"error": (status,error) => console.log("Errore:"+error+" StatusCode:"+status)
		});
		setTimeout(() =>abortRequest(request),10000);
	}
}

//Funzione per effettuare la ricerca al click del tasto Enter
function searchOnEnterButton(e) {
	if(e.keyCode === 13) //keyCode per il tasto Enter
		$(".search-button").trigger("click");
}