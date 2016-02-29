package me.benmarten.griddynamics;

import me.benmarten.griddynamics.model.Row;
import me.benmarten.griddynamics.utils.FileHelper;

import java.util.List;
import java.util.Map;

/**
 * Task:
 * Develop an advanced implementation that is able to join two large tables
 * assuming that none of the input tables can fit RAM. Advanced implementation
 * should process ~100Mb files in several minutes (Xmx=64M).
 * <p>
 * Solution:
 * 1) Read first part of CSV into HashMap(A)
 * 2) Read parts of second CSV as List(B).
 * 2) Lookup each item of list(B) in HashMap(A) - if found, add to result.
 * 3) Write the result list back to file.
 * 4) Continue until no more lines in input files.
 *
 * @author Ben Marten
 */
@SuppressWarnings({"SameParameterValue", "Convert2streamapi"})
class ReaderAdvanced extends Reader {

    private static final int MAX_IN_MEMORY_ROWS = 5;
    private static final String RESULT_FILE = "./data/result.csv";

    public static void main(String... args) {
        new ReaderAdvanced().start();
    }

    /**
     * Runs the solution algorithm.
     */
    void start() {
        Map<String, String> inputA;
        List<Row> inputB;

        FileHelper.clearFile(RESULT_FILE);

        int skipLinesA = 0;
        do {
            inputA = FileHelper.readCsvFileUnique(
                    "./data/input_A.csv", skipLinesA, MAX_IN_MEMORY_ROWS);

            int skipLinesB = 0;
            do {
                inputB = FileHelper.readCsvFile(
                        "./data/input_B.csv", skipLinesB, MAX_IN_MEMORY_ROWS);

                List<Row> joinedList = join(inputB, inputA);
                FileHelper.writeCsvFile(RESULT_FILE, joinedList, true);

                skipLinesB += MAX_IN_MEMORY_ROWS;
            }
            while (inputB.size() == MAX_IN_MEMORY_ROWS);

            skipLinesA += MAX_IN_MEMORY_ROWS;
        }
        while (inputA.size() == MAX_IN_MEMORY_ROWS);
    }

}
