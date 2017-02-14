package andorid.example.com.myfirebase;

/**
 * Created by Sabin on 2/14/2017.
 */

public class Message {
    private String text;
    private String photoUrl;
    private String name;

    public Message(String text, String photoUrl, String name) {
        this.text = text;
        this.photoUrl = photoUrl;
        this.name = name;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
