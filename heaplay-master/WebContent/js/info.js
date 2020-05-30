
/***Info functions ***/
$(document).ready( () => {
	//Caricamento API
	google.charts.load('current', {'packages':['corechart']});
	//Necessita di aspettare
	setTimeout(() => {
		$(".mostViewedButton").trigger("click");
	},500);
	//Listener per riadattare i grafici
	window.addEventListener('resize', function () {
        getInfo($(".selected"));
    }, false);
});

//Funzioni per prendere info

const getInfo = (el) => {
	let url = $(el).prop("href");
	if(url != undefined) {
		let selection = url.substring(url.indexOf("#")+1,url.length);
		
		let request = $.ajax({
			"type" : "GET",
			"url" : encodeSessionId("/heaplay/getInfo"),
			"data": "select="+selection,
			"success": (data) => {
				$("#info-bar").empty();
				google.charts.setOnLoadCallback(drawChart(data,selection));

			},
			"error": (status,error) => console.log("Errore:"+error+" StatusCode:"+status)
		});
		setTimeout(() =>abortRequest(request),10000);
	}
};

//Funzione per disegnare i grafici

function drawChart(data,type) {
	let arr = [];
	type = type == "plays" ? "Ascolti" : type == "likes" ? "Like" : "Vendute";
	
	for(let i=0;i < data.length;i++)
		arr[i] = [data[i].name,type == "Ascolti" ? data[i].plays : type == "Like" ? data[i].likes : data[i].sold];
	
	let content = new google.visualization.DataTable();
	content.addColumn('string', 'Brani');
    content.addColumn('number', type);
    content.addRows(arr);
	
	let options = {'title':'Brani',
                       'width':$("#info-bar").width()-10,
                       'height':$("#info-bar").height()};

   	let chart = new google.visualization.BarChart(document.getElementById("info-bar"));
    chart.draw(content, options);
}