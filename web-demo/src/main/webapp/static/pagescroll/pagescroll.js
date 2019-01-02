$(function () {
    let clientHeight = document.body.clientHeight/2;
    $('body').append('<div id="pageScroll"><div id="pageUp"></div><div id="pageDown"></div></div><div id="pageScrollSwitcher"></div><div id="pageScrollSwitcherLeft"></div>')
    $('#pageScroll').css({top: clientHeight});

    let container = $('.DocumentContainer');
    if(container.length === 0) {
        container = $(window);
    }
    $('#pageDown').on('click', function () {
        container.scrollTop(container.scrollTop() + container.height() * .9);
    })
    $('#pageUp').on('click', function () {
        container.scrollTop(container.scrollTop() - container.height() * .9);
    })
    let toggleClassAndCookie = function (side, $this) {
        if($("#pageScroll").hasClass("cssVisibility")){
            setCookie("pageScroll", side, 30);
            if(side === 'right'){
                $('#pageScroll').css({left: 'auto',right:0});
            }else if(side === 'left'){
                $('#pageScroll').css({left: 0,right:'auto'});
            }
        }else {
            setCookie("pageScroll", "off", 30);
        }
        $("#pageScroll").toggleClass('cssVisibility');
        // $($this).toggleClass('pageScrollEnable');
    };
    $('#pageScrollSwitcher').on('click', function () {
        toggleClassAndCookie('right', this)
    });
    $('#pageScrollSwitcherLeft').on('click', function () {
        toggleClassAndCookie('left', this)
    });

    let pageScroll = getCookie('pageScroll');
    if(pageScroll === 'right'){
        $('#pageScroll').css({left: 'auto',right:0});
        // $('#pageScrollSwitcher').addClass('pageScrollEnable');
    }else if(pageScroll === 'left'){
        $('#pageScroll').css({left: 0,right:'auto'});
        // $('#pageScrollSwitcherLeft').addClass('pageScrollEnable');
    }else {
        $('#pageScroll').addClass("cssVisibility");
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