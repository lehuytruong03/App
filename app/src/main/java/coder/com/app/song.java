package coder.com.app;

public class song {
    private String Title;
    private int File;

    public song(String title, int file){
        Title   = title;
        File    = file;
    }
    public String getTitle() {
        return Title;
    }
    public int getFile() {
        return File;
    }
}
