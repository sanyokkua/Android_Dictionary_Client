package ua.nure.mydictionary.UI.Fragments.Web.AdditionItems;

import java.net.URL;

public class Bookmark {
    private URL url;
    private String header;
    private int pictureId;

    public Bookmark(URL url, String header) {
        this(url, header, 0);
    }

    public Bookmark(URL url, String header, int pictureId) {
        this.url = url;
        this.header = header;
        this.pictureId = pictureId;

    }

    public URL getUrl() {
        return url;
    }

    public String getHeader() {
        return header;
    }

    public int getPictureId() {
        return pictureId;
    }
}
