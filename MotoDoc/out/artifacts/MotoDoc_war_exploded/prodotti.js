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
        },
        error: function(error){
            console.log('error')
        }
    })

}