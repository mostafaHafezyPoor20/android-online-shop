package mostafa.hafezypoor.shikshap.data.model;

public class ModelChat {
    private String id;
    private String sender;
    private String user_id;
    private String type_value;

    public String getType_value() {
        return type_value;
    }

    public ModelChat(String id, String sender, String user_id, String type, String message, String type_value) {
        this.id = id;
        this.sender = sender;
        this.user_id = user_id;
        this.type = type;
        this.message = message;
        this.type_value = type_value;
    }

    private String type;

    public String getMessage() {
        return message;
    }

    public String getType() {
        return type;
    }

    public String getUser_id() {
        return user_id;
    }

    public String getSender() {
        return sender;
    }

    public String getId() {
        return id;
    }

    private String message;
}
