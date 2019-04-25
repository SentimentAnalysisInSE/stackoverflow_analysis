
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import com.opencsv.CSVWriter;



public class SOTorrentConnector {

    Connection conn;
    static final int POSTID_MAX = 53577511;
    static final int USERID_MAX = 10733737;
    static final int MIN_WORDS = 600;

    public SOTorrentConnector() {

        System.out.println("Loading driver...");

        try {
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("Driver loaded!");
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException("Cannot find the driver in the classpath!", e);
        }

        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/sotorrent18_12",
                    "java",
                    "password");
            System.out.println("created database connector");
        } catch (SQLException e) {
            handleSQLException(e);
        }
    }



    public static void main (String[] args) {
        //analyzeTopPostersByScore();
        writeAllReputationsToCSV();
    }

    public static void analyzeTopPostersByScore() {
        SOTorrentConnector sotorrent = new SOTorrentConnector();
        ArrayList<Integer> uids = sotorrent.getTopPostUsers(10);
        for(Integer i : uids) {
            String appendedPostBodies = sotorrent.getAppendedPostBodies(i, 10);
            PersonalityInsightsHandler.sendToPersonalityInsights(appendedPostBodies,i);
            System.out.println(appendedPostBodies);
        }
        System.out.println("Program Complete.");
    }

    public static void writeAllReputationsToCSV() {
        SOTorrentConnector sotorrent = new SOTorrentConnector();
        String filename = "reputations.csv";
        int increment = 10000;
        for(int i = 0; i < 10800000; i += increment) {
            LinkedList reputations = sotorrent.getUserReputationByIDWithinRange(i, i + increment);
            JsonToCSV.writeListToFile(reputations, filename);
        }

    }

    public LinkedList<Integer> getUserReputationByIDWithinRange(int min, int max) {
        LinkedList<Integer> result = new LinkedList<Integer>();
        String query = "SELECT Reputation from Users WHERE 'Id' > " +
                min +
                " AND Id <= " +
                max +
                ";";
        Statement stmt = null;
        ResultSet rs = null;
        System.out.println("Setup...");
        Date date = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
        String strDate = dateFormat.format(date);
        try {
            System.out.println("Executing Query \n" + query + "\nat " + strDate);
            stmt = conn.createStatement();
            rs = stmt.executeQuery(query);
            while(rs.next()) {
                result.add(rs.getInt(1));
            }
            System.out.println("Done querying.");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public ArrayList<Integer> getTopPostUsers(int limit) {
        ArrayList<Integer> result = new ArrayList<Integer>(limit);
        String query = "SELECT OwnerUserId from Posts ORDER BY Score DESC;";
        Statement stmt = null;
        ResultSet rs = null;
        System.out.println("Setup...");
        try {
            System.out.print("Executing Query \n" + query + "... ");
            long startTime = System.currentTimeMillis();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(query);
            long endTime = System.currentTimeMillis();
            Long interval = (endTime - startTime) / 1000;
            System.out.println("done after " + interval.toString() + " seconds");
            while(result.size() < limit && rs.next()) {
                System.out.println();
                String rawText = rs.getString(1);
                int idInt = Integer.parseInt(rawText);
                System.out.println(rawText);
                result.add(idInt);

            }
        } catch (SQLException e) {
            handleSQLException(e);
        }
        return result;
    }


    public String getAppendedPostBodies (int userId, int limit) {
        String query = "SELECT Body FROM Posts WHERE OwnerUserId='"
                + Integer.toString(userId)
                + "' ORDER BY Score DESC;" ;
        Statement stmt = null;
        ResultSet rs = null;
        System.out.println("UserID: " + userId);
        try {
            System.out.print("Executing Query \"" + query + "\" ... ");
            long startTime = System.currentTimeMillis();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(query);
            long endTime = System.currentTimeMillis();
            Long interval = (endTime - startTime) / 1000;
            System.out.println("done after " + interval.toString() + " seconds");
            String result = "";
            int wordCount = 0;
            while(wordCount < MIN_WORDS && rs.next()) {
                String rawText = rs.getString(1);
                String cleanText = Denoiser.denoise(rawText);
                result += cleanText + " ";
                //word counting
                String tokens[] = cleanText.split("\\s+");
                wordCount = tokens.length;
            }
            return result;
        } catch (SQLException e) {
            handleSQLException(e);
            return null;
        }
    }

    public static void writeRandomInsightsToFile(int numResults) {
        for(int i = 0; i < numResults; i++) {

            int postId = (int) (Math.random() * POSTID_MAX);
            System.out.println(postId);

        }
    }

    public static void writeWordCountsToFile() {
        Connection conn = null;

        System.out.println("Loading driver...");

        try {
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("Driver loaded!");
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException("Cannot find the driver in the classpath!", e);
        }

        try {
            conn =
                    DriverManager.getConnection("jdbc:mysql://localhost:3306/sotorrent18_12", "java", "password");

            // Do something with the Connection
            Statement stmt = null;
            ResultSet rs = null;

            try {
                File file = new File("results.csv");
                    // create FileWriter object with file as parameter
                    FileWriter outputfile = new FileWriter(file);

                    // create CSVWriter object filewriter object as parameter
                    CSVWriter writer = new CSVWriter(outputfile);

                    // adding header to csv
                    String[] header = { "WordCount", "Text" };
                    writer.writeNext(header);


                stmt = conn.createStatement();
                rs = stmt.executeQuery("SELECT body FROM Posts LIMIT 20");
                while(rs.next()) {
                    String curString = rs.getString(1);
                    System.out.println(curString);
                    String cleanString = Denoiser.denoise(curString);
                    String tokens[] = cleanString.split("\\s+");
                    int wordCount = tokens.length;
                    System.out.println(wordCount);
                    writer.writeNext( new String[] { Integer.toString(wordCount), cleanString });

                }

                // closing writer connection
                writer.close();


            }
            catch (SQLException ex){
                // handle any errors
                handleSQLException(ex);
            }
            catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
            finally {
                // it is a good idea to release
                // resources in a finally{} block
                // in reverse-order of their creation
                // if they are no-longer needed

                if (rs != null) {
                    try {
                        rs.close();
                    } catch (SQLException sqlEx) { } // ignore

                    rs = null;
                }

                if (stmt != null) {
                    try {
                        stmt.close();
                    } catch (SQLException sqlEx) { } // ignore

                    stmt = null;
                }
            }


        } catch (SQLException ex) {
            // handle any errors
            handleSQLException(ex);
        }
    }

    public static void handleSQLException(SQLException ex) {
        System.out.println("SQLException: " + ex.getMessage());
        System.out.println("SQLState: " + ex.getSQLState());
        System.out.println("VendorError: " + ex.getErrorCode());
    }
}
