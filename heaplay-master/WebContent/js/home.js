$(document).ready(() => {
	//Ajax che mette nella home le track più votate
	let request = $.ajax({
		"type":"GET",
		"url" : "/heaplay/getLikedTracks",
		"success": (data) => {
				//Parsing dell'oggetto JSON	
				let beans = JSON.parse(data);
				let container = $("#songs .flex-container");
				$(container).empty();
				//Creazione dei div
				for(let i = 0 ; i < beans.length; i++){
					//Estrazione del bean
					let bean = beans[i];
					createDiv(bean,container,"track");
				}	
				addEventHandlers();
			},
		"error": (status,error) => console.log("Errore:"+error+" StatusCode:"+status)	
	});
	setTimeout(() =>abortRequest(request),10000);
	
	//Ajax che mette nella home le playlist più ascoltate
	let otherRequest = $.ajax({
		"type":"GET",
		"url" : "/heaplay/getBestPlaylists",
		"success": (beans) => {
				let container = $("#playlists .flex-container");
				$(container).empty();	
				//Creazione dei div
				for(let i = 0 ; i < beans.length; i++){
					//Estrazione del bean
					let bean = beans[i];
					createDiv(bean,container,"playlist");
				}	
			},
		"error": (status,error) => console.log("Errore:"+error+" StatusCode:"+status)	
	});
	setTimeout(() =>abortRequest(otherRequest),10000);
	
});