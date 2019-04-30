$(function () {
    console.info("sendMsg")
    let href = window.location.href;
    let text1 = '嗨，你好~';
    let text2 = "有兴趣认识一下吗？";
    let btnHtml = '<div id="btnSend" style="position:fixed;top:10%;left:20%;width:100%;height:30px;line-height:30px;margin-top:-15px;cursor: pointer;z-index: 99999;">\n    <p style="background:#000;opacity:0.7;width:100px;color:#fff;text-align:center;padding:10px 10px;margin:0 auto;font-size:12px;border-radius:4px;">发送</p>\n</div>'
    if (href.indexOf("love.163.com") !== -1) {
        $("body").append(btnHtml);
        $('#btnSend').click(function () {
            let id = JSON.parse($('#data_currentUser').text()).id;
            sendMsgHT(encodeURI(text1), id).then(function(){
                return sendMsgHT(encodeURI(text2), id)
            }).then(function () {
                return updateSended(id);
            });
            // updateSended(id);
        })
    }else if (href.indexOf("jiayuan") !== -1) {
        $("body").append(btnHtml);
        $('#btnSend').click(function () {
            $('#textfield').val(text2);
            let href = $("div.ems_bg.col_blue > a").attr("href");
            let start = href.indexOf("uid_hash=");
            let to_hash = href.substring(start + 9, start + 9 + 32);
            let idText = $('div.member_box > div.member_info_r.yh > h4 > span').text();
            let id = idText.substring(3, idText.length);
            sendMsgJY(encodeURI(text1), to_hash).then(function(){
                setTimeout(next, 300);
                function next() {
                    sendMsgJY(encodeURI(text2), to_hash).then(function () {
                        updateSended(id);
                    })
                }
            });
        })
    }
    // $.post(url, {}, function (res) {
    //     popTips(300, res);
    // });
})

function updateSended(id) {
    let queryParam = {
        "HuaTian":{
            "outId":id
        }
    }
    return fetch("https://www.longxuanme.com/ext/api/get", {
        "credentials": "include",
        "headers": {
            "Accept": "application/json, text/plain, */*",
            "Accept-Language": "zh-CN,zh;q=0.9,zh-TW;q=0.8,en;q=0.7",
            "Content-Type": "application/json;charset=UTF-8"
        },
        "body": JSON.stringify(queryParam),
        "method": "POST",
        "mode": "cors"
    }).then(function (rsp) {
        return rsp.json();
    }).then(function (rspJson) {
        let param = {"HuaTian":{"id":rspJson.HuaTian.id,"send":true},"tag":"HuaTian"};
        return fetch("https://www.longxuanme.com/ext/api/put", {
            "credentials": "include",
            "headers": {
                "Accept": "application/json, text/plain, */*",
                "Accept-Language": "zh-CN,zh;q=0.9,zh-TW;q=0.8,en;q=0.7",
                "Content-Type": "application/json;charset=UTF-8"
            },
            "body": JSON.stringify(param),
            "method": "POST",
            "mode": "cors"
        });
    }).then(function (response) {return response.json();}).then(function (myJson) {popTips(200, myJson.code + "::" + myJson.msg)});
}

function sendMsgHT(msg, id) {
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
    }).then(function (response) {return response.json();}).then(function (myJson) {popTips(200, JSON.stringify(myJson.richContent))});
}

function sendMsgJY(msg, id) {
    return fetch("http://www.jiayuan.com/msg/dosend.php?type=hello&randomfrom=0", {
        "credentials" : "include",
        "headers" : {
            "accept" : "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8",
            "accept-language" : "zh-CN,zh;q=0.9,zh-TW;q=0.8,en;q=0.7",
            "cache-control" : "max-age=0",
            "content-type" : "application/x-www-form-urlencoded",
            "upgrade-insecure-requests" : "1"
        },
        "referrer" : "http://www.jiayuan.com/msg/hello.php?type=20&fxly=cp_yfq&src=none&cache_key=&uhash=d461c05bf47317f63f4adf59e0bae39e&cnj=profile2",
        "referrerPolicy" : "no-referrer-when-downgrade",
        "body" : "textfield="+msg+"&list=&category=5&xitong_zidingyi_wenhouyu=1&xitong_zidingyi_wenhouyu=0&new_type=1&pre_url=&sendtype=20&hellotype=hello&pro_id=0&new_profile=3&to_hash="+id+"&fxly=cp_yfq&tj_wz=none&need_fxtyp_tanchu=0&self_pay=0&fxbc=0&cai_xin=0&zhuanti=0&liwu_nofree=0&liwu_nofree_id=88",
        "method" : "POST",
        "mode" : "cors"
    }).then(function (response) {popTips(200, decodeURI(msg))});
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