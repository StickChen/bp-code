package com.shallop.bpc.collection.persistent.es;

/**
 * @author StickChen
 * @date 2016/4/10
 */
public class ESApiDemo1_7 {

//    private Client client;
//    private SearchRequestBuilder srb;
//    private QueryBuilder qb;
//
//    // 适合精确匹配
//    @Test
//    public void testFilter(){
//
//        p(hasChildFilter("blog_tag", termFilter("tag", "something")));
//        p(hasParentFilter("blog", termFilter("tag", "something")));
//
//        p(nestedFilter("obj1", boolFilter().must(termFilter("obj1.name", "blue")).must(rangeFilter("obj1.count").gt(5))));
//    }
//
//    // 适合全文搜索
//    @Test
//    public void testQuery(){
//
//        p(matchAllQuery());
//        p(matchQuery("name", "john"));
//        p(matchQuery("name", 1));
//        p(multiMatchQuery("john", "name", "candidate"));
//
//
//
//        // 布尔
//		p(boolQuery().must(termQuery("content", "test1")).must(termQuery("content", "test4"))
//				.mustNot(termQuery("content", "test2")).should(termQuery("content", "test3")));
//
//        // 升降权重
//        p(boostingQuery()
//                .positive(termQuery("name", "kimchy"))
//                .negative(termQuery("name", "dadoonet"))
//                .negativeBoost(0.2f));
//
//        // 多值匹配
//        p(termsQuery("name", "john", "lucy"));
//
//        // ids
//        idsQuery().ids("1", "2");
//
//        // Has Child
//		hasChildQuery("blog_tag", termQuery("tag", "something"));
//
//		hasParentQuery("blog", termQuery("tag", "something"));
//
//		prefixQuery("brand", "heine");
//
//        queryStringQuery("+kimchy -elasticsearch");
//
//
//		rangeQuery("price").from(5).to(10).includeLower(true).includeUpper(false);
//
//        // term精确查询未分词 match全文查询已分词
//        p(termQuery("name", "john"));
//
//        // 未分词的模糊查询
//        wildcardQuery("user", "k?mc*");
//
//        //
//		nestedQuery("obj1", boolQuery().must(matchQuery("obj1.name", "blue")).must(rangeQuery("obj1.count").gt(5))).scoreMode(
//				"avg");
//    }
//
//    void p(Object o) {
//        System.out.println(o);
//    }
}
