package dmytro.kudriavtsev.repotring.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import dmytro.kudriavtsev.repotring.dtos.CountReportDTO;
import dmytro.kudriavtsev.repotring.dtos.ExchangeDTO;
import dmytro.kudriavtsev.repotring.dtos.ExchangeEventReportDTO;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ElasticService {

    @Autowired
    private RestHighLevelClient restHighLevelClient;

    public List<ExchangeDTO> getAll() throws IOException {
        MatchAllQueryBuilder matchAllQueryBuilder = QueryBuilders.matchAllQuery();

        return executeSearchQuery(matchAllQueryBuilder);
    }

    public List<ExchangeDTO> getExchangesReport(boolean success) throws IOException {
        MatchQueryBuilder matchQueryBuilder = QueryBuilders.matchQuery("success", String.valueOf(success));

        return executeSearchQuery(matchQueryBuilder);
    }

    public ExchangeEventReportDTO getReportByExchangeEvent() throws IOException {
        final String terms = "by_event";
        final String fieldName = "event.keyword";

        Terms byEvent = executeCountQuery(terms, fieldName);

        long sale = byEvent.getBucketByKey("SALE") == null ? 0 : byEvent.getBucketByKey("SALE").getDocCount();
        long purchase = byEvent.getBucketByKey("PURCHASE") == null ? 0 : byEvent.getBucketByKey("PURCHASE").getDocCount();

        return new ExchangeEventReportDTO(sale, purchase);
    }

    public CountReportDTO getCountReport() throws IOException {
        final String terms = "by_success";
        final String fieldName = "success";

        Terms bySuccess = executeCountQuery(terms, fieldName);

        long success = bySuccess.getBucketByKey("true") == null ? 0 : bySuccess.getBucketByKey("true").getDocCount();
        long failed = bySuccess.getBucketByKey("false") == null ? 0 : bySuccess.getBucketByKey("false").getDocCount();

        return new CountReportDTO(success, failed);
    }

    private <T extends QueryBuilder> List<ExchangeDTO> executeSearchQuery(T matchQueryBuilder) throws IOException {
        SearchRequest searchRequest = new SearchRequest("exchanges");
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.query(matchQueryBuilder);

        searchRequest.source(sourceBuilder);

        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        SearchHits hits = searchResponse.getHits();

        List<ExchangeDTO> exchanges = new ArrayList<>();

        ObjectMapper objectMapper = new ObjectMapper();

        SearchHit[] searchHits = hits.getHits();
        for (SearchHit hit : searchHits) {
            String sourceAsString = hit.getSourceAsString();
            ExchangeDTO exchangeDTO = objectMapper.readValue(sourceAsString, ExchangeDTO.class);
            exchanges.add(exchangeDTO);
        }

        return exchanges;
    }

    private Terms executeCountQuery(String terms, String fieldName) throws IOException {
        SearchRequest searchRequest = new SearchRequest("exchanges");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

        TermsAggregationBuilder aggregation = AggregationBuilders.terms(terms).field(fieldName);

        searchSourceBuilder.aggregation(aggregation);
        searchRequest.source(searchSourceBuilder);
        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);

        Aggregations aggregations = searchResponse.getAggregations();
        return aggregations.get(terms);
    }
}
