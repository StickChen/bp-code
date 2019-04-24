$(function () {
    console.info("sendMsg")
    let href = window.location.href;
    if (href.indexOf("love.163.com") !== -1) {
        let btnHtml = '<div id="btnSend" style="position:fixed;top:10%;left:20%;width:100%;height:30px;line-height:30px;margin-top:-15px;cursor: pointer;z-index: 99999;">\n    <p style="background:#000;opacity:0.7;width:100px;color:#fff;text-align:center;padding:10px 10px;margin:0 auto;font-size:12px;border-radius:4px;">发送</p>\n</div>'
        $("body").append(btnHtml);
        let text1 = '嗨，你好~';
        let text2 = "有兴趣认识一下吗？";
        $('#btnSend').click(function () {
            // let $textfield = $('.publish-textarea>textarea');
            // if ($textfield.val() === text1) {
            //     $textfield.val(text2);
            // }else {
            //     $textfield.val(text1);
            // }
            let id = JSON.parse($('#data_currentUser').text()).id;
            sendMsg(encodeURI(text1), id).then(function(){
                sendMsg(encodeURI(text2), id)
            });
        })
    }else if (href.indexOf("jiayuan") !== -1) {
        let btnHtml = '<div id="btnSend" style="position:fixed;top:10%;left:20%;width:100%;height:30px;line-height:30px;margin-top:-15px;cursor: pointer;z-index: 99999;">\n    <p style="background:#000;opacity:0.7;width:100px;color:#fff;text-align:center;padding:10px 10px;margin:0 auto;font-size:12px;border-radius:4px;">填充</p>\n</div>'
        $("body").append(btnHtml);
        $('#btnSend').click(function () {
            $('#textfield').val("有兴趣认识一下吗？");
        })
    }
    // $.post(url, {}, function (res) {
    //     popTips(300, res);
    // });
})

function sendMsg(msg, id) {
    return fetch("https://love.163.com/messages/add", {
        "credentials" : "include",
        "headers" : {
            "accept" : "*/*",
            "accept-language" : "zh-CN,zh;q=0.9,zh-TW;q=0.8,en;q=0.7",
            "content-type" : "application/x-www-form-urlencoded; charset=UTF-8",
            "x-requested-with" : "XMLHttpRequest"
        },
        "referrer" : "https://love.163.com/",
        "referrerPolicy" : "no-referrer-when-downgrade",
        "body" : "withUserId="+id+"&content="+msg+"&isSetTop=1",
        "method" : "POST",
        "mode" : "cors"
    }).then(function (response) {return response.json();}).then(function (myJson) {popTips(200, JSON.stringify(myJson))});
}

function popTips(pWidth, content) {
    $("#msg").remove();
    var html = '<div id="msg" style="position:fixed;top:50%;width:100%;height:30px;line-height:30px;margin-top:-15px;">\n    <p style="background:#000;opacity:0.8;width:' + pWidth + 'px;color:#fff;text-align:center;padding:10px 10px;margin:0 auto;font-size:12px;border-radius:4px;">' + content + '</p>\n</div>'
    $("body").append(html);
    var t = setTimeout(next, 3000);

    function next() {
        $("#msg").remove();
    }
}