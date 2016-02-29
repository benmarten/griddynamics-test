package me.benmarten.griddynamics.utils;

import me.benmarten.griddynamics.model.Row;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Class to handle file operations.
 *
 * @author Ben Marten
 */
@SuppressWarnings("SameParameterValue")
public class FileHelper {

    private static final String CSV_SEPARATOR = ",";

    /**
     * Reads a CSV text file in the format "KEY,VALUE" into a list.
     *
     * @param filename The path to the file to be read.
     * @return A list with all the read data, null on error.
     */
    public static List<Row> readCsvFile(String filename) {
        return readCsvFile(filename, null, null);
    }

    /**
     * Reads a CSV text file in the format "KEY,VALUE" into a list.
     *
     * @param filename  The path to the file to be read.
     * @param startLine The start line, defaults to 0.
     * @param maxLines  The maximum lines to read, defaults to all.
     * @return A list with all the read data, null on error.
     */
    public static List<Row> readCsvFile(
            String filename, Integer startLine, Integer maxLines) {

        System.out.println("Reading file: " + filename);

        BufferedReader bufferedReader = null;
        String line;
        List<Row> result = new ArrayList<>();

        if (startLine == null) {
            startLine = 0;
        }

        try {
            bufferedReader = new BufferedReader(new FileReader(filename));
            int currentLine = 0;
            while ((line = bufferedReader.readLine()) != null) {
                if (currentLine++ < startLine) {
                    continue;
                }
                String[] data = line.split(CSV_SEPARATOR);
                result.add(new Row(data[0], data[1]));
                if (maxLines != null && currentLine - startLine >= maxLines) {
                    break;
                }
            }
        } catch (IOException | IndexOutOfBoundsException e) {
            e.printStackTrace();
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return result;
    }


    /**
     * Reads a CSV text file in the format "KEY,VALUE" into a hash map
     * containing only unique values & providing fast retrieval.
     *
     * @param filename The path to the file to be read.
     * @return A HashMap with all the read data, null on error.
     */
    public static HashMap<String, String> readCsvFileUnique(String filename) {
        return readCsvFileUnique(filename, null, null);
    }

    /**
     * Reads a CSV text file in the format "KEY,VALUE" into a hash map
     * containing only unique values & providing fast retrieval.
     *
     * @param filename  The path to the file to be read.
     * @param startLine The start line, defaults to 0.
     * @param maxLines  The maximum lines to read, defaults to all.
     * @return A HashMap with all the read data, null on error.
     */
    public static HashMap<String, String> readCsvFileUnique(
            String filename, Integer startLine, Integer maxLines) {

        System.out.print("Reading file: " + filename);

        BufferedReader bufferedReader = null;
        String line;
        HashMap<String, String> result = new HashMap<>();

        if (startLine == null) {
            startLine = 0;
        }

        try {
            bufferedReader = new BufferedReader(new FileReader(filename));
            int currentLine = 0;
            while ((line = bufferedReader.readLine()) != null) {
                //noinspection ConstantConditions
                if (startLine != null && currentLine++ < startLine) {
                    continue;
                }
                String[] data = line.split(CSV_SEPARATOR);
                result.put(data[0], data[1]);
                if (maxLines != null && currentLine - startLine >= maxLines) {
                    break;
                }
            }
        } catch (IOException | IndexOutOfBoundsException e) {
            e.printStackTrace();
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return result;
    }

    /**
     * Clear contents of a file.
     *
     * @param filename The path to the file.
     */
    public static void clearFile(String filename) {

        System.out.println("Clearing file: " + filename);

        PrintWriter pw = null;
        try {
            pw = new PrintWriter(filename);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (pw != null) {
                pw.close();
            }
        }
    }

    /**
     * Write a list of rows back to a csv file.
     *
     * @param filename The path to the file to be read.
     * @param list     The list to be written to disk.
     * @param append   Wheter to append to an existing file.
     */
    public static void writeCsvFile(
            String filename, List<Row> list, Boolean append) {

        System.out.println("Writing file: " + filename);

        PrintWriter pw = null;
        try {
            pw = new PrintWriter(new FileWriter(filename, append));
            for (Row row : list) {
                pw.println(row.getKey() + CSV_SEPARATOR + row.getValue());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (pw != null) {
                pw.close();
            }
        }
    }

}
