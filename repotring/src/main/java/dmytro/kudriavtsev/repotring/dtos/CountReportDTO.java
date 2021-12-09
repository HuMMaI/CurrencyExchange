package dmytro.kudriavtsev.repotring.dtos;

public class CountReportDTO {
    private int successful;
    private int failed;

    public CountReportDTO() {
    }

    public CountReportDTO(int successful, int failed) {
        this.successful = successful;
        this.failed = failed;
    }

    public int getSuccessful() {
        return successful;
    }

    public void setSuccessful(int successful) {
        this.successful = successful;
    }

    public int getFailed() {
        return failed;
    }

    public void setFailed(int failed) {
        this.failed = failed;
    }
}
