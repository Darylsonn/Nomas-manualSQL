import NBA_Class.Game;
import NHL_Class.GameStats;
import NHL_Class.Goal;

import NHL_Class.Root;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.xml.crypto.Data;
import java.io.*;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


public class MenuTest {

    public static void main(String[] args) throws IOException {

        LocalDate todayDate = LocalDate.now();
        LocalDate startDateNHL = LocalDate.of(2021, 10, 19);
        LocalDate endDateNHL = LocalDate.of(2021, 10, 20);
        DateTimeFormatter dtfNHL = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String date = dtfNHL.format(startDateNHL);
        File file = FileProcessor.getFileHockey(date);
        DatabaseHelper.createFilesTable();
        DatabaseHelper.createGamesTable();
        if(!DatabaseHelper.queryFileName(file.getName())) {
            DatabaseHelper.insertFileName(file.getName(), Date.valueOf(startDateNHL));
            Hockey.uploadFileDataHockey(file);
            System.out.println("File " + file.getName() + " processed.");
        }
        else {
            System.out.println("File already processed!");
        }
    }
}