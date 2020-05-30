//Var globale
let audio,volume,slider,time,playButton;
const showHide = (el) => {
	if($(el).css("display") === "none")
		$(el).removeClass("hidden");
	else
		$(el).addClass("hidden");	
};

//Inizializzazione dei vari handlers
$(document).ready(function init() {
	addEventHandlers();
	focusButton();
});
//Aggiunta degli handlers per tutte le classi
function addEventHandlers() {
	$(".play").click(startAudio);
	$(".play").click(view);
}

//Handlers
function loadAudio(){
	$(audio[0]).trigger('load');
}
//Metodo chiamato per inizializzare
function startAudio(e){
	if(audio != undefined || audio != null)
	 	stopAudio();
	let parent = $(e.currentTarget).parent().parent().parent(); //Risalgo di gerarchie
	init(parent);
	loadAudio();
	playAudio();
}
//Inizializzazione
function init(parent) {
	audio = $(parent).find(".audio");
	volume = $(parent).find(".volume");
	$(volume).parent().parent().removeClass("hidden");
	slider = $(parent).find(".slider-bar");
	time = $(parent).find(".song-time");
	playButton = $(parent).find(".play");
	$(slider).prop("value",0);
}

function playAudio() {
	audio.trigger("play");
	//Gestione listener
	$(playButton).off();
	$(playButton).click(pauseAudio);
	//Gestione classe
	$(playButton).children().removeClass();
	$(playButton).children().prop("class","fa fa-pause color-white");
}

function pauseAudio(){
	if(audio != undefined) {
		audio.trigger('pause');
		//Gestione listener
		$(playButton).off();
		$(playButton).click(playAudio);
		//Gestione classe
		$(playButton).children().removeClass();
		$(playButton).children().prop("class","fa fa-play color-white");
	}	
}
//Metodo per resettare l'audio
function stopAudio(){
    pauseAudio();
	reset();
}

function reset() {
	audio.prop("currentTime",0);
	playButton.off();
	playButton.click(startAudio);
	$(slider).prop("value",0);
	$(time).html("00:00");
	$(volume).parent().parent().addClass("hidden");
}

function forwardAudio(){
	if(audio != undefined) {
		pauseAudio();
		audio.prop("currentTime",audio.prop("currentTime")+5);
		if(audio.prop("currentTime") == 0) {
			loadAudio();
			audio.prop("currentTime",time+5);
		}	
		playAudio();
	}	
}
 
function backAudio(){
	if(audio != undefined) {
		pauseAudio();
		audio.prop("currentTime",audio.prop("currentTime")-5);
		if(audio.prop("currentTime") == 0) {
			loadAudio();
		}	
		playAudio();
	}
}

function setVolume(el) {
	if($(el).is($(volume))) {
		let val = el.value;
		audio.prop("volume",val);
	}else
		$(el).prop("value",1);
}

function replayAudio() {
	if(audio != undefined)
		audio.prop("loop") ? audio.prop("loop",false) : audio.prop("loop",true);
}

function setCurrentTime(params) {
	if($(params).is($(slider))){			//Metodo is per vedere se due tag html sono identici
		let currentTime = $(params).val();
		let min = Math.floor(currentTime/60), sec = Math.round(currentTime%60);
		min = timePadder(min);						
		sec = timePadder(sec);
		$(time).html( min + ":" + sec);
		$(audio[0]).prop("currentTime",currentTime+"");
		if(audio.prop("currentTime") == 0) {
			loadAudio();
			audio.prop("currentTime",currentTime+"");
		}	
		playAudio();
	}
	else
		$(params).prop("value",0);
}

function updateCurrentTime (event) {
	let currentTime = event.currentTime;
	let min = Math.floor(currentTime/60), sec = Math.round(currentTime%60);
	min = timePadder(min);
	sec = timePadder(sec);

	$(slider).prop("value",Math.floor(currentTime));
	$(time).html(min + ":" + sec);
	
	//Necessario a capire se scorrere
	if($("#playlist-page") != undefined && Math.round(event.duration) == Math.round(currentTime))
		next();
	
}

/*********Plays e Likes ***********/

//Funzione che in maniera asincrona incrementa i plays
function view(e) {
	let url = $(e.currentTarget).parent().parent().parent().find(".audio").children().prop("src");
	let id = url.substring(url.indexOf("id")+3,url.indexOf("&"));
	
	let request = $.ajax({
		"type":"GET",
		"url" : encodeSessionId("/heaplay/view"),
		"data": "id="+id,
		"error": (status,error) => console.log("Errore:"+error+" StatusCode:"+status)
	});
	setTimeout(() =>abortRequest(request),10000);
}

function like(e) {
	let url = $(e.currentTarget).parent().parent().parent().find(".audio").children().prop("src");
	let id = url.substring(url.indexOf("id")+3,url.indexOf("&"));
	let span = e.currentTarget;
	let request = $.ajax({
		"type":"GET",
		"url" : encodeSessionId("/heaplay/view"),
		"data": "id="+id+"&like=true",
		"success" : () => {
			if($($(span).children()).hasClass("far fa-thumbs-up")) {
				let likes = Number.parseInt($(span).html().substring($(span).html().indexOf("</i>")+4,$(span).html().length))+1;
				$(span).html("<i class='fa fa-thumbs-up'></i> "+ likes)
			} else {
				let likes = Number.parseInt($(span).html().substring($(span).html().indexOf("</i>")+4,$(span).html().length))-1;
				$(span).html("<i class='far fa-thumbs-up'></i> "+ likes)
			}
		},
		"error": (status,error) => console.log("Errore:"+error+" StatusCode:"+status)
	});
	setTimeout(() =>abortRequest(request),10000);
}


/********* Playlist ************/

const changeAudio = (parent) => {
	if(audio != undefined)
		stopAudio();
	init(parent);
	loadAudio();
	playAudio();
};

const findIndex = (audio,array) => {
	for(let i = 0 ; i < array.length ; i++)
		if(audio.is(array[i])) 
			return i;	
};

function next() {
	let array = $(".audio");
	let i = (audio != undefined)  ? findIndex(audio,array) : -1;
	changeAudio($(array[(i+1)%array.length]).parent())
	
}

function prev() {
	let array = $(".audio");
	let i = (audio != undefined)  ? findIndex(audio,array) : 1; 
	i = (i==0) ? array.length : i;
	changeAudio($(array[(i-1)%array.length]).parent())
}
