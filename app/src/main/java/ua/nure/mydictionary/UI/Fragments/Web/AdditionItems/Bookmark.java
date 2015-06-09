package ua.nure.mydictionary.UI.Fragments.Web.AdditionItems;

public class Bookmark {
    private String url;
    private String header;
    private String pictureId;

    public Bookmark(String url, String header) {
        this(url, header, "0");
    }

    public Bookmark(String url, String header, String pictureId) {
        this.url = url;
        this.header = header;
        this.pictureId = pictureId;

    }

    public String getUrl() {
        return url;
    }

    public String getHeader() {
        return header;
    }

    public String getPictureId() {
        return pictureId;
    }
}
