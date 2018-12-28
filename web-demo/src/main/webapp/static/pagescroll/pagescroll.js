$(function () {
    let clientHeight = document.body.clientHeight/2;
    $('body').append('<div id="pageScroll"><div id="pageUp"></div><div id="pageDown"></div></div><div id="pageScrollSwitcher"></div>')
    $('#pageScroll').css({top: clientHeight});

    let container = $('.DocumentContainer');
    if(container.length === 0) {
        container = $(window);
    }
    $('#pageDown').on('click', function () {
        container.scrollTop(container.scrollTop() + container.innerHeight() * .9);
    })
    $('#pageUp').on('click', function () {
        container.scrollTop(container.scrollTop() - container.innerHeight() * .9);
    })
    $('#pageScrollSwitcher').on('click', function () {
        if($("#pageScroll").hasClass("cssVisibility")){
            setCookie("pageScroll", "on", 30);
        }else {
            setCookie("pageScroll", "off", 30);
        }
        $("#pageScroll").toggleClass('cssVisibility');
        $(this).toggleClass('pageScrollEnable');
    })

    let pageScroll = getCookie('pageScroll');
    if(pageScroll !== 'on'){
        $('#pageScroll').addClass("cssVisibility");
    }else {
        $('#pageScrollSwitcher').addClass('pageScrollEnable');
    }

});

function setCookie(cname,cvalue,exdays){
    var d = new Date();
    d.setTime(d.getTime()+(exdays*24*60*60*1000));
    var expires = "expires=" + d.toLocaleDateString();
    let cookie = cname+"="+cvalue+"; " + expires + "; path=/";
    console.log(cookie);
    document.cookie = cookie;
}
function getCookie(cname){
    var name = cname + "=";
    var ca = document.cookie.split(';');
    for(var i=0; i<ca.length; i++) {
        var c = ca[i].trim();
        if (c.indexOf(name)==0) { return c.substring(name.length,c.length); }
    }
    return "";
}