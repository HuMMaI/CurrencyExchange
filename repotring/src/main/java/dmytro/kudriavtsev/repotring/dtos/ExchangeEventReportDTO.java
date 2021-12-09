package dmytro.kudriavtsev.repotring.dtos;

public class ExchangeEventReportDTO {
    private Integer sold;
    private Integer purchase;

    public ExchangeEventReportDTO() {
    }

    public ExchangeEventReportDTO(Integer sold, Integer purchase) {
        this.sold = sold;
        this.purchase = purchase;
    }

    public Integer getSold() {
        return sold;
    }

    public void setSold(Integer sold) {
        this.sold = sold;
    }

    public Integer getPurchase() {
        return purchase;
    }

    public void setPurchase(Integer purchase) {
        this.purchase = purchase;
    }
}
