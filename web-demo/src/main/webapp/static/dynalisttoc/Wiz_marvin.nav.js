var a = $(document);
a.ready(function () {
    console.log("ul init");
    var body = $('body'),
        sideToolbar = 'sideToolbar',
        sideCatalog = 'sideCatalog',
        catalog = 'sideCatalog-catalog',
        catalogBtn = 'sideCatalogBtn',
        sideToolbarUp = 'sideToolbar-up',
        i = '<div id="sideToolbar"style="display:block;">\<div class="sideCatalogBg"id="sideCatalog">\<div id="sideCatalog-sidebar">\<div class="sideCatalog-sidebar-top"></div>\<div class="sideCatalog-sidebar-bottom"></div>\</div>\<div id="sideCatalog-catalog">\<ul class="nav"style="width:225px;zoom:1">\</ul>\</div>\</div>\<a href="javascript:void(0);"id="sideCatalogBtn"class="sideCatalogBtnDisable"></a>\</div>',
        ulHtml = '',
        k = 0,
        l1 = 0,
        l2 = 0,
        l3 = 0,
        headers, p = 13,
        q = true,
        r = true;
    if (body.length === 0) {
        return
    }
    body.append(i);
    headers = body.find(':header');
    if (headers.length > p) {
        r = false;
        var $h2 = body.find('h2');
        var $h3 = body.find('h3');
        if ($h2.length + $h3.length > p) {
            q = false;  // 如果目录数目超过制定的值，只显示更高一级的目录
        }
    }
    headers.each(function (t) {
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
            if (q) {
                if (text.length > 12) text = text.substr(0, 10) + "...";
                ulHtml += '<li class="h2Offset"><span>' + l1 + '.' + l2 + '&nbsp&nbsp</span><a href="#' + header.attr('id') + '" title="' + title + '">' + text + '</a></li>';
            }
        } else if (headerDom.localName === 'h4') {
            l3++;
            if (r) {
                ulHtml += '<li class="h3Offset"><span>' + l1 + '.' + l2 + '.' + l3 + '&nbsp&nbsp</span><a href="#' + header.attr('id') + '" title="' + title + '">' + header.text() + '</a></li>';
            }
        }
    });
    $('#' + catalog + '>ul').html(ulHtml);
    body.data('spy', 'scroll');
    body.data('target', '.sideCatalogBg');
    // $('body').scrollspy({
    //     target: '.sideCatalogBg'
    // });
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
});