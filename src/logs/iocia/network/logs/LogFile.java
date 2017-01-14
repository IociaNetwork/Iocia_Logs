package iocia.network.logs;

import java.io.File;
import java.io.IOException;

class LogFile {

    private File systemFile;

    LogFile(String fileName, File rootFolder, String subFolders) throws IOException {

        //Checks to see if they file is located in any given sub-folders
        if (subFolders == null) {
            systemFile = new File(rootFolder, fileName + ".txt");
        } else {
            systemFile = new File(rootFolder, subFolders + File.separator + fileName + ".txt");
        }

        //Repeatedly loops and attempts to find an available file name by adding '(number)'
        //to the end of the file until an available name is found
        int count = 1;
        String newFile = systemFile.getAbsolutePath().substring(0, systemFile.getAbsolutePath().lastIndexOf('.'));
        while(systemFile.exists()) {
            systemFile = new File(newFile + "(" + count + ")" + ".txt");
            count++;
        }

        //Creates any required parent folders
        systemFile.getParentFile().mkdirs();

        //Creates the file
        systemFile.createNewFile();

    }

    File getSystemFile() {
        return systemFile;
    }

}
