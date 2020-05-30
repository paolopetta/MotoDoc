const encodeSessionId = (url) => {
	//Prendo l'url
	let currentUrl = window.location.href;
	
	//Definisco la fine del session id se presente
	let begin = currentUrl.indexOf(";jsessionid") != -1 ? currentUrl.indexOf(";jsessionid") : 0;
	let end = currentUrl.indexOf("#") != -1 ? currentUrl.indexOf("#") : currentUrl.length;
	end = currentUrl.indexOf("?") != -1 ? currentUrl.indexOf("?") : end;
	
	let sessionId = begin != 0 ? currentUrl.substring(begin,end) : currentUrl;
	//Encode del sessionId
	if(currentUrl.length != sessionId.length) 
		url = url + sessionId;
	
	return url;
};

