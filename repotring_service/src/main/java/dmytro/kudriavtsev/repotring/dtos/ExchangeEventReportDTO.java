package dmytro.kudriavtsev.repotring.dtos;

public class ExchangeEventReportDTO {
    private Long sale;
    private Long purchase;

    public ExchangeEventReportDTO() {
    }

    public ExchangeEventReportDTO(Long sale, Long purchase) {
        this.sale = sale;
        this.purchase = purchase;
    }

    public Long getSale() {
        return sale;
    }

    public void setSale(Long sale) {
        this.sale = sale;
    }

    public Long getPurchase() {
        return purchase;
    }

    public void setPurchase(Long purchase) {
        this.purchase = purchase;
    }
}
