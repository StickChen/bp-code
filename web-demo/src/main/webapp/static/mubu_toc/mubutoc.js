
let ls = window.localStorage;
let href = window.location.href;
let key = 'sTop-' + href;
let initScroll = true;
let lastHeight = 0;
let nowHeight = 0;
let intervalScrollHistory;
let item = ls.getItem(key);
let levelKey = 'level-' + href;
let tocLevel = ls.getItem(levelKey);
if (!tocLevel) {
    tocLevel = '2';
}
// Node-content 是display:none，锚点需要重新定位
let titleSelectorPre = 'div.Node-self > div.node-line.Node-contentContainer';
let allTitleSelector = titleSelectorPre + ' > div.Node-content';
let allTitleRenderedSelector = titleSelectorPre + ' > div.Node-renderedContent.node-line';
let dyTree;

let intervalInit;
let hasInit = false;
let currDoc;
let jwtToken;
// ajax结果拦截器，得实时获取呀，这个只是一次性的
(function (open) {
    XMLHttpRequest.prototype.open = function (method, url, async, user, pass) {
        this.addEventListener("readystatechange", function () {
            if (this.readyState === 4) {
                // 在这可以获取到response数据，并且修改
                if (url.endsWith("/api/document/edit/get")) {
                    this._requestHeaders.forEach(function (item) {
                        if (item[0] === 'Jwt-Token') {
                            jwtToken = item[1]
                        }
                    })
                    currDoc = JSON.parse(JSON.parse(this.responseText).data.definition);
                }
            }
        }, false);
        open.call(this, method, url, async, user, pass);
    };
})(XMLHttpRequest.prototype.open);

// XMLHttpRequest.prototype.__open = XMLHttpRequest.prototype.open;
// XMLHttpRequest.prototype.open = function (method, url, async, user, password) {
//     // 用对象便于修改参数
//     let options = {
//         method: method,
//         url: url,
//         async: async,
//         user: user,
//         password: password
//     };
//     this.addEventListener("readystatechange", function () {
//         if (this.readyState === 4) {
//             // 在这可以获取到response数据，并且修改
//             if (url.endsWith("/api/document/edit/get")) {
//                 this._requestHeaders.forEach(function (item) {
//                     if (item[0] === 'Jwt-Token') {
//                         jwtToken = item[1]
//                     }
//                 })
//                 currDoc = JSON.parse(JSON.parse(this.responseText).data.definition);
//             }
//         }
//     }, false);
//     this.__open(options.method, options.url, options.async);
// };

// 加载延时
intervalInit = setInterval(function () {
    if ($('#js-app-left-panel').length > 0 && currDoc) {
        clearInterval(intervalInit);
        initToc();
    }
}, 500);



function initToc() {

    $('#root').before(`
    <!-- item template -->
    <script type="text/x-template" id="item-template">
      <li :class="'h'+level+'Offset'">
        <span class="toggle" v-if="isFolder" @click="toggle">{{ isOpen ? '&nbsp;-' : '+' }}</span>
        <span v-if="!isFolder">&nbsp;&nbsp;</span>
        <span class="sequence" @click="expandDescendant(item)">{{levelsStr}}</span>
            <a :class="'head_a level' + level" @click.prevent="scrollTo" :id='"a_" + item.id' :href="'#' + item.id" :title="item.name">{{ item.name }}</a>
        <ul v-show="isOpen" v-if="isFolder">
          <tree-item
            class="item"
            v-for="(child, index) in item.children"
            :tree-data="treeData"
            :key="index"
            :item="child"
            :level="curLevel"
            :sequence="index+1"
            :levels="levelArr"
          ></tree-item>
        </ul>
      </li>
    </script>
    
    <div id="TocContainer">
        <div id="barSplitterContainer" class="resizable">
            <div id="sideToolbarContainer">
                <div id="sideToolbar" style="display:block;">
                    <div class="sideCatalogBg" id="sideCatalog">
                        <div id="sideCatalog-catalog">
                            <!-- the demo root element -->
                            <ul id="demo">
                                <tree-item :key="index" v-for="(item,index) in treeData"
                                        class="item"
                                        :tree-data="treeData"
                                        :item="item"
                                        :level='1'
                                        :sequence='index+1'
                                        :levels="[]"
                                ></tree-item>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>
            <div class="splitter" style="touch-action: none;"></div>
        </div>
        <div id="sideCatalogRefreshFirstBtn">
    </div>
    <div id="sideCatalogRefreshSecondBtn"></div>
    <div id="sideCatalogRefreshBtn"></div>
    <div id="sideLocateBtn"></div>
    <a href="javascript:void(0);" id="sideCatalogBtn" class="sideCatalogBtnEnable"></a></div>
    `);



    // demo data
    var treeData = [
            { name: 'hello' },
            { name: 'wat' },
            {
                name: 'child folder',
                children: [
                    {
                        name: 'child folder',
                        children: [
                            { name: 'hello' },
                            { name: 'wat' }
                        ]
                    },
                    { name: 'hello' },
                    { name: 'wat' },
                    {
                        name: 'child folder',
                        children: [
                            { name: 'hello' },
                            { name: 'wat' }
                        ]
                    }
                ]
            }
        ];
    let treeData2 = [];
// define the tree-item component
    Vue.component('tree-item', {
        template: '#item-template',
        props: {
            item: Object,
            level: Number,
            sequence: Number,
            levels: Array,
            treeData: Array
        },
        data: function () {
            return {
                curLevel: this.level + 1,
                curExpandOneLvl: 1,
            }
        },
        computed: {
            isOpen: {
                get() {
                    return this.item.isOpen;
                },
                set(val){
                    // 直接更新也没有报错呢
                    this.item.isOpen = val;
                }
            },
            levelArr() {
                let curLevels = this.levels.slice();
                curLevels.push(this.sequence);
                return curLevels;
            },
            isFolder: function () {
                return this.item.children &&
                    this.item.children.length
            },
            levelsStr() {
                // if (this.levelArr.length < 4) {
                    let curLevels = '';
                    this.levelArr.forEach(function (ele) {
                        curLevels += ele + '.';
                    })
                    return curLevels.substr(0, curLevels.length - 1);
                // }
                // return this.sequence;
            },
        },
        created() {
        },
        methods: {
            scrollTo: function (prePos, count = 0) {
                if (count > 10) {
                    return
                }
                let $document = $('#js-doc-wrap');
                // 导航到指定位置
                let targetId = this.item.id;
                let offset = $(".outliner-node[data-id='" + targetId + "'").offset();
                let ctx = this;
                if (offset) {
                    let top = offset.top;
                    $document.scrollTop($document.scrollTop() + top - 100);
                    // 加亮
                    let $targetNode = $('#a_' + targetId);
                    $targetNode.addClass("currentLocation");
                    setTimeout(function () {
                        $targetNode.removeClass('currentLocation');
                    }, 3000);
                }else {
                    let idStr;
                    $(".outliner-node").each(function (i) {
                        let $this = $(this);
                        if($this.offset().top > 150) {
                            idStr = $this.attr('data-id');
                            return false
                        }
                    })
                    let pos = this.traverseNode(this.treeData, targetId, idStr);
                    if (pos === 'before' && prePos !== 'after') {
                        $document.scrollTop($document.scrollTop() - window.outerHeight * 1.5);
                        setTimeout(function () {
                            ctx.scrollTo(pos, count + 1)
                        }, 100);
                    }else if(pos === 'after' && prePos !== 'before'){
                        $document.scrollTop($document.scrollTop() + window.outerHeight * 1.5);
                        setTimeout(function () {
                            ctx.scrollTo(pos, count + 1)
                        }, 100);
                    }
                }
            },
            traverseNode: function(nodes, targetId, id){
                for (const node of nodes) {
                    if (node.id === targetId) {
                        return "before"
                    } else if(node.id === id){
                        return "after"
                    }
                    else if(node.children && node.children.length > 0){
                        let result = this.traverseNode(node.children, targetId, id)
                        if (result) {
                            return result
                        }
                    }
                }
            },
            expandDescendant: function (item) {
                if (!item.children || !item.children.length) {
                    return
                }
                let _this = this;
                if (item.isOpen) {
                    // 如果已经是展开的，说明需要往下展开
                    item.children.forEach(function (ele) {
                        _this.expandDescendant(ele);
                    })
                }else {
                    // 未展开则展开即可并结束
                    item.isOpen = true;
                }
            },
            toggle: function () {
                if (this.isFolder) {
                    this.isOpen = !this.isOpen;
                }
            },
            makeFolder: function () {
                if (!this.isFolder) {
                    this.$emit('make-folder', this.item)
                    this.isOpen = true
                }
            }
        }
    })

    // 创建Vue组件
    dyTree = new Vue({
        el: '#demo',
        data: {
            treeData: treeData2,
            currExpand: 1,
            tocLevel: tocLevel,
        },
        methods: {
            // 调整视图
            changeView: function(view){
                if (view) {
                    this.tocLevel = view;
                    ls.setItem(levelKey, tocLevel);
                }
                let _this = this;
                if (this.tocLevel === '1') {
                    this.expandToOne();
                }else if (this.tocLevel === '2') {
                    this.expandAllTo(this.treeData, this.currExpand, 1);
                }else if (this.tocLevel === '3') {
                    // 展开到当前节点下
                    $(".outliner-node").each(function (i) {
                        let $this = $(this);
                        if($this.offset().top > 150){
                            let idStr = $this.attr('data-id');
                            if(idStr) {
                                _this.expandToId(_this.treeData, idStr);
                                let $sideOl = $('#a_' + idStr);
                                if($sideOl.length > 0) {
                                    // 加亮
                                    $sideOl.addClass("currentLocation");
                                    setTimeout(function () {
                                        $sideOl.removeClass('currentLocation');
                                    }, 3000);
                                    let top = $sideOl.offset().top;
                                    $('#barSplitterContainer').scrollTop($('#barSplitterContainer').scrollTop() + top - 100);
                                    return false;
                                }
                            }
                        }
                    })
                }
            },
            expandToOne: function () {
                scriptBody();
                this.currExpand = 1;
                this.expandAllTo(this.treeData, 1, 1);
            },
            expandTo: function (nodes, levelArr, level){
                let currLevelArr = levelArr.slice();
                let shift = currLevelArr.shift();
                if (shift) {
                    nodes[shift-1].isOpen = true;
                    this.expandTo(nodes[shift-1].children, currLevelArr, level - 1);
                }
            },
            expandToId: function (nodes, id){
                for (const node of nodes) {
                    if (node.id === id) {
                        return true
                    }
                    if (node.children && node.children.length > 0) {
                        if (this.expandToId(node.children, id)) {
                            node.isOpen = true
                            return true
                        }
                    }
                }
            },
            expandAllTo: function(nodes, target, current){
                let currIsOpen = false;
                if (current >= target) {
                    currIsOpen = false;
                }else{
                    currIsOpen = true;
                }
                let _this = this;
                nodes.forEach(function (node) {
                    node.isOpen = currIsOpen;
                    if (node.children && node.children.length) {
                        _this.expandAllTo(node.children, target, current + 1);
                    }
                });
            },
            makeFolder: function (item) {
                Vue.set(item, 'children', [])
                this.addItem(item)
            },
            addItem: function (item) {
                item.children.push({
                    name: 'new stuff'
                })
            }
        }
    });

    // 通过cookie保存状态
    let sideToc = getCookie("sideToc");
    if(sideToc === "0") {
        $("#barSplitterContainer").addClass("cssVisibility");
    }else {
        $('#sideCatalogBtn').addClass('sideCatalogBtnEnable');
    }
    // 显示or隐藏目录
    $('#sideCatalogBtn').on('click', function () {
        if($("#barSplitterContainer").hasClass("cssVisibility")){
            setCookie("sideToc", "1", 30);
        }else {
            setCookie("sideToc", "0", 30);
        }
        $("#barSplitterContainer").toggleClass('cssVisibility')
        $(this).toggleClass('sideCatalogBtnEnable')
    });
    $('#sideCatalogRefreshFirstBtn').on('click', function () {
        tocLevel = "1";
        dyTree.changeView(tocLevel);
    })
    $('#sideCatalogRefreshSecondBtn').on('click', function () {
        tocLevel = "2";
        dyTree.currExpand++;
        dyTree.changeView(tocLevel);
    })
    $('#sideCatalogRefreshBtn').on('click', function () {
        tocLevel = "3";
        // 展开到当前节点
        dyTree.changeView(tocLevel);

        // 滚动跟随，太卡了，去掉吧
        // scrollSpy();
    })

    // 反向定位
    $('#sideLocateBtn').on('click', function (e) {
        console.log("sideLocateBtn click")
        $(".outliner-node").each(function (i) {
            let $this = $(this);
            if($this.offset().top > 150){
                let idStr = $this.attr('data-id');
                if(idStr) {
                    let $sideOl = $('#a_' + idStr);
                    if($sideOl.length > 0) {
                        let top = $sideOl.offset().top;
                        if (top > 0) {
                            // 根据id找到节点加状态
                            // 隐藏状态
                            $sideOl.addClass("currentLocation");
                            setTimeout(function () {
                                $sideOl.removeClass('currentLocation');
                            }, 3000);
                            $('#barSplitterContainer').scrollTop($('#barSplitterContainer').scrollTop() + top - 100);
                            return false;
                        }
                    }
                }
            }
        })
    });

    // 可调整大小
    $("#barSplitterContainer").resizable({
        handleSelector: ".splitter",
        resizeHeight: false
    });

    // 首次加载
    let intervalScriptBody = setInterval(intervalBody, 5000);

    // 自动刷新
    MutationObserver = window.MutationObserver || window.WebKitMutationObserver;
    let observer;

    //
    function intervalBody() {
        if($('.outliner-node').length > 1){
            scriptBody();
            // 首次定位到历史滚动位置
            if(initScroll === true) {
                intervalScrollHistory = setInterval(scrollHistory, 500);
            }
            clearInterval(intervalScriptBody);
            intervalScriptBody = null;
            if(!observer) {
                observer = new MutationObserver(function(mutations, observer) {
                    if(!intervalScriptBody) {
                        intervalScriptBody = setInterval(intervalBody, 60000);
                    }
                });

                // 如果同步状态有变化，触发刷新
                observer.observe($("[class*='style__wrap']")[0], {
                    subtree: true,
                    attributes: true,
                    childList: true
                });
            }
        }
    }

    $(window).bind('hashchange', function() {
        if(!intervalScriptBody) {
            intervalScriptBody = setInterval(intervalBody, 60000);
        }
    });
}



// ====================================================================================================

function setCookie(cname,cvalue,exdays){
    var d = new Date();
    d.setTime(d.getTime()+(exdays*24*60*60*1000));
    var expires = "expires=" + d.toGMTString();
    let cookie = cname+"="+cvalue+"; " + expires /*+ "; path=/"*/;
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

let lv1Selector = 'div.Document-rootNode > div > div.Node-children > div.Node-outer';
let titleSelector = '> ' + allTitleSelector;
let idNameMap ={};
let nameMap = {};
/**
 * 获取所有节点
 */
function scriptBody() {
    let treeData2 = [];
    let scrollTop = $('#sideToolbar').scrollTop();
    // 按名称，id缓存
    cacheTreeNode(dyTree.treeData, nameMap, idNameMap);

    // 页面懒加载节点了，没法获取到，改调用接口
    // let nodes = $('.outliner-node');
    // recursiveTree2(nodes, treeData2, 1, []);

    let url = window.location.href;
    let index = url.lastIndexOf("/") + 1;
    let lastIndex = url.indexOf("#");
    let length = lastIndex === -1 ? url.length - index:lastIndex-index
    let docId = url.substr(index, length);

    // 从接口获取数据
    $.ajax({
        type: 'POST',
        url: "//api2.mubu.com/v3/api/document/edit/get",
        dataType: 'json',
        contentType: 'application/json;charset=utf-8',
        data: JSON.stringify({docId: docId, password: ""}),
        headers: {'jwt-token': jwtToken},
        success: function(data){
            currDoc = JSON.parse(data.data.definition)
            recursiveTree3(currDoc.nodes, treeData2)

            recoveryStatus(treeData2, nameMap, idNameMap);

            dyTree.treeData = treeData2;

            $('#sideToolbar').scrollTop(scrollTop);
        }
    })
}

function recursiveTree3(nodes, treeData) {
    if (nodes && nodes.length > 0) {
        for (const node of nodes) {
            let children = []
            let name = node.text
            try {
                name = jQuery(node.text).text();
                name = name.trim() === '' ? node.text:name
            } catch (e) {
            }
            treeData.push({id: node.id, name: name, children: children,isOpen: false})
            if (node.children && node.children.length > 0) {
                recursiveTree3(node.children, children)
            }
        }
    }
}

function cacheTreeNode(nodes, nameMap, idNameMap) {
    nodes.forEach(function (node) {
        idNameMap[node.id+node.name] = node;
        nameMap[node.name] = node;
        if (node.children && node.children.length) {
            cacheTreeNode(node.children, nameMap, idNameMap);
        }
    })
}
function recoveryStatus(nodes, nameMap, idNameMap) {
    nodes.forEach(function (node) {
        if (idNameMap[node.id+node.name]) {
            node.isOpen = idNameMap[node.id+node.name].isOpen;
        }else if (nameMap[node.name]) {
            node.isOpen = nameMap[node.name].isOpen;
        }
        recoveryStatus(node.children, nameMap, idNameMap);
    })
}

// 它这个用了懒加载，没法获取全部的
function recursiveTree2(nodes, treeData, level, levelArr) {
    let parentArr = [];
    nodes.each(function () {
        let dataId = $(this).attr('data-id');
        let indent = $($(this).children()[0]).children().length;
        let title = $($(this).find('div.outliner-node-content-container > div.outliner-node-content > div.content > span')[0]).text();
        let node = {id: dataId, name: title, children: [],isOpen: false};
        if (indent === 0) {
            treeData.push(node);
        }else {
            parentArr[indent - 1] ? parentArr[indent - 1].children.push(node) : null
        }
        parentArr[indent] = node
    });
}

function recursiveTree(nodes, treeData, level, levelArr){
    if (level > 6) {
        // 大于6级没多大意义了
        return
    }
    let curIndex = 0;
    nodes.each(function () {
        let currLevelArr = levelArr.slice();
        let headerContainer = $(this).children();
        let header = $(headerContainer.find(titleSelector)[0]);
        // let title = htmlEncode(header.text());
        let title = header.text();
        if (title && title.trim().length > 0) {
            currLevelArr.push(++curIndex);
            let headId = 'autoid';
            currLevelArr.forEach(function (ele) {
                headId += '-' + ele;
            });
            // 给Node加ID，实现跳转
            $(header.next('.Node-renderedContent')[0]).attr('id', headId);
            let childrenNodes = $(headerContainer.children()[1]).children();
            let childrenData = [];
            treeData.push({id: headId, name: title, children: childrenData, isOpen: false})
            recursiveTree(childrenNodes, childrenData, level + 1, currLevelArr);
        }
    })
}

/**
 * 旧方法
 */
function scriptBodyBak(level){
    if(!level){
        level = tocLevel;
    }
    if(level === ""){
        level = "3";
    }
    let iTocLevel = parseInt(level);
    let scrollTop = $('#sideToolbar').scrollTop();
    $('#sideToolbarContainer').empty();

    // 需将主体宽度减小
    // $('.normal-view').css({left: '200px'});
    // $('.DocumentContainer').css({width: $('.DocumentContainer').width() - 200});

    var body = $('body'),
        catalog = 'sideCatalog-catalog',
        i = '<div id="sideToolbar" style="display:block;">\<div class="sideCatalogBg" id="sideCatalog">\<div id="sideCatalog-catalog">\<ul class="nav"style="zoom:1">\</ul>\</div>\</div>\</div>',
        ulHtml = '',
        l1 = 0,
        l2 = 0,
        l3 = 0;
    if (body.length === 0) {
        return
    }
    $('#sideToolbarContainer').append(i);
    let lv1Selector = 'div.Document-rootNode > div > div.Node-children > div.Node-outer';
    let titleSelector = '> ' + allTitleSelector;
    body.find(lv1Selector)
        .each(function (i) {
            var headerContainer1 = $(this).children();
            var header1 = $(headerContainer1.find(titleSelector)[0]);
            var title1 = htmlEncode(header1.text());
            var text1 = htmlEncode(header1.text());
            if((title1 && title1.trim().length > 0)/* || $(headerContainer1.children()[1]).children().length > 0*/){
                l1++;
                l2=0;
                let headId = 'autoid-' + l1 + '-' + l2 + '-' + l3;
                $(header1.next('.Node-renderedContent')[0]).attr('id', headId);
                // if (text1.length > 14) text1 = text1.substr(0, 12) + "...";
                ulHtml += '<li class="h1Offset"><span>' + l1 + '&nbsp&nbsp</span><a class="head_a level1" id="a_' + headId + '" href="#' + headId + '" title="' + title1 + '">' + text1 + '</a><span class="sideCatalog-dot"></span></li>';
                if(iTocLevel >= 2) {
                    $(headerContainer1.children()[1]).children()
                        .each(function (i) {
                            var headerContainer2 = $(this).children();
                            var header2 = $(headerContainer2.find(titleSelector)[0]);
                            var title2 = htmlEncode(header2.text());
                            var text2 = htmlEncode(header2.text());
                            if((title2 && title2.trim().length > 0)/* || $(headerContainer2.children()[1]).children().length > 0*/){
                                l2++;
                                l3 = 0;
                                let headId = 'autoid-' + l1 + '-' + l2 + '-' + l3;
                                $(header2.next('.Node-renderedContent')[0]).attr('id', headId);
                                // if (text2.length > 14) text2 = text2.substr(0, 12) + "...";
                                ulHtml += '<li class="h2Offset"><span>' + l1 + '.' + l2 + '&nbsp&nbsp</span><a class="head_a level2" id="a_' + headId + '" href="#' + headId + '" title="' + title2 + '">' + text2 + '</a></li>';
                                if (iTocLevel >= 3) {
                                    $(headerContainer2.children()[1]).children()
                                        .each(function (i) {
                                            var headerContainer3 = $(this).children();
                                            var header3 = $(headerContainer3.find(titleSelector)[0]);
                                            var title3 = htmlEncode(header3.text());
                                            var text3 = htmlEncode(header3.text());
                                            if((title3 && title3.trim().length > 0)/* || $(headerContainer3.children()[1]).children().length > 0*/){
                                                l3++
                                                let headId = 'autoid-' + l1 + '-' + l2 + '-' + l3;
                                                $(header3.next('.Node-renderedContent')[0]).attr('id', headId);
                                                // if (text3.length > 14) text3 = text3.substr(0, 12) + "...";
                                                ulHtml += '<li class="h3Offset"><span>' + l1 + '.' + l2 + '.' + l3 + '&nbsp&nbsp</span><a class="head_a level3" id="a_' + headId + '" href="#' + headId + '" title="' + title3 + '">' + text3 + '</a></li>';
                                            }
                                        });
                                }
                            }
                        });
                }
            }
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
        // window.location.href = window.location.href + aHref;
        let top = $(aHref).offset().top;
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


            $this.scrollspy({
                container: $DocumentContainer,
                min: position.top,
                max: position.top + $this.height(),
                onEnter: function onEnter(element/*, position*/) {

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
                    $('li > a[href*="' + element.id + '"]').parent().removeClass('active');
                }
            });
        });
    });
}


function scrollHistory() {
    // 页面每次加载的时候获取本地存储里面的值
    if(initScroll === false) {
        return;
    }
    nowHeight = $('.docMain')[0].scrollHeight;
    if(lastHeight === 0 || lastHeight !== nowHeight) {
        lastHeight = $('.docMain')[0].scrollHeight;
        return;
    }
    if (item) {
        // 获取到的值来设置页面滚动条的位置
        $('.DocumentContainer').scrollTop(item);
        window.setTimeout(function () {
            $('#sideLocateBtn').trigger('click');
        }, 500);
        clearInterval(intervalScrollHistory);
    }
    initScroll = false;
}

// 监听页面滚动条的状态（是否滚动）
let scrollSaveInterval;
$('.DocumentContainer').scroll(function() {
    if (!scrollSaveInterval) {
        setInterval(function () {
            // 滚动时获取页面滚动条的位置
            var sTop = $('.DocumentContainer').scrollTop();
            // 滚动条的位置保存到本地存储里面
            ls.setItem(key, sTop);
            scrollSaveInterval = null;
        }, 1500);
    }
});

function htmlEncode(str) {
    var div = document.createElement("div");
    div.appendChild(document.createTextNode(str));
    return div.innerHTML;
}
function htmlDecode(str) {
    var div = document.createElement("div");
    div.innerHTML = str;
    return div.innerHTML;
}
