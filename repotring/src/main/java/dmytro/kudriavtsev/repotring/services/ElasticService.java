package dmytro.kudriavtsev.repotring.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import dmytro.kudriavtsev.repotring.dtos.ExchangeDTO;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.client.indices.GetIndexResponse;
import org.elasticsearch.index.query.MatchQueryBuilder;
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
        SearchRequest searchRequest = new SearchRequest("exchanges");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchAllQuery());
        searchRequest.source(searchSourceBuilder);

        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        SearchHits hits = searchResponse.getHits();

        List<ExchangeDTO> exchangeDTOList = new ArrayList<>();

        ObjectMapper objectMapper = new ObjectMapper();

        SearchHit[] searchHits = hits.getHits();
        for (SearchHit hit : searchHits) {
            String sourceAsString = hit.getSourceAsString();
            ExchangeDTO exchangeDTO = objectMapper.readValue(sourceAsString, ExchangeDTO.class);
            exchangeDTOList.add(exchangeDTO);
        }

        return exchangeDTOList;

//        SearchRequest searchRequest = new SearchRequest("exchanges");
//
//        MatchQueryBuilder matchQueryBuilder = QueryBuilders.matchQuery("email", "shining.melon2015@gmail.com");
//        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
//        sourceBuilder.query(matchQueryBuilder);
//
//        searchRequest.source(sourceBuilder);
//
//        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
    }

    public List<ExchangeDTO> getSuccessfulExchanges() throws IOException {
        SearchRequest searchRequest = new SearchRequest("exchanges");

        MatchQueryBuilder matchQueryBuilder = QueryBuilders.matchQuery("success", "true");
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.query(matchQueryBuilder);

        searchRequest.source(sourceBuilder);

        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        SearchHits hits = searchResponse.getHits();

        List<ExchangeDTO> exchangeDTOList = new ArrayList<>();

        ObjectMapper objectMapper = new ObjectMapper();

        SearchHit[] searchHits = hits.getHits();
        for (SearchHit hit : searchHits) {
            String sourceAsString = hit.getSourceAsString();
            ExchangeDTO exchangeDTO = objectMapper.readValue(sourceAsString, ExchangeDTO.class);
            exchangeDTOList.add(exchangeDTO);
        }

        return exchangeDTOList;
    }

    public List<ExchangeDTO> getUnsuccessfulExchanges() throws IOException {
        SearchRequest searchRequest = new SearchRequest("exchanges");

        MatchQueryBuilder matchQueryBuilder = QueryBuilders.matchQuery("success", "false");
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.query(matchQueryBuilder);

        searchRequest.source(sourceBuilder);

        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        SearchHits hits = searchResponse.getHits();

        List<ExchangeDTO> exchangeDTOList = new ArrayList<>();

        ObjectMapper objectMapper = new ObjectMapper();

        SearchHit[] searchHits = hits.getHits();
        for (SearchHit hit : searchHits) {
            String sourceAsString = hit.getSourceAsString();
            ExchangeDTO exchangeDTO = objectMapper.readValue(sourceAsString, ExchangeDTO.class);
            exchangeDTOList.add(exchangeDTO);
        }

        return exchangeDTOList;
    }
}
