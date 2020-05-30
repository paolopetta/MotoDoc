const timePadder = (data,pad="0") => (data < 10) ? pad.toString()+data.toString() : data.toString();
const selectOption = (toShow,button) => {
	$(".only").hide();
	$(".only").removeClass("only");
	$(toShow).show();
	$(toShow).addClass("only");
	
	let children = $(button).parent().children();
	for(let i=0; i < children.length; i++)
		if(!$(children[i]).is(button)) 
			$(children[i]).removeClass("selected");
	$(button).addClass("selected");		
};
const abortRequest = (request) => {
	request.abort();
};


$(document).ready( () => {

	const showHide = (x,y) => { 	
		$(x).show();
		$(y).hide();
	};

	$(".search-button").click(() => {	//Listener della ricerca
		let url = encodeSessionId("/heaplay/search")+"?q="+$("#search-box").val()+"&filter="+$(".search-select").val();//url creato dinamicamente (probabilmente bisogna filtrare ciò che è stato scritto dal utente)
		let request = $.ajax({
			"type":"GET",
			"url" : url,
			"beforeSend": () => {
				$(".content-wrapper > *").not(".search-content").remove();
				showHide($(".loading"),$("#content"));
			},
			"complete"  : () => {showHide($("#content"),$(".loading"))},
			"success": (data) => {
				let typeOfSearch = url.substring(url.indexOf("&filter")+8,url.length); //Controllo il tipo di ricerca
				const headerDiv ="<p>Elementi trovati: <span id='found'>"+data.length+"</span></p>";
				const tableDiv = "<table class='admin-table'></table>"
				//Estrazione del container e rimozione degli elementi precedenti/inserimento dell'header
				let container = $("#content .flex-container");
				$("#content p").remove();
				$(container).empty();
				$(headerDiv).prependTo($("#content"));	
				if(data.list.length > 0)
					$(tableDiv).appendTo(container);
					
				let table = $("table");
				//Scelta dell'header della table
				if(typeOfSearch == "track")
					$("<tr><th>Brano</th><th>Autore</th><th>Tipo</th><th>Azione</th></tr>").appendTo(table);
				else if(typeOfSearch == "tag")
					$("<tr><th>Nome</th><th>Azione</th></tr>").appendTo(table);
				else if(typeOfSearch == "user")
					$("<tr><th>Utente</th><th>Ruolo</th><th>Attivo</th><th>Azione</th></tr>").appendTo(table);
						
				for(let i = 0 ; i < data.list.length; i++){
					//Estrazione del bean
					let bean = data.list[i];
					createDiv(bean,table,typeOfSearch);
				}	
			},
			"error": (status,error) => console.log("Errore:"+error+" StatusCode:"+status)
		});	
		setTimeout(() =>abortRequest(request),10000);
	});


	const onScroll = () => {
		let container = $(".admin-table");
		let numberOfElements = $(container).children().length-1;

		//Effettuo la chiamata solo quando ho già effettuato una ricerca e ho raggiunto il bottom della pagina
		if(numberOfElements > 0  && ($(window).scrollTop() + $(window).height() >= $(document).height()-1)) {
			let url = encodeSessionId("/heaplay/search")+"?q="+$("#search-box").val()+"&startFrom="+numberOfElements.toString()+"&filter="+$(".search-select").val(); //url creato dinamicamente (probabilmente bisogna filtrare ciò che è stato scritto dal utente)
			let found = parseInt($("#found").text(),10); //Numero di elementi trovati dalla ricerca
			//Effettuo la chiamata se esistono ancora elementi da caricare
			if( found > numberOfElements) { 
				$(window).off("scroll");
				let request = $.ajax({
					"type":"GET",
					"url" : url,
					"success": (data) => {
						let typeOfSearch = url.substring(url.indexOf("&filter")+8,url.length); //Controllo il tipo di ricerca
						numberOfElements = $(container).children().length-1;
						
						//Ulteriore controllo 
						if(found > numberOfElements) {
							for(let i = 0 ; i < data.list.length; i++){
								//Estrazione del bean
								let bean = data.list[i];
								//Creazione del div
								createDiv(bean,container,typeOfSearch);	
							}	
							$(window).scroll(onScroll);
						}
					},
					"error": (status,error) => console.log("Errore:"+error+" StatusCode:"+status)
				});
				setTimeout(() => abortRequest(request),10000);
			}
		}
	};

	$(window).scroll(onScroll);
});

//Dato un bean crea il div corrispondente
function createDiv(bean,container,typeOfSearch) {
	//Button da aggiungere in base allo stato del brano o dell'user
	const buttonTrack = (bean.indexable == true) ? "<button onclick='disableTrack(event)'>Blocca</button></td></tr>" : "<button onclick='enableTrack(event)'>Sblocca</button></td></tr>"; 
	const buttonUser = (bean.active == true )  ? "<button onclick='disableUser(event)'>Blocca</button>" : "<button onclick='enableUser(event)'>Sblocca</button>";
	//Varie table rows
	const trackDiv = typeOfSearch == "track" ? "<tr><td ><span><b><a href='"+encodeSessionId("/heaplay/user/"+bean.authorName+"/"+bean.name.replace(/\s|!|\*|\'|\(|\)|\;|\:|@|&|=|\$|\,|\/|\?|\#|\[|\]/g,''))+"?id="+bean.id+"'>"+bean.name+"</a></b></span></td><td><span><a href='"+encodeSessionId("/heaplay/user/"+bean.authorName)+"'>"+bean.authorName+"</a></span></td><td><span>"+bean.type+"</span></td><td><input type='hidden' name='track_id' value='"+bean.id+"'><button onclick='removeTrack(this)'>Rimuovi</button>"+buttonTrack : "";
	const tagDiv="<tr><td><span><b>"+bean.name+"</b></span></td><td><input type='hidden' value='"+bean.id+"'><button onclick='removeTag(this)'>Rimuovi</button></td></tr>";
	const userDiv = "<tr><td><span><b><a href='"+encodeSessionId("/heaplay/user/"+bean.username)+"'>"+bean.username+"</a></b></span></td><td><span>"+bean.auth+"</span></td><td><span class='active'>"+bean.active+"</span></td><td>"+buttonUser+"<button onclick='removeUser(this)'>Rimuovi</button><input type='hidden' value='"+bean.id+"'></td></tr>"
	const playlistDiv= typeOfSearch == "playlist" ? "<div class='playlist'><div class='playlist-image'><img alt='Non trovata' src='/heaplay/getImage?id=" + (bean.tracks.length > 0 ? bean.tracks[0].id : -1) + "'></div><div class='playlist-content'><span class='author'><a href='"+encodeSessionId("/heaplay/user/" + bean.authorName)+"'>" + bean.authorName + "</a></span><br/><span class='playlist-name'><a href='"+encodeSessionId("/heaplay/user/" + bean.authorName + "/playlist/" + bean.name.replace(/\s|!|\*|\'|\(|\)|\;|\:|@|&|=|\$|\,|\/|\?|\#|\[|\]/g,''))+ "?id=" + bean.id + "'>" + bean.name + "</a></span></div></div>" : "";
	const commentDiv ="<div class='comment'><span class='comment-author'><b><a href='"+encodeSessionId("/heaplay/user/"+bean.author)+"'>"+bean.author+"</a></b></span><span class='comment-body'>"+bean.body+"</span></div>";
	

	//Scelta del div da usare
	let div = (typeOfSearch == "track") ? trackDiv : typeOfSearch == "user" ? userDiv : typeOfSearch == "tag" ? tagDiv : (typeOfSearch == "comment") ? commentDiv : playlistDiv;
	
	//Creazione del div e inserimento
	let ob = $(div);
	$(ob).appendTo($(container));
}

/***Funzioni di eliminazione ***/

/**Tracks */
function removeTrack(button) {
	let track_id = $(button).parent().find("input").val();
	
	$.ajax({
		"type":"GET",
		"url" :encodeSessionId("/heaplay/removeTrack")+"?track_id="+track_id,
		"success": () => {
			$(button).parent().parent().remove();
		}
	});
}

function disableTrack(event) {
	let button = event.currentTarget;
	let track_id = $(button).parent().find("input").val();
	
	$.ajax({
		"type":"GET",
		"url" :encodeSessionId("/heaplay/removeTrack")+"?track_id="+track_id+"&disable=true",
		"success": () => {
			$(button).html("Sblocca");
			$(button).off();
			$(button).click(enableTrack);
		}
	});
}

function enableTrack(event) {
	let button = event.currentTarget;
	let track_id = $(button).parent().find("input").val();
	
	$.ajax({
		"type":"GET",
		"url" :encodeSessionId("/heaplay/removeTrack")+"?track_id="+track_id+"&enable=true",
		"success": () => {
			$(button).html("Blocca");
			$(button).off();
			$(button).click(disableTrack);
		}
	});
}

/**Users */
function removeUser(button) {
	let user_id = $(button).parent().find("input").val();
	
	$.ajax({
		"type":"GET",
		"url" :encodeSessionId("/heaplay/admin/removeUser")+"?user_id="+user_id,
		"success": () => {
			$(button).parent().parent().remove();
		}
	});
}

function disableUser(event) {
	let button = event.currentTarget;
	let user_id = $(button).parent().find("input").val();
	
	$.ajax({
		"type":"GET",
		"url" :encodeSessionId("/heaplay/admin/removeUser")+"?user_id="+user_id+"&disable=true",
		"success": () => {
			$(button).html("Sblocca");
			$(button).off();
			$(button).click(enableUser);
			$(button).parent().parent().find(".active").html("false");
		}
	});
}

function enableUser(event) {
	let button = event.currentTarget;
	let user_id = $(button).parent().find("input").val();
	
	$.ajax({
		"type":"GET",
		"url" :encodeSessionId("/heaplay/admin/removeUser")+"?user_id="+user_id+"&enable=true",
		"success": () => {
			$(button).html("Blocca");
			$(button).off();
			$(button).click(disableUser);
			$(button).parent().parent().find(".active").html("true");
		}
	});
}

/*Tags */
function removeTag(button) {
	let tag_id = $(button).parent().find("input").val();
	
	$.ajax({
		"type":"GET",
		"url" :encodeSessionId("/heaplay/admin/removeTag")+"?tag_id="+tag_id,
		"success": () => {
			$(button).parent().parent().remove();
		}
	});
}
