package dmytro.kudriavtsev.repotring.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import dmytro.kudriavtsev.repotring.dtos.ExchangeDTO;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.UUID;

@Service
public class ConsumerService {
    @Autowired
    private RestHighLevelClient restHighLevelClient;

    @KafkaListener(topics = "reports", groupId = "group_id")
    public void consume(@Payload String message) throws IOException {
        System.out.println(message);

        IndexRequest request = new IndexRequest("exchanges");
        request.id(UUID.randomUUID().toString());
        request.source(message, XContentType.JSON);

        IndexResponse indexResponse = restHighLevelClient.index(request, RequestOptions.DEFAULT);

        System.out.println("response id: " + indexResponse.getId());
        System.out.println("data: " + indexResponse.getResult().name());

//        SearchRequest searchRequest = new SearchRequest("exchanges");
//
//        MatchQueryBuilder matchQueryBuilder = QueryBuilders.matchQuery("email", "shining.melon2015@gmail.com");
//        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
//        sourceBuilder.query(matchQueryBuilder);
//
//        searchRequest.source(sourceBuilder);
//
//        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);


//        PutMappingRequest request = new PutMappingRequest("exchanges");
//        request.source(message, XContentType.JSON);
//
//        AcknowledgedResponse acknowledgedResponse = restHighLevelClient.indices().putMapping(request, RequestOptions.DEFAULT);
    }

}
