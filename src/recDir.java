import java.io.File;
import java.util.ArrayList;
import java.util.Objects;

public class recDir {
    public static ArrayList<Data> datas = new ArrayList<>();

    public static void exec(File dir) {
        if (dir.isDirectory()) for (String item : Objects.requireNonNull(dir.list()))
            recDir.exec(new File(dir.getPath() + File.separator + item));
        else {
            if (dir.getName().endsWith(".mp4") || dir.getName().endsWith(".mkv") || dir.getName().endsWith(".avi")){
                Converter.isCurHEVC = false;
                long size  = dir.length();
                int dur = Converter.getDuration(dir.getPath()),
                        curBR = (int)(size * 8 / dur) / 1024;
                datas.add(new Data(dir.getPath(), dur, size, curBR));
            }
        }
    }

}
