public class Data {

    public String path;
    public int BR, duration;
    public long size;


    public Data(String filename, int duration, long size, int BR){
        this.path = filename;
        this.duration = duration;
        this.size = size;
        this.BR = BR;
    }

    public String getPath(){
        return this.path;
    }
}
