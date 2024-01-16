import com.dalibe.ColoredMessage;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Comparator;

public class Converter {
    public static boolean deleteFlag = false;
    public static boolean isCurHEVC = false;

    public static void run(){
        File WD = new File(System.getProperty("user.dir"));
        recDir.exec(WD);
        ArrayList<Data> datas = new ArrayList<>(recDir.datas);
        datas.sort(Comparator.comparing(Data::getPath));
        for (Data data : datas) exec(data.path, data.duration, data.size, data.BR);
    }

    public static void exec(String filename, int dur, long size, int currentFileBR){
        if (isCurHEVC){
            ColoredMessage.yellowLn("Skipped (already HEVC)");
            return;
        }
        String newFileName = filename.substring(0, filename.lastIndexOf(".")) + "_HEVC.mp4";
        String[] command = new String[]{"ffmpeg", "-i", filename, "-c:v",
                "hevc_nvenc", "-preset", "p7", "-b:v",
                (currentFileBR / Main.K) + "k", "-maxrate",
                (currentFileBR / Main.K) + 800 + "k",
                "-bufsize", "12M", "-rc-lookahead", "32", "-c:a", "copy", "-y", newFileName};
        ProcessBuilder pb = new ProcessBuilder(command);

        try {
            Process process = pb.start();
            ColoredMessage.blueLn("src_brate= ~" + currentFileBR + "kB/s");
            ColoredMessage.blueLn("dst_brate= " + (currentFileBR / Main.K)+ "kB/s");
            ColoredMessage.blueLn("Size: " + size/1024/1024 + " M");
            ColoredMessage.blueLn("Duration: " + dur + " s");
            for (String item : command) ColoredMessage.blue(item + " ");
            System.out.println();

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            while (reader.readLine() != null) {}
            process.destroy();
            if (deleteFlag) if (isConverted(newFileName)) new File(filename).delete();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static int getDuration(String filename) {
        int timeInSec = 0;
        try {
            ProcessBuilder processBuilder = new ProcessBuilder("ffmpeg", "-i", filename);
            Process process = processBuilder.start();

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            String line, durationLine = null;
            while ((line = reader.readLine()) != null) {

                if (line.contains("Duration")) durationLine = line;
                if (line.toLowerCase().contains("video: hevc")){
                    isCurHEVC = true;
                    return 1;
                }
            }
            if (durationLine == null) {
                System.err.println("Not a video: " + filename);
                System.exit(-1);
            }
            line = durationLine;
            String hours, minutes, seconds;
            hours = line.split("[,.:]")[1].trim();
            minutes = line.split("[,.:]")[2].trim();
            seconds = line.split("[,.:]")[3].trim();
            timeInSec = Integer.parseInt(hours) * 60 * 60 +
                    Integer.parseInt(minutes) * 60 + Integer.parseInt(seconds);

            reader.close();
        } catch (IOException e) {
            ColoredMessage.redLn("Error creating ffmpeg process. Check if it is in bin path.");
            System.exit(-1);
        }
        return timeInSec;
    }

    public static boolean isConverted(String filename){
        return new File(filename).length() != 0;
    }
}
