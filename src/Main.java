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
                    
                Options:
                    -p, --player <Path>: Set media player's path to run with it
                    -h, --help: Help flag. Show this message.
                    -u, -U, --username <NAME>: Use custom username in trk-in command prompts
                    -c, -C, --coefficient <0-1_float_value>: Set time limit to consider it is done
                    -o, --output, --trackerFileName <FileName>: Set custom filename for trackerFile
                    -e , -i, --extra, --info : Extra info flag. Shows extra info
                    
                                                *******************
                                                *  toh by 0xDABE  *
                                                *******************""");
        System.exit(0);
    }
}
