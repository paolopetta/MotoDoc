$(document).ready(function(){
    $(".card-img-top").hover(function(){
        $(".card-img-top").css({
            "opacity": "1"
        });
    }, function(){
        $(this).css({
            "opacity": "0.5"
        });
    });
});