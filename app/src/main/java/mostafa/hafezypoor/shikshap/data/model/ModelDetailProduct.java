package mostafa.hafezypoor.shikshap.data.model;

public class ModelDetailProduct {
    private String id;
    private String product_name;
    private String product_description;
    private String product_price;
    private String group_id;

    public String getProduct_image() {
        return product_image;
    }

    public String getGroup_id() {
        return group_id;
    }

    public String getProduct_price() {
        return product_price;
    }

    public String getProduct_description() {
        return product_description;
    }

    public String getProduct_name() {
        return product_name;
    }

    public String getId() {
        return id;
    }

    private String product_image;
}
