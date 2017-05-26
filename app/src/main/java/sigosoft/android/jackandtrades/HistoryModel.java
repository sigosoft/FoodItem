package sigosoft.android.jackandtrades;

/**
 * Created by pooja on 5/3/2017.
 */

public class HistoryModel {
    private String image;
    private  String title;
    private String description;
    private String id;
    private String qty;
    private String cart_id;


    public HistoryModel(String id, String title, String image, String description, String qty) {
        this.id=id;
        this.title=title;
        this.image=image;
        this.description=description;
        this.qty=qty;


    }

    public HistoryModel() {

    }

    public HistoryModel(String cart_id, String title, String price) {
        this.cart_id=cart_id;
        this.title=title;
        this.description=price;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getQty(){return qty;}

    public void setQty(String qty) {this.qty=qty;}

    public String getCart_id(){return cart_id;}

    public void setCart_id(String cart_id) {this.cart_id=cart_id;}



}
