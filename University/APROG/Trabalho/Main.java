import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
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
    static class MoodData {
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
    static MoodData readMoodMapFromFile(String filename) {
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
    static void executeMoodAnalysis(MoodData data) {
        displayMoodMap(data);
        displayAverageMoodPerDay(data);
        displayAverageMoodPerPerson(data);
        displayDaysWithHighestMood(data);
        displayMoodLevelPercentages(data);
        displayEmotionalDisorders(data);
        displayMoodCharts(data);
        displayRecommendedTherapy(data);
        displayMostSimilarPeople(data);
    }
    
    // b) Display the MoodMap
    static void displayMoodMap(MoodData data) {
        System.out.println("b) Mood (level/day(person)");
        
        // Header with day numbers
        System.out.print("day       : ");
        for (int day = 0; day < data.numDays; day++) {
            System.out.printf("%3d ", day);
        }
        System.out.println();
        
        // Separator line
        System.out.print("----------|");
        for (int day = 0; day < data.numDays; day++) {
            System.out.print("---|");
        }
        System.out.println();
        
        // Person data
        for (int person = 0; person < data.numPeople; person++) {
            System.out.printf("Person #%d : ", person);
            for (int day = 0; day < data.numDays; day++) {
                System.out.printf("%3d ", data.moodMatrix[person][day]);
            }
            System.out.println();
        }
        System.out.println();
    }
    
    // c) Average mood each day
    static void displayAverageMoodPerDay(MoodData data) {
        System.out.println("c) Average mood each day:");
        
        double[] averages = calculateDailyAverages(data);
        
        // Header
        System.out.print("day       : ");
        for (int day = 0; day < data.numDays; day++) {
            System.out.printf("%3d ", day);
        }
        System.out.println();
        
        // Separator
        System.out.print("----------|");
        for (int day = 0; day < data.numDays; day++) {
            System.out.print("---|");
        }
        System.out.println();
        
        // Averages
        System.out.print("mood      ");
        for (int day = 0; day < data.numDays; day++) {
            System.out.printf("%4.1f", averages[day]);
        }
        System.out.println("\n");
    }
    
    static double[] calculateDailyAverages(MoodData data) {
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
    static void displayAverageMoodPerPerson(MoodData data) {
        System.out.println("d) Average of each person's mood:");
        
        double[] personAverages = calculatePersonAverages(data);
        
        for (int person = 0; person < data.numPeople; person++) {
            System.out.printf("Person #%d  : %.1f\n", person, personAverages[person]);
        }
        System.out.println();
    }
    
    static double[] calculatePersonAverages(MoodData data) {
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
    
    // e) Days with highest mood
    static void displayDaysWithHighestMood(MoodData data) {
        double[] dailyAverages = calculateDailyAverages(data);
        double maxAverage = findMaxValue(dailyAverages);
        
        System.out.printf("e) Days with the highest average mood (%.1f) : ", maxAverage);
        
        for (int day = 0; day < data.numDays; day++) {
            if (dailyAverages[day] == maxAverage) {
                System.out.print(" " + day);
            }
        }
        System.out.println("\n");
    }
    
    static double findMaxValue(double[] array) {
        double max = array[0];
        for (int i = 1; i < array.length; i++) {
            if (array[i] > max) {
                max = array[i];
            }
        }
        return max;
    }
    
    // f) Percentage of mood levels
    static void displayMoodLevelPercentages(MoodData data) {
        System.out.println("f) Percentage of mood levels:");
        
        int[] moodCounts = countMoodLevels(data);
        int totalEntries = data.numPeople * data.numDays;
        
        for (int mood = 5; mood >= 1; mood--) {
            double percentage = (moodCounts[mood] * 100.0) / totalEntries;
            System.out.printf("Mood #%d: %.1f%%\n", mood, percentage);
        }
        System.out.println();
    }
    
    static int[] countMoodLevels(MoodData data) {
        int[] counts = new int[6]; // Index 0-5, we'll use 1-5
        
        for (int person = 0; person < data.numPeople; person++) {
            for (int day = 0; day < data.numDays; day++) {
                counts[data.moodMatrix[person][day]]++;
            }
        }
        
        return counts;
    }
    
    // g) People with emotional disorders
    static void displayEmotionalDisorders(MoodData data) {
        System.out.println("g) People with emotional disorders:");
        
        int[] maxConsecutiveLowDays = calculateMaxConsecutiveLowMoodDays(data);
        boolean foundDisorder = false;
        
        for (int person = 0; person < data.numPeople; person++) {
            if (maxConsecutiveLowDays[person] >= 2) {
                System.out.printf("Person #%d  : %d consecutive days\n", 
                    person, maxConsecutiveLowDays[person]);
                foundDisorder = true;
            }
        }
        
        if (!foundDisorder) {
            System.out.println("nobody");
        }
        System.out.println();
    }
    
    static int[] calculateMaxConsecutiveLowMoodDays(MoodData data) {
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
    static void displayMoodCharts(MoodData data) {
        System.out.println("h) People's Mood Level Charts:");
        
        for (int person = 0; person < data.numPeople; person++) {
            displayPersonMoodChart(data, person);
        }
    }
    
    static void displayPersonMoodChart(MoodData data, int person) {
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
        
        // Legend
        System.out.print("      0");
        int nextLabel = 5;
        while (nextLabel < data.numDays) {
            int spaces = 5;
            for (int i = 0; i < spaces; i++) {
                System.out.print(" ");
            }
            System.out.print(nextLabel);
            nextLabel += 5;
        }
        System.out.println("\n");
    }
    
    static int findMinMoodForPerson(MoodData data, int person) {
        int min = data.moodMatrix[person][0];
        for (int day = 1; day < data.numDays; day++) {
            if (data.moodMatrix[person][day] < min) {
                min = data.moodMatrix[person][day];
            }
        }
        return min;
    }
    
    static int findMaxMoodForPerson(MoodData data, int person) {
        int max = data.moodMatrix[person][0];
        for (int day = 1; day < data.numDays; day++) {
            if (data.moodMatrix[person][day] > max) {
                max = data.moodMatrix[person][day];
            }
        }
        return max;
    }
    
    // i) Recommended therapy
    static void displayRecommendedTherapy(MoodData data) {
        System.out.println("i) Recommended therapy:");
        
        int[] maxConsecutiveLowDays = calculateMaxConsecutiveLowMoodDays(data);
        
        for (int person = 0; person < data.numPeople; person++) {
            String therapy = determineTherapy(maxConsecutiveLowDays[person]);
            if (therapy != null) {
                System.out.printf("Person #%d  : %s\n", person, therapy);
            }
        }
        System.out.println();
    }
    
    static String determineTherapy(int consecutiveLowDays) {
        if (consecutiveLowDays >= 5) {
            return "psychological support";
        } else if (consecutiveLowDays >= 2) {
            return "listen to music";
        } else {
            return null;
        }
    }
    
    // j) People with most similar moods
    static void displayMostSimilarPeople(MoodData data) {
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
    
    static int countSameMoodDays(MoodData data, int person1, int person2) {
        int count = 0;
        for (int day = 0; day < data.numDays; day++) {
            if (data.moodMatrix[person1][day] == data.moodMatrix[person2][day]) {
                count++;
            }
        }
        return count;
    }
}
