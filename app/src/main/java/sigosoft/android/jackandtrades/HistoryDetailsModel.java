package sigosoft.android.jackandtrades;

/**
 * Created by pooja on 5/10/2017.
 */
public class HistoryDetailsModel {
    int id;
    String product_name;

    public HistoryDetailsModel(String bill_id, String product_name, String qty, String total) {
        this.billId=bill_id;
        this.product_name=product_name;
        this.qty=qty;
        this.total=total;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getBillId() {
        return billId;
    }

    public void setBillId(String billId) {
        this.billId = billId;
    }

    String qty;
    String total;
    String billId;


}
