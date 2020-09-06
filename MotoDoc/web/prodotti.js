function showProd(cat, contextPath) {
    $("#showProd").html("")
    console.log(cat)
    $.ajax({
        type:'POST',
        url: contextPath + '/Categoria',
        data: {
            categoria: cat
        },
        success: function (data){
            console.log(data)
            console.log('success')
            addRow(contextPath, data)
        },
        error: function(error){
            console.log('error')
        }
    })
}

function addRow(contextPath, data){ //dati json
    let row= document.createElement('DIV') //creo il div
    row.className = 'row row-cols-1 row-cols-md-4'; // assegno il nome della classe
    /*var result= []
    for(var i in data){
        result.push()
    }*/
    data.forEach(function(element, index){
        let col= document.createElement('DIV')
        col.className = 'col mb-4'
        let card= document.createElement('DIV')
        card.className = 'card'
        let img= document.createElement('IMG')
        img.className= 'card-img-top'
        img.setAttribute('src', element['img'])
        img.setAttribute('width', '500')
        img.setAttribute('height', '300')
        let body= document.createElement('DIV')
        body.className = 'card-body'
        let h5= document.createElement('H5')
        h5.className = 'card-title'
        h5.innerText = element['nome']
        h5.setAttribute('align', 'center')
        let paragraf= document.createElement('P')
        paragraf.className= 'card-text'
        paragraf.innerText = element['prezzo']
        var link= "CartServlet?action=addCart&id=" + element['codiceProd']
        let button= document.createElement('A')
        button.setAttribute('href', link)
        button.className= 'btn btn-primary'
        button.setAttribute('align', 'center')
        button.innerText= 'Aggiungi al Carrello'


        body.appendChild(h5)
        body.appendChild(paragraf)
        body.appendChild(button)
        card.appendChild(img)
        card.appendChild(body)
        col.appendChild(card)
        row.appendChild(col)

    })
        document.getElementById('showProd').appendChild(row)

}

/*
<div class="row row-cols-1 row-cols-md-4">
    <div class="col mb-4">
        <div class="card" >
            <img src="<%=bean.getImg()%>" class="card-img-top" alt="..." width="500" height="300">
            <div class="card-body">
                <h5 class="card-title" align="center"><%=bean.getNome()%></h5>
                <p class="card-text" align="center">€ <%=bean.getPrezzo()%></p>
                <a href="<%=response.encodeURL("CartServlet?action=addCart&id=" + bean.getCodiceProd())%>" class="btn btn-primary" align="center">Aggiungi al carrello</a>
            </div>
        </div>
    </div>
    <% 	} %>
</div>
*/