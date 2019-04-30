/* 
* @Author: Marte
* @Date:   2017-09-05 13:58:58
* @Last Modified by:   Marte
* @Last Modified time: 2017-09-15 10:13:14
*/

$(document).ready(function(){
    var host_url='';
    function getImgList(page){
        var html=$('.arr_box').html();
        
        $.ajax({
            type: "post",
            url:host_url+'/getFileList/'+page+'/25',
            dataType: "JSON",
            success: function(data) {
                for (var i = 0; i < data.data.length; i++) {
                    var thisFilePath = decodeURI(data.data[i].filePath);
                   // console.log(thisFilePath);
                    html+=' <li value="'+data.data[i].index+'">'+
                                '<div class="container" title="'+decodeURI(data.data[i].fileName)+'" data-path="'+thisFilePath+'">'+
                                    '<a href="/viewpdf/web/viewer.html?value='+data.data[i].index+'" target="_blank" class="title_link"><img src="image/wenjian.png"/></a>'+
                                    '<h4 class="title_text"><a href="/viewpdf/web/viewer.html?value='+data.data[i].index+'" target="_blank" class="title_link">'+decodeURI(data.data[i].fileName)+'</a></h4>'+
                                    '<span><img src="image/wenjian_gengduo.png"/></span>'+
                                '</div>'+
                                '<div class="order">'+
                                    // '<p>重命名</p>'+
                                    '<p><a href="'+host_url+'/getFile/'+data.data[i].index+'" class="img_down_btn">下载</a></p>'+
                                    // '<p>删除</p>'+
                                '</div>'+
                            '</li>';
                };
                
                $('.arr_box').html(html);
                sub_text('title_link',13);
                $('.file_list .head .right').text('已加载'+$('.arr_box li').length+'个');
            }
        });
    }
    function getFileList(page){
        var html=$('.arr_box').html();
        $.ajax({
            type: "post",
            url:host_url+'/getFileList/'+page+'/20',
            dataType: "JSON",
            success: function(data) {
                for (var i = 0; i < data.data.length; i++) {
                    var size=data.data[i].size/(1024*1024);
                    size = size.toFixed(2);
                    var time=data.data[i].time;
                    time=new Date(time).toLocaleString();
                    html+='<li class="clearfix" value="'+data.data[i].index+'">'+
                                '<div class="col-1">'+
                                    // '<span class="check_box"></span>'+
                                    '<b title="'+decodeURI(data.data[i].fileName)+'"><a href="/viewpdf/web/viewer.html?value='+data.data[i].index+'" target="_blank" class="title_link">'+decodeURI(data.data[i].fileName)+'</a></b>'+
                                    '<p class="btn">'+
                                    '<a class="xiazai" href="'+host_url+'/getFile/'+data.data[i].index+'"></a>'+
                                    '</p>'+
                                '</div>'+
                                '<p class="col-2">'+
                                    '<span>'+size+'MB</span>'+
                                '</p>'+
                                '<p class="col-3">'+
                                    '<span>'+time+'</span>'+
                                '</p>'+
                            '</li>';
                };
                
                $('.arr_box').html(html);
                // sub_text('title_text',13);
                $('.file_list .head .right').text('已加载全部,共'+$('.arr_box li').length+'个'); 
            }
        });
    }
    if (document.documentElement.clientWidth>768) {
        getImgList(1);
        $('.arr_box').addClass('img_file').removeClass('list_file');
        $('.list_toggle .title_list').addClass('current').siblings('span').removeClass('current');
    }else{
        getFileList(1);
        $('.arr_box').addClass('list_file').removeClass('img_file');
        $('.list_toggle .img_list').addClass('current').siblings('span').removeClass('current');
    };
    $('.download_all').live('click', function(event) {
        var arr_box = [];
        for (var i = 0; i < $('.arr_box li.active').length; i++) {
            var file_index=$('.arr_box li.active')[i].value;
            arr_box.push(file_index);

        };
        var json_box =JSON.stringify(arr_box);
        console.log(host_url+'/getFiles/'+json_box);
        window.location.href = host_url+'/getFiles/'+json_box;
    });
    //鼠标滚动异步加载
    var page_num=2;
    var finished=true;
    window.onscroll=function(event) {
        var vH=document.documentElement.clientHeight;
        var sH=document.documentElement.scrollTop || document.body.scrollTop;
        var oH=document.documentElement.scrollHeight;
        if (vH+sH>oH-200 && finished) {
            finished=false;
            // alert(finished);
            if ($('.list_toggle .img_list').hasClass('current')) {
                getFileList(page_num);
                page_num+=1;
                setTimeout(function(){
                    finished=true;
                }, 500);
            
            }else{
                 getImgList(page_num);
                 page_num+=1;
                 setTimeout(function(){
                    finished=true;
                }, 500);
            
            };
        };
        
    };


    // 文件展示切换
    $('.list_toggle .img_list').live('click', function(event) {
        $(this).addClass('current').siblings('span').removeClass('current');
        $('.arr_box').html('');
        getFileList(1);
        $('.arr_box').addClass('list_file').removeClass('img_file');
    });
    $('.list_toggle .title_list').live('click', function(event) {
        $(this).addClass('current').siblings('span').removeClass('current');
        $('.arr_box').html('');
        getImgList(1);
        $('.arr_box').addClass('img_file').removeClass('list_file');
    });
    

    // 上传文件添加
    /*$('.dropzone').live('click', function(event){
        $.ajax({
            type: "post",
            url:'/request.php',
            dataType: "JSON",
            data: {
                'path':'/uploadFile',
            },
            success: function(data) {
                console.log(data);
            }
        });
    });*/

    // 刷新页面
    $('.refresh').live('click', function(event) {;
        if ($('.list_toggle .img_list').hasClass('current')) {
            $('.arr_box').html('');
            getFileList(1);
        }else{
            $('.arr_box').html('');
            getImgList(1);
        };
    });   

    this.previewFile = function(fileUrl,fileType) {
        if (fileType === 'pdf') {
            var tmpUrl = 'viewpdf/web/viewer.html?file=/getFile/723'; //+ encodeURIComponent(fileUrl);
            $("#iframePreview").attr("src", tmpUrl);
            angular.element('#previewModal').modal();
        }
    };



});


