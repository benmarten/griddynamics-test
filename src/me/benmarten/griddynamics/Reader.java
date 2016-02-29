package me.benmarten.griddynamics;

import me.benmarten.griddynamics.model.Row;
import me.benmarten.griddynamics.utils.FileHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Task:
 * Develop a simple implementation that joins two input tables in a reasonably
 * efficient way and can store both tables in RAM if needed.
 * <p>
 * Solution:
 * 1) Read first CSV into HashMap(A)
 * 2) Read second CSV as List(B).
 * 2) Lookup each item of list(B) in HashMap(A) - if found, add to result.
 * 3) Write the result list back to file.
 *
 * @author Ben Marten
 */
@SuppressWarnings({"SameParameterValue", "Convert2streamapi"})
class Reader {

    private static final String CSV_SEPARATOR = ",";

    public static void main(String... args) {
        new Reader().start();
    }

    /**
     * Runs the solution algorithm.
     */
    void start() {

        Map<String, String> inputA = FileHelper
                .readCsvFileUnique("./data/input_A.csv");
        List<Row> inputB = FileHelper.readCsvFile("./data/input_B.csv");

        List<Row> joinedList = join(inputB, inputA);

        FileHelper.writeCsvFile("./data/result.csv", joinedList, false);
    }

    /**
     * Join two lists by its keys together.
     *
     * @param listA The first list.
     * @param listB The second list.
     * @return The joined result list.
     */
    List<Row> join(List<Row> listA, Map<String, String> listB) {

        System.out.println("Joining lists ...");

        List<Row> result = new ArrayList<>();

        for (Row rowA : listA) {
            if (listB.containsKey(rowA.getKey())) {
                result.add(new Row(rowA.getKey(), listB.get(rowA.getKey())
                        + CSV_SEPARATOR + rowA.getValue()
                ));
            }
        }

        return result;
    }

}
