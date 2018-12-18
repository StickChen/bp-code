$(function () {

    $('.normal-view').before('<div id="TocContainer"><div id="barSplitterContainer"><div id="sideToolbarContainer"></div><div class="splitter"></div></div><a href="javascript:void(0);" id="sideCatalogBtn" ></a><div  id="sideCatalogRefreshBtn" ></div></div>');

    // 通过cookie保存状态
    let sideToc = getCookie("sideToc");
    if(sideToc === "0") {
        $("#barSplitterContainer").addClass("cssVisibility");
    }
    // 显示隐藏目录
    $('#sideCatalogBtn').on('click', function () {
        if($("#barSplitterContainer").hasClass("cssVisibility")){
            setCookie("sideToc", "1", 30);
        }else {
            setCookie("sideToc", "0", 30);
        }
        $("#barSplitterContainer").toggleClass('cssVisibility')
        $(this).toggleClass('sideCatalogBtnDisable')
    });
    $('#sideCatalogRefreshBtn').on('click', function () {
        scriptBody()
        // 太卡了，去掉吧
        // scrollSpy();
    })

    let intervalScriptBody = setInterval(intervalBody, 3000);

    // 自动刷新
    MutationObserver = window.MutationObserver || window.WebKitMutationObserver;
    let observer;

    function intervalBody() {
        if($('.Node-children').length > 1){
            scriptBody();
            clearInterval(intervalScriptBody);
            intervalScriptBody = null;
            if(!observer) {
                observer = new MutationObserver(function(mutations, observer) {
                    if(!intervalScriptBody) {
                        intervalScriptBody = setInterval(intervalBody, 3000);
                    }
                });

                // 定义变化元素
                observer.observe($('div.header-sync-state.mod-synced')[0], {
                    subtree: true,
                    attributes: true
                });
            }
        }
    }

    $(window).bind('hashchange', function() {
        if(!intervalScriptBody) {
            intervalScriptBody = setInterval(intervalBody, 3000);
        }
    });



});

function setCookie(cname,cvalue,exdays){
    var d = new Date();
    d.setTime(d.getTime()+(exdays*24*60*60*1000));
    var expires = "expires="+d.toGMTString();
    document.cookie = cname+"="+cvalue+"; "+expires;
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

function scriptBody(){
    console.log("dynalist toc");
    let scrollTop = $('#sideToolbar').scrollTop();
    $('#sideToolbarContainer').empty();

    // 需将主体宽度减小
    // $('.normal-view').css({left: '200px'});
    // $('.DocumentContainer').css({width: $('.DocumentContainer').width() - 200});

    var body = $('body'),
        sideToolbar = 'sideToolbar',
        sideCatalog = 'sideCatalog',
        catalog = 'sideCatalog-catalog',
        catalogBtn = 'sideCatalogBtn',
        sideToolbarUp = 'sideToolbar-up',
        i = '<div id="sideToolbar" style="display:block;">\<div class="sideCatalogBg" id="sideCatalog">\<div id="sideCatalog-catalog">\<ul class="nav"style="zoom:1">\</ul>\</div>\</div>\</div>',
        ulHtml = '',
        k = 0,
        l1 = 0,
        l2 = 0,
        l3 = 0,
        headers;
    if (body.length === 0) {
        return
    }
    $('#sideToolbarContainer').append(i);
    let lv1Selector = 'div.Document-rootNode > div > div.Node-children > div.Node-outer';
    let titleSelector = 'div.Node-self > div.node-line.Node-contentContainer > div.Node-renderedContent.node-line > span';
    let childrenSelector = 'div.Node-children > div.Node-outer > div';
    body.find(lv1Selector)
        .each(function (i) {
            l1++;
            l2=0;
            var headerContainer1 = $(this).children();
            var header1 = $(headerContainer1.find(titleSelector)[0]);
            var title1 = header1.text();
            var text1 = header1.text();
            header1.attr('id', 'autoid-' + l1 + '-' + l2 + '-' + l3);
            // if (text1.length > 14) text1 = text1.substr(0, 12) + "...";

            ulHtml += '<li><span>' + l1 + '&nbsp&nbsp</span><a class="head_a" id="a_' + header1.attr('id') + '" href="#' + header1.attr('id') + '" title="' + title1 + '">' + text1 + '</a><span class="sideCatalog-dot"></span></li>';

            $(headerContainer1.children()[1]).children()
                .each(function (i) {
                    l2++;
                    l3=0;
                    var headerContainer2 = $(this).children();
                    var header2 = $(headerContainer2.find(titleSelector)[0]);
                    var title2 = header2.text();
                    var text2 = header2.text();
                    header2.attr('id', 'autoid-' + l1 + '-' + l2 + '-' + l3);
                    // if (text2.length > 14) text2 = text2.substr(0, 12) + "...";
                    ulHtml += '<li class="h2Offset"><span>' + l1 + '.' + l2 + '&nbsp&nbsp</span><a class="head_a" id="a_' + header2.attr('id') + '" href="#' + header2.attr('id') + '" title="' + title2 + '">' + text2 + '</a></li>';

                    $(headerContainer2.children()[1]).children()
                        .each(function (i) {
                            l3++
                            var headerContainer3 = $(this).children();
                            var header3 = $(headerContainer3.find(titleSelector)[0]);
                            var title3 = header3.text();
                            var text3 = header3.text();
                            header3.attr('id', 'autoid-' + l1 + '-' + l2 + '-' + l3);
                            // if (text3.length > 14) text3 = text3.substr(0, 12) + "...";
                            ulHtml += '<li class="h3Offset"><span>' + l1 + '.' + l2 + '.' + l3 + '&nbsp&nbsp</span><a class="head_a" id="a_' + header3.attr('id') + '" href="#' + header3.attr('id') + '" title="' + title3 + '">' + text3 + '</a></li>';
                        });
                });
        });


    $('#' + catalog + '>ul').html(ulHtml);
    // 滚动当前位置
    // body.data('spy', 'scroll');
    // body.data('target', '.sideCatalogBg');
    // $('body').scrollspy({
    //     target: '.sideCatalogBg'
    // });


    // $('#' + sideToolbarUp).on('click', function () {
    //     $("html,body").animate({
    //         scrollTop: 0
    //     }, 500)
    // });
    // var $sideToolbar = $('#' + sideToolbar);
    // a.on('scroll', function () {
    //     var t = a.scrollTop();
    //     if (t > k) {
    //         $sideToolbar.css('display', 'block')
    //     } else {
    //         $sideToolbar.css('display', 'none')
    //     }
    // })


    // 可调整大小
    $("#barSplitterContainer").resizable({
        handleSelector: ".splitter",
        resizeHeight: false
    });

    // $("#sideCatalog-catalog").scrollspy();

    // 优化滚动
    $('.head_a').on('click', function (e) {
        e.preventDefault();
        let aHref = $(this).attr('href');
        let top = $(aHref).offset().top;
        // window.location.href = window.location.href + aHref;
        $('.DocumentContainer').scrollTop($('.DocumentContainer').scrollTop() + top - 100);
    })


    $('#sideToolbar').scrollTop(scrollTop);
};

function scrollSpy() {
    let $DocumentContainer = $('.DocumentContainer');
    $(function domReady($) {
        // 对所有内容
        $('div.Node-self > div.node-line.Node-contentContainer > div.Node-renderedContent.node-line > span').each(function eachElement() {
            // cache the jQuery object
            var $this = $(this);

            var position = $this.position();

            // window.console.log(position);
            // window.console.log('min: ' + position.top + ' / max: ' + window.parseInt(position.top + $this.height(), 10));

            $this.scrollspy({
                container: $DocumentContainer,
                min: position.top,
                max: position.top + $this.height(),
                onEnter: function onEnter(element/*, position*/) {
                    // window.console.log('Entering ' + element.id);

                    let href = $('#a_' + element.id);
                    let offset = href.parent().offset();
                    let scrollTop = $('#sideToolbar').scrollTop();
                    if(offset && offset.top) {
                        let top = offset.top;

                        if(top > 900){
                            $('#sideToolbar').scrollTop(top - 200);
                        }
                        if(top < 0){
                            $('#sideToolbar').scrollTop(scrollTop - 300);
                        }
                    }
                    href.parent().addClass('active');
                },
                onLeave: function onLeave(element/*, position*/) {
                    // window.console.log('Leaving ' + element.id);
                    $('li > a[href*="' + element.id + '"]').parent().removeClass('active');
                }
            });
        });
    });
}