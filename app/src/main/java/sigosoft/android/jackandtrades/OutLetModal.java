package sigosoft.android.jackandtrades;

/**
 * Created by pooja on 3/28/2017.
 */

public class OutLetModal {
    int outLet_id;
    String location;
    String outLet_name;
    String image;

    public OutLetModal(int outLet_id, String outLet_name, String location, String image) {
        this.outLet_id=outLet_id;
        this.outLet_name=outLet_name;
        this.location=location;
        this.image=image;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }




    public int getOutLet_id() {
        return outLet_id;
    }

    public String getLocation() {
        return location;
    }
//

    public String getOutLet_name() {
        return outLet_name;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setOutLet_id(int outLet_id) {
        this.outLet_id = outLet_id;
    }

    public void setOutLet_name(String outLet_name) {
       this.outLet_name = outLet_name;
  }
//
//
//
// public OutLetModal(int outLet_id,String outLet_name,String location){
//        this. outLet_id=outLet_id;
//        this.outLet_name=outLet_name;
//        this.location=location;
//    }
}
