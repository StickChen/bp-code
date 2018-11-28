(function () {
    o = s.find(':header');
    if (o.length > p) {
        r = false;
        var t = s.find('h2');
        var u = s.find('h3');
        if (t.length + u.length > p) {
            q = false;//如果目录数目超过制定的值，只显示更高一级的目录
        }
    };
    o.each(function (t) {
        var u = $(this),
            v = u[0];

        var title=u.text();
        var text=u.text();

        u.attr('id', 'autoid-' + l + '-' + m + '-' + n)

        if (v.localName === 'h2') {
            l++;
            m = 0;
            if(text.length>14) text=text.substr(0,12)+"...";
            j += '<li><span>' + l + '&nbsp&nbsp</span><a href="#' + u.attr('id') + '" title="' + title + '">' + text + '</a><span class="sideCatalog-dot"></span></li>';
        } else if (v.localName === 'h3') {
            m++;
            n = 0;
            if(q){
                if(text.length>12) text=text.substr(0,10)+"...";
                j += '<li class="h2Offset"><span>' + l + '.' + m + '&nbsp&nbsp</span><a href="#' + u.attr('id') + '" title="' + title + '">' + text + '</a></li>';
            }
        } else if (v.localName === 'h4') {
            n++;
            if(r){
                j += '<li class="h3Offset"><span>' + l + '.' + m + '.' + n + '&nbsp&nbsp</span><a href="#' + u.attr('id') + '" title="' + title + '">' + u.text() + '</a></li>';
            }
        }
    });
})