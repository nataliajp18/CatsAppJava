package org.example.Model;

public class Cats {

    String  id;
    String url;
    String apikey = "live_NlQpHXwYfoObCVmOoBGh1aD4BXSAAgMlpr2TltpDFsRuT0SvzxABfwi4qnqVdEvO";
    String image;

    public Cats() {
    }

    public Cats(String id, String url, String apikey, String image) {
        this.id = id;
        this.url = url;
        this.apikey = apikey;
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getApikey() {
        return apikey;
    }

    public void setApikey(String apikey) {
        this.apikey = apikey;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
