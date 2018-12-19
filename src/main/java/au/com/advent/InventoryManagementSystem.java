package au.com.advent;

import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.*;

public class InventoryManagementSystem {

    private static final Logger LOGGER = LoggerFactory.getLogger(InventoryManagementSystem.class);

    private static int twoCounterInstance = 0;
    private static int threeCounterInstance = 0;

    public static void main(String[] args) throws Exception {

        final File boxIdFile = new File("src/main/resources/input.txt");
        List<String> boxIDs = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(boxIdFile))) {
            while (reader.ready()) {
                boxIDs.add(reader.readLine());
            }

            phase1(boxIDs);
            phase2(boxIDs);

        } catch (Exception ex) {
            LOGGER.error("Exception occurred: {}", ex);
            throw new Exception("Exception occurred");
        }
    }

    private static void phase1(List<String> boxIDs) {

        Iterator<String> it = boxIDs.iterator();
        Map<Character, Integer> letterHashMap = new HashMap<>();
        while(it.hasNext()) {
            int twoCounter = 0;
            int threeCounter = 0;
            String boxID = it.next();
            for (char letter : boxID.toCharArray()) {
                int letterCounter = 0;
                letterHashMap.putIfAbsent(letter, letterCounter);
                for (char letter2 : boxID.toCharArray()) {
                    if (letter == letter2) {
                        letterCounter++;
                    }
                }
                if (letterCounter > 0) {
                    letterHashMap.put(letter, letterCounter);
                    switch (letterCounter) {
                        case 2 :
                            twoCounter++;
                            break;
                        case 3 :
                            threeCounter++;
                            break;
                    }
                }
            }
            if (twoCounter > 0) {
                twoCounterInstance++;
            }
            if (threeCounter > 0) {
                threeCounterInstance++;
            }
        }

        LOGGER.info("Two counter: {}", twoCounterInstance);
        LOGGER.info("Three counter: {}", threeCounterInstance);
        int checksum = twoCounterInstance * threeCounterInstance;
        LOGGER.info("Checksum is: {}", checksum);
    }

    private static void phase2(List<String> boxIDs) {

        final int errorMargin = 1;

        for (String boxID : boxIDs) {
            for (String boxID2: boxIDs) {
                int mismatchCounter = 0;
                if (boxID.equals(boxID2)) {
                    continue;
                }
                List<Character> boxChars = Arrays.asList(ArrayUtils.toObject(boxID.toCharArray()));
                ListIterator<Character> iterator = boxChars.listIterator();
                int boxChar2Counter = 0;
                while (iterator.hasNext()) {
                    char[] boxLetter2 = boxID2.toCharArray();
                    char boxChar1 = iterator.next();
                    char boxChar2 = boxLetter2[boxChar2Counter];
                    if (boxChar1 != boxChar2) {
                        mismatchCounter++;
                    }
                    if (mismatchCounter > errorMargin) {
                        break;
                    }
                    boxChar2Counter++;
                }
                if (mismatchCounter == 1) {
                    LOGGER.info("Found the boxIDs!: {}, {}", boxID, boxID2);
                    return;
                }
            }
        }
    }
}
