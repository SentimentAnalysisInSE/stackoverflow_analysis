import java.io.File;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

import com.google.gson.Gson;
import com.opencsv.CSVWriter;



public class JsonToCSV {
    public static void main(String[] args) {
        //convert();
        writeHashTableToCSV(SOTorrentConnector.getTopUsersTags(100));
    }

    public static void writeListToFile(List list, String filename) {
        try {
            FileWriter writer = new FileWriter(filename, true);
            CSVWriter csvWriter = new CSVWriter(writer);
            for (Object i : list) {
                csvWriter.writeNext(new String[]{i.toString()});
            }
            csvWriter.close();
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void writeHashTableToCSV(HashMap<String, Integer> map) {
        File file = new File("tags.csv");
        try {
            FileWriter outputFile = new FileWriter(file);
            CSVWriter writer = new CSVWriter(outputFile);
            String[] header = {"Tag name", "Count"};
            writer.writeNext(header);

            for (Map.Entry entry : map.entrySet()) {
                System.out.println(entry.getKey() + ": " + entry.getValue());
                writer.writeNext(new String[] {
                        entry.getKey().toString(),
                        entry.getValue().toString()
                });
            }
            writer.close();
            outputFile.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void convert() {
        File f = new File(".");
        File[] files = f.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.endsWith(".json");
            }
        });

        try {
            File file = new File("results.csv");
            // create FileWriter object with file as parameter
            FileWriter outputfile = new FileWriter(file);

            // create CSVWriter object filewriter object as parameter
            CSVWriter writer = new CSVWriter(outputfile);

            // adding header to csv
            String[] header = { "Owner ID", "Openness", "Conscientiousness", "Extraversion", "Agreeableness", "Neuroticism" };
            writer.writeNext(header);

            for (File jsonFile : files) {
                System.out.println(jsonFile.getName());
                Insights obj = new Insights();

                try {
                    String contents = new String(Files.readAllBytes(Paths.get(jsonFile.getName())));
                    Gson gson = new Gson();
                    obj = gson.fromJson(contents, Insights.class);

                    HashMap<String, Insights.Personality> map = new HashMap<String, Insights.Personality>();

                    for(Insights.Personality p : obj.personality) {
                        map.put(p.trait_id, p);
                    }

                    writer.writeNext( new String[] {
                            jsonFile.getName(),
                            Float.toString(map.get("big5_openness").percentile),
                            Float.toString(map.get("big5_conscientiousness").percentile),
                            Float.toString(map.get("big5_extraversion").percentile),
                            Float.toString(map.get("big5_agreeableness").percentile),
                            Float.toString(map.get("big5_neuroticism").percentile)
                    });


                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static class Insights {
        int word_count;
        String processed_language;
        Personality[] personality;

        public static class Personality {
            String trait_id;
            float percentile;
            public Personality() {

            }
        }

        public Insights () {

        }
    }

}
