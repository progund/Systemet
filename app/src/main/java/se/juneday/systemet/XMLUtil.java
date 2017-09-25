package se.juneday.systemet;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by hesa on 2017-09-24.
 */

public class XMLUtil {

  public static void fixUTF8File(String dir, String fileName, String tmpName) throws IOException {
    System.err.println("XMLUtil: " + dir + " | " + fileName + " | " + tmpName);

    // resulting file (
    String newFileName = dir + fileName;
    File newFile = new File(newFileName);
    FileWriter fw = new FileWriter(newFile);

    FileReader file = new FileReader(dir + tmpName);
    BufferedReader br = new BufferedReader(file);

    System.err.println("XMLUtil: reading " + file + " writing " + newFile);
    while(br.ready()) {
      fw.write(br.readLine().replaceAll("å", "a") + "\n");
    }
    fw.close();

    System.err.println("XMLUtil: done");
  }


}
