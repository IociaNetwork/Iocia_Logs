package iocia.network.logs;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ILog {

    /**
     * Singleton code for initializing and getting the instance.
     */
    private static ILog instance = null;
    private ILog() {
        logs = new HashMap<>();
        fw = null;
        bw = null;
        dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        date = new Date();
    }
    static void initInstance() {
        if (instance == null)
            instance = new ILog();
    }
    private static ILog getInstance() {
        return instance;
    }

    private Map<String, LogFile> logs;
    private FileWriter fw;
    private BufferedWriter bw;
    DateFormat dateFormat;
    Date date;

    /**
     * Creates a new log file and registers it memory based on the given ID tag.
     *
     * @param ID ID tag of the file. This is used to reference the log file (separate file fileName)
     * @param fileName Name of the file on the computer
     * @param rootFolder Root directory of the plugin folder
     * @param subFolders Any sub-folders within the root folder
     * @throws IOException
     */
    public static void registerLogFile(String ID, String fileName, File rootFolder, String subFolders) throws IOException {

        if (getInstance().logs.containsKey(ID))
            return;

        getInstance().logs.put(ID, new LogFile(fileName, rootFolder, subFolders));

    }

    /**
     * Creates a new log file and registers it memory based on the given ID tag.
     *
     * @param ID ID tag of the file. This is used to reference the log file (separate file fileName)
     * @param fileName Name of the file on the computer
     * @param rootFolder Root directory of the plugin folder
     * @throws IOException
     */
    public static void registerLogFile(String ID, String fileName, File rootFolder) throws IOException {

        if (getInstance().logs.containsKey(ID))
            return;

        getInstance().logs.put(ID, new LogFile(fileName, rootFolder, null));

    }

    /**
     * Unregisters a log file from memory. Whe this is done, another file must be re-registered
     * to log to again.
     *
     * @param ID ID of the log file to unregister
     */
    public static void unRegisterLogFile(String ID) {
        getInstance().logs.remove(ID);
    }

    /**
     * Logs a String of text to a given log file.
     * If the given ID does not match a registered log file, nothing will happen.
     *
     * @param ID ID of the log file
     * @param logType Severity tag of the log
     * @param text Line of text to add to the log
     */
    public static void log(String ID, LogTypes logType, String text) {

        if (!getInstance().logs.containsKey(ID))
            return;

        try {

            getInstance().fw = new FileWriter(getInstance().logs.get(ID).getSystemFile().getAbsolutePath(), true);
            getInstance().bw = new BufferedWriter(getInstance().fw);
            getInstance().bw.write("[" + getInstance().dateFormat.format(getInstance().date) + "] " +
                    logType.get() + text + "\n");

        } catch (IOException e) {
            e.printStackTrace();
        } finally {

            try {

                if (getInstance().bw != null)
                    getInstance().bw.close();

                if (getInstance().fw != null)
                    getInstance().fw.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
