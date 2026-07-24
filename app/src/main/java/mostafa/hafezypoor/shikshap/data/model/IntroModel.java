package mostafa.hafezypoor.shikshap.data.model;

public class IntroModel {
    private int animation;
    private String title;
    private String description;

    public IntroModel(int animation, String title, String description) {
        this.animation = animation;
        this.title = title;
        this.description = description;
    }

    public int getAnimation() {
        return animation;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }
}
