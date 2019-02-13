// ==UserScript==
// @name         DynalistScroll-All
// @namespace    http://longxuanme.com/
// @version      0.1
// @description  mobile dynalist margin
// @author       StickChen
// @match        https://dynalist.io/d/*
// @require      http://cdn.bootcss.com/jquery/3.2.1/jquery.min.js
//== @resource     css file:\\D:\WorkProject\!CXL\bp-code\web-demo\src\main\webapp\static\dynalistScroll\dynalistScroll.css
//== @resource     css https://gitcdn.xyz/repo/StickChen/bp-code/master/web-demo/src/main/webapp/static/dynalistScroll/dynalistScroll.css
//== @require     file:\\D:\WorkProject\!CXL\bp-code\web-demo\src\main\webapp\static\dynalistScroll\dynalistScroll.js
//== @require     https://gitcdn.xyz/repo/StickChen/bp-code/master/web-demo/src/main/webapp/static/dynalistScroll/dynalistScroll.js
// @grant        GM_addStyle
// @grant        GM_getResourceText
// ==/UserScript==

(function() {
    'use strict';
    GM_addStyle(GM_getResourceText('css'))
    // Your code here...

    $(function () {
        console.info("dynalistScroll");
        $('.main-container').prepend('<div id="dynalistScroll" style="position: absolute; height: 100%; width: 0; margin-top: 20px;"><div id="dynalistScrollInner" style="position: absolute;height: 100%; width: 20px;"></div></div>');
    });
})();