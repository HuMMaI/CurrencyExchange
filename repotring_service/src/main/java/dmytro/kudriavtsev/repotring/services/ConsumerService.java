package dmytro.kudriavtsev.repotring.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import dmytro.kudriavtsev.repotring.dtos.MessageDTO;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
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
        MessageDTO messageDTO = new ObjectMapper().readValue(message, MessageDTO.class);
        IndexRequest request = new IndexRequest("exchanges");
        request.id(UUID.randomUUID().toString());
        request.source(new ObjectMapper().writeValueAsString(messageDTO.getData()), XContentType.JSON);

        restHighLevelClient.index(request, RequestOptions.DEFAULT);
    }

}
