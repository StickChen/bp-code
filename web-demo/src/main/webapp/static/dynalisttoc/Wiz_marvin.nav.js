$(function () {

    let intervalScriptBody = setInterval(intervalBody, 3000);

    function intervalBody() {
        if($('.Node-children').length > 1){
            scriptBody();
            clearInterval(intervalScriptBody);
            intervalScriptBody = null;
        }
    }

    MutationObserver = window.MutationObserver || window.WebKitMutationObserver;

    var observer = new MutationObserver(function(mutations, observer) {
        if(!intervalScriptBody) {
            // intervalScriptBody = setInterval(intervalBody, 5000);
        }
    });

    // 定义变化元素
    observer.observe($('div.Document-rootNode')[0], {
        subtree: true,
        attributes: true
    });

});

function scriptBody(){
    console.log("dynalist toc");
    if($('#TocContainer').length === 0) {
        $('.normal-view').before('<div id="TocContainer"></div>');
    }else {
        $('#TocContainer').empty();
    }
    $('.normal-view').css({left: '200px'});

    var body = $('body'),
        sideToolbar = 'sideToolbar',
        sideCatalog = 'sideCatalog',
        catalog = 'sideCatalog-catalog',
        catalogBtn = 'sideCatalogBtn',
        sideToolbarUp = 'sideToolbar-up',
        i = '<div id="sideToolbar" style="display:block;">\<div class="sideCatalogBg" id="sideCatalog">\<div id="sideCatalog-sidebar">\<div class="sideCatalog-sidebar-top"></div>\<div class="sideCatalog-sidebar-bottom"></div>\</div>\<div id="sideCatalog-catalog">\<ul class="nav"style="zoom:1">\</ul>\</div>\</div>\<a href="javascript:void(0);" id="sideCatalogBtn" class="sideCatalogBtnDisable"></a>\</div>',
        ulHtml = '',
        k = 0,
        l1 = 0,
        l2 = 0,
        l3 = 0,
        headers;
    if (body.length === 0) {
        return
    }
    $('#TocContainer').append(i);
    let lv1Selector = 'div.Document-rootNode > div > div.Node-children > div.Node-outer';
    let titleSelector = 'div.Node-self > div.node-line.Node-contentContainer > div.Node-renderedContent.node-line > span';
    let childrenSelector = 'div.Node-children > div.Node-outer > div';
    body.find(lv1Selector)
        .each(function (i) {
            l1++;
            var headerContainer1 = $(this).children();
            var header1 = $(headerContainer1.find(titleSelector)[0]);
            var title1 = header1.text();
            var text1 = header1.text();
            header1.attr('id', 'autoid-' + l1 + '-' + l2 + '-' + l3);
            if (text1.length > 14) text1 = text1.substr(0, 12) + "...";

            ulHtml += '<li><span>' + l1 + '&nbsp&nbsp</span><a href="#' + header1.attr('id') + '" title="' + title1 + '">' + text1 + '</a><span class="sideCatalog-dot"></span></li>';

            $(headerContainer1.children()[1]).children()
                .each(function (i) {
                    l2++;
                    var headerContainer2 = $(this).children();
                    var header2 = $(headerContainer2.find(titleSelector)[0]);
                    var title2 = header2.text();
                    var text2 = header2.text();
                    header2.attr('id', 'autoid-' + l1 + '-' + l2 + '-' + l3);
                    if (text2.length > 14) text2 = text2.substr(0, 12) + "...";
                    ulHtml += '<li class="h2Offset"><span>' + l1 + '.' + l2 + '&nbsp&nbsp</span><a href="#' + header2.attr('id') + '" title="' + title2 + '">' + text2 + '</a></li>';

                    $(headerContainer2.children()[1]).children()
                        .each(function (i) {
                            l3++
                            var headerContainer3 = $(this).children();
                            var header3 = $(headerContainer3.find(titleSelector)[0]);
                            var title3 = header3.text();
                            var text3 = header3.text();
                            header3.attr('id', 'autoid-' + l1 + '-' + l2 + '-' + l3);
                            if (text3.length > 14) text3 = text3.substr(0, 12) + "...";
                            ulHtml += '<li class="h3Offset"><span>' + l1 + '.' + l2 + '.' + l3 + '&nbsp&nbsp</span><a href="#' + header3.attr('id') + '" title="' + title3 + '">' + text3 + '</a></li>';

                        });
                });
        });

    /*
    h1s.each(function (t) {
        var header = $(this),
            headerDom = header[0];

        var title = header.text();
        var text = header.text();

        header.attr('id', 'autoid-' + l1 + '-' + l2 + '-' + l3);
        //if (!u.attr('id')) {
        //    u.attr('id', 'autoid-' + l + '-' + m + '-' + n)
        //};
        if (headerDom.localName === 'h2') {
            l1++;
            l2 = 0;
            if (text.length > 14) text = text.substr(0, 12) + "...";
            ulHtml += '<li><span>' + l1 + '&nbsp&nbsp</span><a href="#' + header.attr('id') + '" title="' + title + '">' + text + '</a><span class="sideCatalog-dot"></span></li>';
        } else if (headerDom.localName === 'h3') {
            l2++;
            l3 = 0;
                if (text.length > 12) text = text.substr(0, 10) + "...";
                ulHtml += '<li class="h2Offset"><span>' + l1 + '.' + l2 + '&nbsp&nbsp</span><a href="#' + header.attr('id') + '" title="' + title + '">' + text + '</a></li>';
        } else if (headerDom.localName === 'h4') {
            l3++;
                ulHtml += '<li class="h3Offset"><span>' + l1 + '.' + l2 + '.' + l3 + '&nbsp&nbsp</span><a href="#' + header.attr('id') + '" title="' + title + '">' + header.text() + '</a></li>';
        }
    });
    */

    $('#' + catalog + '>ul').html(ulHtml);
    // 滚动当前位置
    body.data('spy', 'scroll');
    body.data('target', '.sideCatalogBg');
    // $('body').scrollspy({
    //     target: '.sideCatalogBg'
    // });

    // 显示隐藏目录
    $sideCatelog = $('#' + sideCatalog);
    $('#' + catalogBtn).on('click', function () {
        if ($(this).hasClass('sideCatalogBtnDisable')) {
            $sideCatelog.css('visibility', 'hidden')
        } else {
            $sideCatelog.css('visibility', 'visible')
        }
        $(this).toggleClass('sideCatalogBtnDisable')
    });
    $('#' + sideToolbarUp).on('click', function () {
        $("html,body").animate({
            scrollTop: 0
        }, 500)
    });
    // var $sideToolbar = $('#' + sideToolbar);
    // a.on('scroll', function () {
    //     var t = a.scrollTop();
    //     if (t > k) {
    //         $sideToolbar.css('display', 'block')
    //     } else {
    //         $sideToolbar.css('display', 'none')
    //     }
    // })

};