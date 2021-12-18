package dmytro.kudriavtsev.repotring.dtos;

public class ExchangeEventReportDTO {
    private Long sold;
    private Long purchase;

    public ExchangeEventReportDTO() {
    }

    public ExchangeEventReportDTO(Long sold, Long purchase) {
        this.sold = sold;
        this.purchase = purchase;
    }

    public Long getSold() {
        return sold;
    }

    public void setSold(Long sold) {
        this.sold = sold;
    }

    public Long getPurchase() {
        return purchase;
    }

    public void setPurchase(Long purchase) {
        this.purchase = purchase;
    }
}
