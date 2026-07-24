package mostafa.hafezypoor.shikshap.data.model;

public class ModelSizes {
    private String id;
    private String size;
    private String inCart;
    private boolean selected;

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public String getId() {
        return id;
    }

    public String getSize() {
        return size;
    }

    public String getInCart() {
        return inCart;
    }
}
