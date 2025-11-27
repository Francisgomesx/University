import java.io.File;
import java.io.FileNotFoundException;
import java.util.Locale;
import java.util.Scanner;

public class moodMapAnalyzer {

    public static void main(String[] args) {
        Locale.setDefault(Locale.US);
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the filename: ");
        String filename = scanner.nextLine();

        MoodData moodData = readMoodMapFromFile(filename);

        if (moodData != null) {
            executeMoodAnalysis(moodData);
        } else {
            System.out.println("Error reading file.");
        }

        scanner.close();
    }

    // Data structure to hold mood information
    public static class MoodData {
        String description;
        int numPeople;
        int numDays;
        int[][] moodMatrix;

        MoodData(String description, int numPeople, int numDays, int[][] moodMatrix) {
            this.description = description;
            this.numPeople = numPeople;
            this.numDays = numDays;
            this.moodMatrix = moodMatrix;
        }
    }

    // a) Read information from file
    public static MoodData readMoodMapFromFile(String filename) {
        try {
            Scanner fileScanner = new Scanner(new File(filename));

            String description = fileScanner.nextLine();
            String[] dimensions = fileScanner.nextLine().split(" ");
            int numPeople = Integer.parseInt(dimensions[0]);
            int numDays = Integer.parseInt(dimensions[1]);

            int[][] moodMatrix = new int[numPeople][numDays];

            for (int person = 0; person < numPeople; person++) {
                String[] moodValues = fileScanner.nextLine().split(" ");
                for (int day = 0; day < numDays; day++) {
                    moodMatrix[person][day] = Integer.parseInt(moodValues[day]);
                }
            }

            fileScanner.close();
            return new MoodData(description, numPeople, numDays, moodMatrix);

        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + filename);
            return null;
        }
    }

    // Execute all analysis functions
    public static void executeMoodAnalysis(MoodData data) {
        int labelWidth = determineLabelWidth(data);
        displayMoodMap(data, labelWidth);
        displayAverageMoodPerDay(data, labelWidth);
        displayAverageMoodPerPerson(data, labelWidth);
        displayDaysWithHighestMood(data);
        displayMoodLevelPercentages(data);
        displayEmotionalDisorders(data, labelWidth);
        displayMoodCharts(data);
        displayRecommendedTherapy(data, labelWidth);
        displayMostSimilarPeople(data);
    }

    public static void displayBoard(MoodData data, int labelWidth) {

        printAlignedLabel("day", labelWidth);
        for (int day = 0; day < data.numDays; day++) {
            System.out.printf("%3d " + " ", day);
        }
        System.out.println();

        // Separator line
        System.out.print("----------|");
        for (int day = 0; day < data.numDays; day++) {
            System.out.print("----|");
        }
        System.out.println();
    }

    // b) Display the MoodMap
    public static void displayMoodMap(MoodData data, int labelWidth) {
        System.out.println("b) Mood (level/day(person)");
        displayBoard(data, labelWidth);

        // Person data
        for (int person = 0; person < data.numPeople; person++) {
            String label = String.format("Person #%d", person);
            printAlignedLabel(label, labelWidth);
            for (int day = 0; day < data.numDays; day++) {
                System.out.printf("%3d " + " ", data.moodMatrix[person][day]);
            }
            System.out.println();
        }
        System.out.println();
    }

    // c) Average mood each day
    public static void displayAverageMoodPerDay(MoodData data, int labelWidth) {
        System.out.println("c) Average mood each day:");

        double[] averages = calculateDailyAverages(data);
        displayBoard(data, labelWidth);

        // Averages
        printAlignedLabel("mood", labelWidth);
        for (int day = 0; day < data.numDays; day++) {
            System.out.printf("%3.1f " + " ", averages[day]);
        }
        System.out.println("\n");
    }

    public static double[] calculateDailyAverages(MoodData data) {
        double[] averages = new double[data.numDays];

        for (int day = 0; day < data.numDays; day++) {
            int sum = 0;
            for (int person = 0; person < data.numPeople; person++) {
                sum += data.moodMatrix[person][day];
            }
            averages[day] = (double) sum / data.numPeople;
        }

        return averages;
    }

    // d) Average mood of each person
    public static void displayAverageMoodPerPerson(MoodData data, int labelWidth) {
        System.out.println("d) Average of each person's mood:");

        double[] personAverages = calculatePersonAverages(data);

        for (int person = 0; person < data.numPeople; person++) {
            String label = String.format("Person #%d", person);
            System.out.printf("%-" + labelWidth + "s : %.1f\n", label, personAverages[person]);
        }
        System.out.println();
    }

    public static double[] calculatePersonAverages(MoodData data) {
        double[] averages = new double[data.numPeople];

        for (int person = 0; person < data.numPeople; person++) {
            int sum = 0;
            for (int day = 0; day < data.numDays; day++) {
                sum += data.moodMatrix[person][day];
            }
            averages[person] = (double) sum / data.numDays;
        }

        return averages;
    }

    // e) Days with the highest mood
    public static void displayDaysWithHighestMood(MoodData data) {
        double[] dailyAverages = calculateDailyAverages(data);
        double maxAverage = findMaxValue(dailyAverages);

        System.out.printf("e) Days with the highest average mood (%.1f) : ", maxAverage);

        boolean first = true;
        for (int day = 0; day < data.numDays; day++) {
            if (dailyAverages[day] == maxAverage) {
                if (!first) {
                    System.out.print(" ");
                }
                System.out.print(day);
                first = false;
            }
        }
        System.out.println("\n");
    }

    public static double findMaxValue(double[] array) {
        double max = array[0];
        for (int i = 1; i < array.length; i++) {
            if (array[i] > max) {
                max = array[i];
            }
        }
        return max;
    }

    // f) Percentage of mood levels
    public static void displayMoodLevelPercentages(MoodData data) {
        System.out.println("f) Percentage of mood levels:");

        int[] moodCounts = countMoodLevels(data);
        int totalEntries = data.numPeople * data.numDays;

        for (int mood = 5; mood >= 1; mood--) {
            double percentage = (moodCounts[mood] * 100.0) / totalEntries;
            System.out.printf("Mood #%d: %.1f%%\n", mood, percentage);
        }
        System.out.println();
    }

    public static int[] countMoodLevels(MoodData data) {
        int[] counts = new int[6]; // Index 0-5, we'll use 1-5

        for (int person = 0; person < data.numPeople; person++) {
            for (int day = 0; day < data.numDays; day++) {
                counts[data.moodMatrix[person][day]]++;
            }
        }

        return counts;
    }

    // g) People with emotional disorders
    public static void displayEmotionalDisorders(MoodData data, int labelWidth) {
        System.out.println("g) People with emotional disorders:");

        int[] maxConsecutiveLowDays = calculateMaxConsecutiveLowMoodDays(data);
        boolean foundDisorder = false;

        for (int person = 0; person < data.numPeople; person++) {
            if (maxConsecutiveLowDays[person] >= 2) {
                String label = String.format("Person #%d", person);
                System.out.printf("%-" + labelWidth + "s : %d consecutive days\n",
                        label, maxConsecutiveLowDays[person]);
                foundDisorder = true;
            }
        }

        if (!foundDisorder) {
            System.out.println("nobody");
        }
        System.out.println();
    }

    public static int[] calculateMaxConsecutiveLowMoodDays(MoodData data) {
        int[] maxConsecutive = new int[data.numPeople];

        for (int person = 0; person < data.numPeople; person++) {
            int currentConsecutive = 0;
            int maxForPerson = 0;

            for (int day = 0; day < data.numDays; day++) {
                if (data.moodMatrix[person][day] < 3) {
                    currentConsecutive++;
                    if (currentConsecutive > maxForPerson) {
                        maxForPerson = currentConsecutive;
                    }
                } else {
                    currentConsecutive = 0;
                }
            }

            maxConsecutive[person] = maxForPerson;
        }

        return maxConsecutive;
    }

    // h) Display mood charts
    public static void displayMoodCharts(MoodData data) {
        System.out.println("h) People's Mood Level Charts:");

        for (int person = 0; person < data.numPeople; person++) {
            displayPersonMoodChart(data, person);
        }
    }

    public static void displayPersonMoodChart(MoodData data, int person) {
        System.out.printf("Person #%d:\n", person);

        int minMood = findMinMoodForPerson(data, person);
        int maxMood = findMaxMoodForPerson(data, person);

        // Display from max to min
        for (int mood = maxMood; mood >= minMood; mood--) {
            System.out.printf("%4d |", mood);

            for (int day = 0; day < data.numDays; day++) {
                if (data.moodMatrix[person][day] == mood) {
                    System.out.print("*");
                } else {
                    System.out.print(" ");
                }
            }
            System.out.println();
        }

        // X-axis
        System.out.print("Mood +");
        for (int i = 0; i < data.numDays; i++) {
            System.out.print("-");
        }
        System.out.println();

        // Legend - align labels with day positions (0, 5, 10, etc.)
        System.out.print("      ");
        int outputPos = 0;
        for (int day = 0; day < data.numDays; day++) {
            if (day % 5 == 0) {
                // Print the label at the correct output position
                String label = String.valueOf(day);
                // If we're not at the right output position yet, add spaces
                while (outputPos < day) {
                    System.out.print(" ");
                    outputPos++;
                }
                System.out.print(label);
                outputPos += label.length();
            }
        }
        // Fill remaining positions with spaces if needed
        while (outputPos < data.numDays) {
            System.out.print(" ");
            outputPos++;
        }
        System.out.println("\n");
    }

    public static int findMinMoodForPerson(MoodData data, int person) {
        int min = data.moodMatrix[person][0];
        for (int day = 1; day < data.numDays; day++) {
            if (data.moodMatrix[person][day] < min) {
                min = data.moodMatrix[person][day];
            }
        }
        return min;
    }

    public static int findMaxMoodForPerson(MoodData data, int person) {
        int max = data.moodMatrix[person][0];
        for (int day = 1; day < data.numDays; day++) {
            if (data.moodMatrix[person][day] > max) {
                max = data.moodMatrix[person][day];
            }
        }
        return max;
    }

    // i) Recommended therapy
    static void displayRecommendedTherapy(MoodData data, int labelWidth) {
        System.out.println("i) Recommended therapy:");

        int[] maxConsecutiveLowDays = calculateMaxConsecutiveLowMoodDays(data);

        for (int person = 0; person < data.numPeople; person++) {
            String therapy = determineTherapy(maxConsecutiveLowDays[person]);
            if (therapy != null) {
                String label = String.format("Person #%d", person);
                System.out.printf("%-" + labelWidth + "s : %s\n", label, therapy);
            }
        }
        System.out.println();
    }

    public static String determineTherapy(int consecutiveLowDays) {
        if (consecutiveLowDays >= 5) {
            return "psychological support";
        } else if (consecutiveLowDays >= 2) {
            return "listen to music";
        } else {
            return null;
        }
    }

    // j) People with most similar moods
    public static void displayMostSimilarPeople(MoodData data) {
        System.out.print("j) People with the most similar moods: ");

        int maxSimilarity = 0;
        int person1 = -1;
        int person2 = -1;

        for (int p1 = 0; p1 < data.numPeople - 1; p1++) {
            for (int p2 = p1 + 1; p2 < data.numPeople; p2++) {
                int similarity = countSameMoodDays(data, p1, p2);
                if (similarity > maxSimilarity) {
                    maxSimilarity = similarity;
                    person1 = p1;
                    person2 = p2;
                }
            }
        }

        if (maxSimilarity > 0) {
            System.out.printf("(Person #%d and Person #%d have the same mood on %d days)\n",
                    person1, person2, maxSimilarity);
        } else {
            System.out.println("None");
        }
    }

    public static int countSameMoodDays(MoodData data, int person1, int person2) {
        int count = 0;
        for (int day = 0; day < data.numDays; day++) {
            if (data.moodMatrix[person1][day] == data.moodMatrix[person2][day]) {
                count++;
            }
        }
        return count;
    }

    public static int determineLabelWidth(MoodData data) {
        if (data.numPeople == 0) {
            return 8;
        }
        String longestPersonLabel = String.format("Person #%d", data.numPeople - 1);
        int longest = Math.max("mood".length(), longestPersonLabel.length());
        return Math.max(8, longest);
    }

    public static void printAlignedLabel(String label, int labelWidth) {
        System.out.printf("%-" + labelWidth + "s : ", label);
    }
}
