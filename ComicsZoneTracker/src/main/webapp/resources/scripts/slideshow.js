$(document).ready(function(){
      $('.slideshow').slick({
        dots: true,
        arrows: true,
        slidesToShow: 3,
        slidesToScroll: 3,
        autoplay: true,
        autoplaySpeed: 2000,
        responsive: [
        {
            breakpoint: 960,
            settings: {
                slidesToShow: 1,
                slidesToScroll: 1
            }
        } 
        ]
    });
});


