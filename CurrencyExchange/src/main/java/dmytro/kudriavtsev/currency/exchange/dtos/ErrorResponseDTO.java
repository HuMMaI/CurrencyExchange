package dmytro.kudriavtsev.currency.exchange.dtos;

public class ErrorResponseDTO {
    private String status;
    private int statusCode;
    private String message;
    private String path;

    public ErrorResponseDTO() {
    }

    public ErrorResponseDTO(String status, int statusCode, String message, String path) {
        this.status = status;
        this.statusCode = statusCode;
        this.message = message;
        this.path = path;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
