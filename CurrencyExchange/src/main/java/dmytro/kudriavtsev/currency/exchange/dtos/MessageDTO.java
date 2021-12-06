package dmytro.kudriavtsev.currency.exchange.dtos;

public class MessageDTO<T> {
    private T data;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
