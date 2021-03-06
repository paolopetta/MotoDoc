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

    data.forEach(function(element, index){
        let col= document.createElement('DIV')
        col.className = 'col mb-4'
        let card= document.createElement('DIV')
        card.className = 'card'
        let img= document.createElement('IMG')
        img.className= 'card-img-top'
        img.setAttribute('src', element['img'])
        img.setAttribute('width', '300')
        img.setAttribute('height', '300')
        let body= document.createElement('DIV')
        body.className = 'card-body'
        let h5= document.createElement('H5')
        h5.className = 'card-title'
        h5.innerText = element['nome']
        h5.setAttribute('align', 'center')
        let marca= document.createElement('P')
        marca.className= 'card-text'
        marca.innerText = element['marca']
        marca.setAttribute('align', 'center')

        let misura= document.createElement('P')
        misura.className= 'card-text'
        misura.innerText = element['misure']
        misura.setAttribute('align', 'center')

        let materiale= document.createElement('P')
        materiale.className= 'card-text'
        materiale.innerText = element['materiale']
        materiale.setAttribute('align', 'center')

        let impiego= document.createElement('P')
        impiego.className= 'card-text'
        impiego.innerText = element['impiego']
        impiego.setAttribute('align', 'center')

        let paragraf= document.createElement('P')
        paragraf.className= 'card-text'
        paragraf.innerText = '€' + element['prezzo']
        paragraf.setAttribute('align', 'center')
        var link= "CartServlet?action=addCart&id=" + element['codiceProd']
        let button= document.createElement('A')
        button.setAttribute('href', link)
        button.className= 'btn btn-primary'
        button.setAttribute('align', 'center')
        button.innerText= 'Aggiungi al Carrello'


        body.appendChild(h5)
        body.appendChild(marca)
        if(~element['codiceProd'].indexOf('P')) body.appendChild(misura)
        if(~element['codiceProd'].indexOf('C')) body.appendChild(materiale)
        if(~element['codiceProd'].indexOf('M')) body.appendChild(impiego)
        body.appendChild(materiale)
        body.appendChild(paragraf)
        body.appendChild(button)
        card.appendChild(img)
        card.appendChild(body)
        col.appendChild(card)
        row.appendChild(col)

    })
        document.getElementById('showProd').appendChild(row)

}

