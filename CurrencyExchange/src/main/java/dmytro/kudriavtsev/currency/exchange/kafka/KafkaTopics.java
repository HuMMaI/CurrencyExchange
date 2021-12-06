package dmytro.kudriavtsev.currency.exchange.kafka;

public enum KafkaTopics {
    REPORTS("reports"),
    MAIL("mail"),
    ACTIVATION_MAIL("activation");

    private final String topicName;

    KafkaTopics(String topicName) {
        this.topicName = topicName;
    }

    public String getTopicName() {
        return topicName;
    }
}
