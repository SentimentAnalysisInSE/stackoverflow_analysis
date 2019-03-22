import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ResultsAnalyzer {

    public static void main (String[] args) {

        textFiles("/");
    }

    static List<String> textFiles(String directory) {
        List<String> textFiles = new ArrayList<String>();
        File dir = new File(System.getProperty("user.dir"));
        System.out.println(dir.getAbsolutePath());
        for (File file : dir.listFiles()) {
            if (file.getName().endsWith((".json"))) {
                textFiles.add(file.getName());
                System.out.println(file.getName());
            }
        }
        return textFiles;
    }
}
