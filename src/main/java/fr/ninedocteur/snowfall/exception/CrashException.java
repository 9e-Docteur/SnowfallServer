package fr.ninedocteur.snowfall.exception;

import be.ninedocteur.apare.utils.logger.Logger;
import fr.ninedocteur.snowfall.Snowfall;
import fr.ninedocteur.snowfall.utils.FileManager;
import fr.ninedocteur.snowfall.utils.FileUtils;
import fr.ninedocteur.snowfall.utils.SnowfallMath;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CrashException extends RuntimeException{
    private Date date = new Date();
    private SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE dd MMMM yyyy");
    private String formattedDate = dateFormat.format(date);
    private String prefix = "[" + formattedDate + " || " + date.getHours() + ":" + date.getMinutes() + ":" + date.getSeconds() + "] ";
    private List<String> CRASH_MESSAGES = new ArrayList<>();
    private String randomMessage;
    private String filePath;
    private String fileName;

    public CrashException(String stackStrace){ //TODO: SELECT RANDOM CRASH MESSAGE and LAUNCH NEW JFRAME WITH CRASH REPORT
        String userHomeDirectory = System.getProperty("user.home");
        File blackWoodDirectorylogs = new File(userHomeDirectory, "ApareAPI/crashs-reports");

        if (!blackWoodDirectorylogs.exists()) {
            blackWoodDirectorylogs.mkdirs();
        }

        fileName = formattedDate.trim().replace(" ", "-") + "---" + date.getHours() + "-" + date.getMinutes() + "-" + date.getSeconds() + ".log";
        this.filePath = new File(blackWoodDirectorylogs, fileName).getPath();

        log("---------------------------- CRASH REPORT ");

        //THIS IS RANDOM MESSAGE FUNNY, DOES NOT REFLECT THE REALITY
        CRASH_MESSAGES.addAll(FileManager.getFile(FileUtils.getFileInResourceFolder("funny-crash-messages.txt")).readFileAsList());

        randomMessage = CRASH_MESSAGES.get(SnowfallMath.generateRandomNumber(0, CRASH_MESSAGES.size() - 1));
        log(prefix + " ►► " + randomMessage + "\n\n");

        log("--------CRASH-------> ");

        log(stackStrace);

        log("---------------------------- END OF CRASH REPORT ");
        System.exit(1);
    }

    private void log(String message) {
        Snowfall.getLogger().send(message, Logger.Type.ERROR);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            writer.write(message);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
