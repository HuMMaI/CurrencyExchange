package dmytro.kudriavtsev.sender.dtos;

public class MessageDTO<T> {
    private T data;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
