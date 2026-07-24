package mostafa.hafezypoor.shikshap.data.model;

public class FHomeModelProduct {
    private String id;
    private String product_name;
    private String product_description;

    public FHomeModelProduct(String id, String product_name, String product_description, String product_price, String group_id, String product_image, String topShow) {
        this.id = id;
        this.product_name = product_name;
        this.product_description = product_description;
        this.product_price = product_price;
        this.group_id = group_id;
        this.product_image = product_image;
        this.topShow = topShow;
    }

    private String product_price;
    private String group_id;
    private String product_image;
    private String topShow;

    public String getId() {
        return id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public String getProduct_description() {
        return product_description;
    }

    public String getProduct_price() {
        return product_price;
    }

    public String getGroup_id() {
        return group_id;
    }

    public String getProduct_image() {
        return product_image;
    }

    public String getTopShow() {
        return topShow;
    }
}
