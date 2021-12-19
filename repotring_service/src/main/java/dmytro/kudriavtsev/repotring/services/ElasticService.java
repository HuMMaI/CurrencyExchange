package dmytro.kudriavtsev.repotring.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import dmytro.kudriavtsev.repotring.dtos.CountReportDTO;
import dmytro.kudriavtsev.repotring.dtos.ExchangeDTO;
import dmytro.kudriavtsev.repotring.dtos.ExchangeEventReportDTO;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.core.CountRequest;
import org.elasticsearch.client.core.CountResponse;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
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

    public CountReportDTO getCountReport() throws IOException {
        MatchQueryBuilder matchQueryBuilder = QueryBuilders.matchQuery("success", String.valueOf(true));
        Long successful = executeCountQuery(matchQueryBuilder);
        matchQueryBuilder = QueryBuilders.matchQuery("success", String.valueOf(false));
        Long failed = executeCountQuery(matchQueryBuilder);

        return new CountReportDTO(successful, failed);
    }

    public ExchangeEventReportDTO getReportByExchangeEvent() throws IOException {
        MatchQueryBuilder matchQueryBuilder = QueryBuilders.matchQuery("event", "SALE");
        Long sale = executeCountQuery(matchQueryBuilder);
        matchQueryBuilder = QueryBuilders.matchQuery("event", "PURCHASE");
        Long purchase = executeCountQuery(matchQueryBuilder);

        return new ExchangeEventReportDTO(sale, purchase);
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

    private Long executeCountQuery(QueryBuilder queryBuilder) throws IOException {
        CountRequest countRequest = new CountRequest("exchanges");
        countRequest.query(queryBuilder);

        CountResponse countResponse = restHighLevelClient.count(countRequest, RequestOptions.DEFAULT);

        return countResponse.getCount();
    }
}
