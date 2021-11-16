package dmytro.kudriavtsev.currency.exchange.kafka;

public enum KafkaTopics {
    REPORTS("reports"),
    MAIL("mail");

    private String topicName;

    KafkaTopics(String topicName) {
        this.topicName = topicName;
    }

    public String getTopicName() {
        return topicName;
    }
}
