package dmytro.kudriavtsev.repotring.dtos;

public class CountReportDTO {
    private Long successful;
    private Long failed;

    public CountReportDTO() {
    }

    public CountReportDTO(Long successful, Long failed) {
        this.successful = successful;
        this.failed = failed;
    }

    public Long getSuccessful() {
        return successful;
    }

    public void setSuccessful(Long successful) {
        this.successful = successful;
    }

    public Long getFailed() {
        return failed;
    }

    public void setFailed(Long failed) {
        this.failed = failed;
    }
}
