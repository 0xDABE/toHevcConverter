import com.dalibe.ColoredMessage;
import com.dalibe.Nargs;

import java.io.IOException;

public class Main {

    public static float K = 1.5F;

    public static void main(String[] args) {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            ProcessBuilder processBuilder = new ProcessBuilder("cmd", "/c", "taskkill", "/IM", "ffmpeg.exe", "/f");
            try{
                processBuilder.start();
            }catch (IOException ignored){}
        }));
        String line;
        Nargs nargs = new Nargs(args, "<c>:<d><-delete><h><-help>");
        while ((line = nargs.getOpt()) != null) {
            switch (line) {
                case "c" -> {
                    try {
                        K = Float.parseFloat(nargs.optionArg.trim());
                    } catch (NumberFormatException e) {
                        System.err.println("Error: \"" + nargs.optionArg + "\" is not a float number");
                        System.exit(-1);
                    }
                }
                case "d", "-delete" -> Converter.deleteFlag = true;
                case "h", "-help" -> help();
            }
        }
        ColoredMessage.blueLn("Launching with K=" + K);
        Converter.run();
    }

    public static void help() {
        System.out.println("""
                                 
                                                ******************
                                                *      HELP      *
                                                ******************
                            
                Description:
                    "toh" is the script (may be binary or kinda bash or bat, but must be in PATH) that uses this java utility to recursively recode video files to HEVC
                    
                Usage:
                    toh <options>
                    
                ...lazy
                    
                                                *******************
                                                *  toh by 0xDABE  *
                                                *******************""");
        System.exit(0);
    }
}
