$(document).ready(function(){
      $('.slideshow').slick({
        dots: true,
        infinite: true,
        speed: 600,
        arrows: true,
        slidesToShow: 3,
        slidesToScroll: 3,
        adaptiveHeight: true,
        autoplay: true,
        autoplaySpeed: 2000,
        responsive: [
        {
            breakpoint: 1024,
            settings: {
                slidesToShow: 2,
                slidesToScroll: 2,
                infinite: true,
                dots: true
            }
        },
        {
            breakpoint: 600,
            settings: {
                slidesToShow: 2,
                slidesToScroll: 2
            }
        },
        {
            breakpoint: 480,
            settings: {
                slidesToShow: 1,
                slidesToScroll: 1
            }
        }   
        ]
    });
});


