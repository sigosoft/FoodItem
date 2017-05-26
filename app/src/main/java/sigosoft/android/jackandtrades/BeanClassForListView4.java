package sigosoft.android.jackandtrades;

/**
 * Created by admin on 3/22/2016.
 */
public class BeanClassForListView4 {

    private String image;
    private  String title;
    private String description;
    private String id;
    private String qty;
    private String cart_id;
    private String unitprice;

    public String getOutletID() {
        return outletID;
    }

    public void setOutletID(String outletID) {
        this.outletID = outletID;
    }

    private String outletID;


    public BeanClassForListView4(String image, String name, String price, String id) {
        this.image=image;
        this.title=name;
        this.description=price;
        this.id=id;

    }

    public BeanClassForListView4() {

    }

    public BeanClassForListView4(String id, String title, String image, String description, String qty, String unit_price,String outletID) {
        this.id=id;
        this.title=title;
        this.image=image;
        this.description=description;
        this.qty=qty;
        this.unitprice=unit_price;
        this.outletID=outletID;

    }

    public BeanClassForListView4(String qty, String pr,String id) {
        this.qty=qty;
        this.description=pr;
        this.id=id;
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

    public String getUnitPrice(){
        return unitprice;
    }
    public void setUnitprice(String unitp) {this.unitprice=unitp;}

    public void setQty(String qty) {this.qty=qty;}

    public String getCart_id(){return cart_id;}

    public void setCart_id(String cart_id) {this.cart_id=cart_id;}


}
