/* 
* @Author: Marte
* @Date:   2017-09-01 14:29:00
* @Last Modified by:   Marte
* @Last Modified time: 2017-09-14 17:34:32
*/
//检测是否具有class current

    //图片选中
    /*$('.img_file .container').live('click', function(event) {
        if ($(this).hasClass('current')) {
            $(this).removeClass('current');
            $(this).parent('li').removeClass('active');
        }else{
            $(this).addClass('current');
            $(this).parent('li').addClass('active');
        };
    });*/
    //文件全选

    $('.all_box').live('click', function(event) {
        if ($(this).hasClass('current')) {
            $(this).removeClass('current');
            $('.img_file .container').removeClass('current');
            $('.img_file li').removeClass('active');
            $('.check_box').removeClass('current');
        }else{
            $(this).addClass('current');
            $('.img_file .container').addClass('current');
            $('.check_box').addClass('current');
            $('.img_file li').addClass('active');
        };
    });

    //图片列表悬浮效果
    $('.img_file .container').live('mouseenter', function(event) {
        $(this).addClass('current1');
        $(this).children('span').css('display', 'block');
    });
    $('.img_file .container').live('mouseleave', function(event) {
        $(this).removeClass('current1');
        $(this).children('span').css('display', 'none');
    });
    //调出功能菜单

    $('.img_file .container span').live('click', function(event) {
        $(this).parent('div').siblings('div').fadeToggle(200);
        event.stopPropagation();
    });
    $('.order p').live('mouseenter', function(event) {
        $(this).css('color', '#168AFF');
        
    });
    $('.order p').live('mouseleave', function(event) {
        $(this).css('color', '#333');
    });
    //关闭弹出层
    $(document).click(function(e){
        var target  = $(e.target);
          if(target.closest(".order").length == 0){
           $(".order").fadeOut(200);
        }
    });

    //文件名过长自动截取
    function sub_text(name,length){
        for (var i = 0; i < $('.'+name).length; i++) {
            if ($('.'+name).eq(i).text().length>length) {
                var s_head=$('.'+name).eq(i).text().substring(0,5);
                var s_foot=$('.'+name).eq(i).text().substring($('.'+name).eq(i).text().length,$('.'+name).eq(i).text().length-6);
                var new_text=s_head+'…'+s_foot;
                $('.'+name).eq(i).text(new_text);
            };
        };
    };
    

    //文件列表悬浮

    $('.list_file li').live('mouseenter', function(event) {
        $(this).children('.col-1').children('p').css('display', 'block');
    });
    $('.list_file li').live('mouseleave', function(event) {
        $(this).children('.col-1').children('p').css('display', 'none');
    });
    /*$('.list_file li').live('click', function(event) {
        var box=$(this).children('.col-1').children('.check_box');
        if (box.hasClass('current')) {
            box.removeClass('current');
            $(this).removeClass('active');
        }else{
            box.addClass('current');
            $(this).addClass('active');
        };
    });*/

    $("body").live("touchstart", function(e) {
        clearTimeout(timer);
        $(".back_top").stop().css('display', 'block');
    });
    $("body").live("touchend", function(e) {
        timer=setTimeout(function(){
            $(".back_top").stop().css('display', 'none');
        }, 3000);
    });

    


    