package com.example.myapplication;

public class MemoItem{
    private String title;
    private String preview;
    private String time;

    private String key;
    public String getTitle(){
        return title;
    }
    public String getPreview(){
        return preview;
    }
    public String getKey() {return key;}
    public String getTime() { return time; }
    public void setTitle(String title){
        this.title = title;
    }
    public void setPreview(String preview){
        this.preview = preview;
    }
    public void setTime(String time) { this.time = time; }
    public void setKey(String key) {this.key = key; }
    public MemoItem(String time, String title, String preview, String key){
        this.time = time;
        this.title = title;
        this.preview = preview;
        this.key = key;
    }


    public boolean compareTo(MemoItem memoItem) {
        if(Integer.parseInt(time.substring(0,2)) < Integer.parseInt(memoItem.time.substring(0,2))){
            return false;
        }
        else if(Integer.parseInt(time.substring(0,2)) == Integer.parseInt(memoItem.time.substring(0,2))){
            if(Integer.parseInt(time.substring(3,5)) < Integer.parseInt(memoItem.time.substring(3, 5))) {
                return false;
            }
            else return true;
        }
        else return true;
    }
}
