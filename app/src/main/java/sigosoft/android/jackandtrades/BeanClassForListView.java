package sigosoft.android.jackandtrades;

/**
 * Created by admin on 3/22/2016.
 */
public class BeanClassForListView {
int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private String image;
    private  String title;
    private String description;


    public BeanClassForListView(String image, String title, String description) {
        this.image = image;
        this.title = title;
        this.description = description;

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


}
