package org.example.Model;

public class CatsFav {

    String id;
    String imageId;
    String apiKey = "live_NlQpHXwYfoObCVmOoBGh1aD4BXSAAgMlpr2TltpDFsRuT0SvzxABfwi4qnqVdEvO";
    ImageX image;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public ImageX getImage() {
        return image;
    }

    public void setImage(ImageX image) {
        this.image = image;
    }
}
