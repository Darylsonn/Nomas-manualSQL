import java.io.*;
import java.net.URL;
import java.net.URLConnection;

public class FileProcessor {

    public static File getFileHockey(String date) {
        File file = new File("src/NHL_files/" + date + ".json");
        if (!file.exists()) {
            try {
                URL url = new URL("https://nhl-score-api.herokuapp.com/api/scores?startDate=" + date);
                URLConnection urlc = url.openConnection();
                BufferedReader in = new BufferedReader(new InputStreamReader(urlc.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String inputline;
                while ((inputline = in.readLine()) != null) sb.append(inputline);
                in.close();
                BufferedWriter writer = new BufferedWriter(new FileWriter(file));
                writer.write(sb.toString());
                writer.close();
                System.out.println("File " + file + " downloaded.");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else{
            System.out.println("File " + file + " already exists!");
        }
        return file;
    }
}
