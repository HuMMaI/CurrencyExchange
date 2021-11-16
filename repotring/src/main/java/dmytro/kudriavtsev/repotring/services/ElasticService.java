package dmytro.kudriavtsev.repotring.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import dmytro.kudriavtsev.repotring.dtos.ExchangeDTO;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.MatchAllQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
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

        return executeQuery(matchAllQueryBuilder);
    }

    public List<ExchangeDTO> getExchangesReport(boolean success) throws IOException {
        MatchQueryBuilder matchQueryBuilder = QueryBuilders.matchQuery("success", String.valueOf(success));

        return executeQuery(matchQueryBuilder);
    }

    private <T extends QueryBuilder> List<ExchangeDTO> executeQuery(T matchQueryBuilder) throws IOException {
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
}
