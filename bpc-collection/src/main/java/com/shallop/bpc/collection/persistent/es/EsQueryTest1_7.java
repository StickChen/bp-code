package com.shallop.bpc.collection.persistent.es;

/**
 * @author StickChen
 * @date 2016/4/10
 */
public class EsQueryTest1_7 {

//	private static final Logger logger = LoggerFactory.getLogger(EsQueryTest1_7.class);
//
//	@Autowired
//	private TransportClient esClient;
//
//	private ObjectMapper mapper = new ObjectMapper();
//
//	@Test
//	public void testJoin() {
//		List<CfOperationRecord> cfOperationRecords = new ArrayList<CfOperationRecord>();
//		FilterBuilder filter = FilterBuilders.hasParentFilter("cf_plan", QueryBuilders.rangeQuery("amount").from(100).to(200000));
//		QueryBuilder query = QueryBuilders.termQuery("bizType", "loan");
//		List<Map<String, Object>> esData = new ArrayList<Map<String, Object>>();
//		InnerHitsBuilder.InnerHit innerHit = new InnerHitsBuilder.InnerHit();
//		innerHit.setType("cf_plan");
//		SearchResponse response = esClient.prepareSearch("financial_cf").setTypes("cf_operation_record").setQuery(query)
//				.setPostFilter(filter).setFrom(0).setSize(10000).addInnerHit("foo", innerHit).execute().actionGet();
//		for (SearchHit hit : response.getHits()) {
//			CfOperationRecord cfOperationRecord = mapper.convertValue(hit.sourceAsMap(), CfOperationRecord.class);
//			cfOperationRecords.add(cfOperationRecord);
//			esData.add(hit.sourceAsMap());
//			for (String key : hit.getInnerHits().keySet()) {
//				for (SearchHit hit1 : hit.getInnerHits().get(key).getHits()) {
//					CfPlan cfPlan = mapper.convertValue(hit1.sourceAsMap(), CfPlan.class);
//					logger.info("", cfPlan);
//				}
//			}
//		}
//	}
//
//	private class CfOperationRecord {
//	}
//
//	private class CfPlan {
//	}
}
