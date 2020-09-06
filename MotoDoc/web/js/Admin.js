$(function(ready){
    $('#selectCategoria').change(function(){
        $('.aggiuntivi').css('display', 'none');

        if(document.getElementById('optionCarrozzeria').selected) $('#carrozzeria').css('display', 'block');
        else if(document.getElementById('optionMeccanica').selected) $('#meccanica').css('display', 'block');
        else if(document.getElementById('optionPneumatici').selected) $('#pneumatici').css('display', 'block');
    })
})