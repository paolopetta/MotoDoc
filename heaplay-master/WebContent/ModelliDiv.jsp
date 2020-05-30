//Esempi dei vari modelli di Div usati (più comodo averli scritti così)

//Esempio div dell'user

<div class='item'>
	<div class='user-image'>
		<img alt='Non trovata'src='/heaplay/getImage?id=" + bean.id + "&extension=...&user=true'>
    </div>
    <div class='user-content'>
    	<span><a href='/heaplay/user/"+bean.username+"'>" + bean.username+ "</a></span>
    </div>
</div>


//Esempio div di un commento
<div class='comment'>
	<h3>
		<a href='/heaplay/user/"+bean.author+"'>"+bean.author+"</a>
	</h3>
	<span class='comment-body'>"+bean.body+"</span>
</div>

//Esempio div di una playlist
<div class='playlist'>
    <div class='playlist-image'>
    	<a href='/heaplay/user/" + bean.authorName + "/playlist/" + bean.name.replace(/\s/g,'') + "?id=" + bean.id + "'><img alt='Non trovata' src='/heaplay/getImage?id=" + (bean.tracks.length > 0 ? bean.tracks[0].id : -1) + "'></a>
    </div>
    <div class='playlist-content'>
    	<span class='author'><a href='/heaplay/user/" + bean.authorName + "'>" + bean.authorName + "</a></span>
        <br/>
        <span class='playlist-name'><a href='/heaplay/user/" + bean.authorName + "/playlist/" + bean.name.replace(/\s/g,'') + "?id=" + bean.id + "'>" + bean.name + "</a></span>
    </div>
</div>

//Esempio div di una track
<div class='song'>
	<audio preload='none' class='audio'
		ontimeupdate='updateCurrentTime(this)'>
		<source
			src='/heaplay/getAudio?id="+ bean.id +"&extension="+bean.trackExt+"'
			type='audio/"+bean.trackExt+"'>
	</audio>
	<div class='song-image'>
		<img width='100px' src='/heaplay/getImage?id="+ bean.id +"'
			alt='Errore'>
	</div>
	
	
	<div class='song-content'>
	
		<div class='song-info'>
				<button class='play'>
						<i class='fa fa-play color-white'></i>
				</button>
				<div>
					<span class='author'><a href='/heaplay/user/"+bean.authorName+"'>"+bean.authorName+"</a></span><br>
					<span class='song-name'><a href='/heaplay/user/"+bean.authorName+"/"+bean.name.replace(/\s/g,'
					')+"?id="+bean.id+"'>"+bean.name+"</a></span>
				</div>
			</div>
			
			<div class='controls'>
				<table>
					<tr>
						<td><span class='song-time'>00:00</span></td>
						<td><input type='range'
						name='slider' step='1' class='slider slider-bar'
						onchange='setCurrentTime(this)' value='0' min='0'
						max='"+bean.duration+"'></td>
						<td><span>"+timePadder(Math.floor(bean.duration/60))
						+":"+timePadder(Math.floor(bean.duration%60)) + "</span></td>
					</tr>
					<tr>
						<td><i class='fa fa-volume-down'></i></td>
						<td><input type='range' name='volume' step='.1' class='slider volume'
							onchange='setVolume(this)' value='1' min='0' max='1'></td>
						<td><i class='fa fa-volume-up'></i></td>
					</tr>
				</table>
			</div>
			<div class='song-buttons'>
				<span>bean.plays riproduzioni</span>
				<span class='song-button' onclick='like(event)' title='Aggiungi Mi Piace'><i class='fa fa-thumbs-up'></i> bean.likes</span>
			</div>		
	</div>
</div>

// Esempio div di una track nell'admin
<tr>
	<td><span class='song-name'><a
			href='/heaplay/user/"+bean.authorName+"/"+bean.name.replace(/\s/g,'
			')+"?id="+bean.id+"'>"+bean.name+"</a></span></td>
	<td><span class='author'><a
			href='/heaplay/user/"+bean.authorName+"'>"+bean.authorName+"</a></span></td>
	<td><span>+bean.type+</span>
	</td>
	<td><input type='hidden' name='track_id' value='"+bean.id+"'>
	<button value='Blocca'>Blocca</button>
	<button>Sblocca</button>
	<button>Rimuovi</button>
	</td>
</tr>

//Esempio div di un user nell' admin
<tr>
	<td>
	<span class='author'><a
			href='/heaplay/user/"+bean.authorName+"'>"+bean.authorName+"</a></span>
	</td>
	<td><span>bean.auth</span></td>
	<td><span>bean.active</span></td>
	<td>
		<input type='hidden' value='bean.id'>
		<button>Blocca</button>
		<button>Sblocca</button>
		<button>Rimuovi</button>
	</td>
</tr>

//Esempio div di un tag nell' admin
<tr>
	<td>
		<span>bean.name</span>
	</td>
	<td>
		<input type='hidden' value='bean.id'>
		<button>Rimuovi</button>
	</td>
</tr>