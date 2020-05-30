//Funzioni di servizio
const searchElement = (list,el) => {
	for(let i = 0; i < list.length ; i++) {
		if($(list[i]).val() === el)
			return true;
	}		
	return false;
};

const removeTag = (object) =>{
	$(object).next("input[name='tag']").remove();
	$(object).remove();
};

const addTag = (button) => {
	let divParent = $(button).parent().parent();
	let val = $(divParent).find("#autocomplete").val();
	let list = document.getElementsByName("tag");
	if(val !== "" && !searchElement(list,val)) {
		$( "<span class='tag' onclick='removeTag(this)'>" + val + "</span>" ).appendTo(divParent.find(".added-tags"));
		$( "<input type='hidden' name='tag' value='" + val +"'>" ).appendTo(divParent.find(".added-tags"));
		$("input[name='tags']").val("");
	
	}
};

//Aggiunta dei vari handlers al caricamento
$(document).ready(() => {
	let objectUrl;
	$("#audioFake").on("canplaythrough", function(e){
		let seconds = e.currentTarget.duration;
		$("#duration").val(Math.floor(seconds));
		URL.revokeObjectURL(objectUrl);
		$("#track-field .file-input").nextAll(".check").fadeIn(); /* Controllare se e' giusto che stia qui */
		$("#track-field .file-input").next(".cross").fadeOut();
	});

	$("#audio").change(function(e){
		let file = e.currentTarget.files["0"];
		if(file != undefined) {
			objectUrl = window.URL.createObjectURL(file);
			$("#audioFake").prop("src", objectUrl);
		}
	});

	//Preview dell'immagine
	$("#image").change(function(e){
		let file = e.currentTarget.files["0"];
		if(file != undefined) {
			let objectUrl = window.URL.createObjectURL(file);
			$("#preview").prop("src", objectUrl);
			$("#preview").parent().removeClass("hidden");
			$("#image-field .file-input").next(".cross").fadeOut();
		}
	});

	$("form").keypress(function(e) {
  		if (e.which == 13) {
			let form = e.currentTarget;
			let tagInput = form["tags"];
			if($(tagInput).val() != "") 
				$("#tagButton").trigger("click");
			return false;
		}
	});
	
});	
		
//Mostra e nasconde il tag html su cui è fissato
function ShowAndHide(status) {					
		if(status == 1)
			$("#divPrice").removeClass("hidden");
		else
			$("#divPrice").addClass("hidden");
}		

function autocompleteTags(el,suggestions) {
	let url = "/heaplay/getTags?query="+$(el).val();
	if($(el).val().length > 1) {
		let request = $.ajax({
			"type":"GET",
			"url" : url,
			"cache":false,
			"success": (data) => {
				$(suggestions).empty();
				for(let i=0;i<data.length;i++) {
					let option = "<option value='"+data[i]+"'>"+data[i]+"</option>";
					$(option).appendTo(suggestions);
				}	
			}
		});
		setTimeout(() =>abortRequest(request),10000);
	}
}

