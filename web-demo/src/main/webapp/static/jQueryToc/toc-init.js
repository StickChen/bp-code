
var verboseIdCache = {};
$(function () {
    $('.toc').remove();
    $('.toc-btn').remove();
    $('body').prepend('<div class="toc"></div>');
    $('.toc').toc({
        'selectors': 'h1,h2,h3,h4,h5,h6', //elements to use as headings
        'headerText': function(i, heading, $heading) {



            return $heading.text();
            // var candidateId = $(heading).text().replace(/[^a-z0-9]/ig, ' ').replace(/\s+/g, '-').toLowerCase();
            // console.info("candidateId:" + candidateId);
            // if (verboseIdCache[candidateId]) {
            //     var j = 2;
            //
            //     while(verboseIdCache[candidateId + j]) {
            //         j++;
            //     }
            //     candidateId = candidateId + '-' + j;
            //
            // }
            // verboseIdCache[candidateId] = true;
            //
            // return candidateId + $heading.text();
        }
    });
    $('.toc').before('<div class="toc-btn"></div>');
    $('.toc-btn').on('click', function () {
        $('.toc').toggleClass('hidden');
    });
})