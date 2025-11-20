import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class MoodMapSimple {

    public static void main(String[] args) {
        // Create scanner to read user input
        Scanner keyboard = new Scanner(System.in);
        
        // Ask for filename
        System.out.print("Enter the filename: ");
        String filename = keyboard.nextLine();
        
        // Read the file
        String description = "";
        int numPeople = 0;
        int numDays = 0;
        int[][] moods = null;
        
        // Try to open and read the file
        try {
            Scanner file = new Scanner(new File(filename));
            
            // Line 1: Description
            description = file.nextLine();
            
            // Line 2: Number of people and days
            String[] numbers = file.nextLine().split(" ");
            numPeople = Integer.parseInt(numbers[0]);
            numDays = Integer.parseInt(numbers[1]);
            
            // Create the mood table
            moods = new int[numPeople][numDays];
            
            // Read each person's moods
            for (int person = 0; person < numPeople; person++) {
                String[] moodLine = file.nextLine().split(" ");
                for (int day = 0; day < numDays; day++) {
                    moods[person][day] = Integer.parseInt(moodLine[day]);
                }
            }
            
            file.close();
            
        } catch (FileNotFoundException e) {
            System.out.println("File not found!");
            return; // Exit the program
        }
        
        // Now run all the analyses
        showMoodTable(moods, numPeople, numDays);
        showDailyAverages(moods, numPeople, numDays);
        showPersonAverages(moods, numPeople, numDays);
        showBestDays(moods, numPeople, numDays);
        showMoodPercentages(moods, numPeople, numDays);
        showEmotionalProblems(moods, numPeople, numDays);
        showMoodCharts(moods, numPeople, numDays);
        showTherapyRecommendations(moods, numPeople, numDays);
        showSimilarPeople(moods, numPeople, numDays);
        
        keyboard.close();
    }
    
    // ========== b) Show the mood table ==========
    public static void showMoodTable(int[][] moods, int numPeople, int numDays) {
        System.out.println("b) Mood (level/day(person)");
        
        // Print day numbers at top
        System.out.print("day       : ");
        for (int day = 0; day < numDays; day++) {
            System.out.printf("%3d ", day);
        }
        System.out.println();
        
        // Print separator line
        System.out.print("----------|");
        for (int day = 0; day < numDays; day++) {
            System.out.print("---|");
        }
        System.out.println();
        
        // Print each person's moods
        for (int person = 0; person < numPeople; person++) {
            System.out.printf("Person #%d : ", person);
            for (int day = 0; day < numDays; day++) {
                System.out.printf("%3d ", moods[person][day]);
            }
            System.out.println();
        }
        System.out.println();
    }
    
    // ========== c) Average mood per day ==========
    public static void showDailyAverages(int[][] moods, int numPeople, int numDays) {
        System.out.println("c) Average mood each day:");
        
        // Calculate average for each day
        double[] dailyAvg = new double[numDays];
        
        for (int day = 0; day < numDays; day++) {
            int sum = 0;
            // Add up all people's moods for this day
            for (int person = 0; person < numPeople; person++) {
                sum = sum + moods[person][day];
            }
            // Calculate average
            dailyAvg[day] = (double) sum / numPeople;
        }
        
        // Print header
        System.out.print("day       : ");
        for (int day = 0; day < numDays; day++) {
            System.out.printf("%3d ", day);
        }
        System.out.println();
        
        // Print separator
        System.out.print("----------|");
        for (int day = 0; day < numDays; day++) {
            System.out.print("---|");
        }
        System.out.println();
        
        // Print averages
        System.out.print("mood      ");
        for (int day = 0; day < numDays; day++) {
            System.out.printf("%4.1f", dailyAvg[day]);
        }
        System.out.println("\n");
    }
    
    // ========== d) Average mood per person ==========
    public static void showPersonAverages(int[][] moods, int numPeople, int numDays) {
        System.out.println("d) Average of each person's mood:");
        
        // Calculate each person's average
        for (int person = 0; person < numPeople; person++) {
            int sum = 0;
            // Add up all days for this person
            for (int day = 0; day < numDays; day++) {
                sum = sum + moods[person][day];
            }
            // Calculate and print average
            double avg = (double) sum / numDays;
            System.out.printf("Person #%d  : %.1f\n", person, avg);
        }
        System.out.println();
    }
    
    // ========== e) Days with highest mood ==========
    public static void showBestDays(int[][] moods, int numPeople, int numDays) {
        // First, calculate daily averages again
        double[] dailyAvg = new double[numDays];
        
        for (int day = 0; day < numDays; day++) {
            int sum = 0;
            for (int person = 0; person < numPeople; person++) {
                sum = sum + moods[person][day];
            }
            dailyAvg[day] = (double) sum / numPeople;
        }
        
        // Find the highest average
        double highest = dailyAvg[0];
        for (int day = 1; day < numDays; day++) {
            if (dailyAvg[day] > highest) {
                highest = dailyAvg[day];
            }
        }
        
        // Print days that have the highest average
        System.out.printf("e) Days with the highest average mood (%.1f) : ", highest);
        for (int day = 0; day < numDays; day++) {
            if (dailyAvg[day] == highest) {
                System.out.print(" " + day);
            }
        }
        System.out.println("\n");
    }
    
    // ========== f) Percentage of each mood level ==========
    public static void showMoodPercentages(int[][] moods, int numPeople, int numDays) {
        System.out.println("f) Percentage of mood levels:");
        
        // Count how many times each mood appears (1-5)
        int[] moodCount = new int[6]; // Index 0 not used, 1-5 for moods
        
        // Go through all moods and count them
        for (int person = 0; person < numPeople; person++) {
            for (int day = 0; day < numDays; day++) {
                int mood = moods[person][day];
                moodCount[mood] = moodCount[mood] + 1;
            }
        }
        
        // Calculate total entries
        int total = numPeople * numDays;
        
        // Print percentages from mood 5 to 1
        for (int mood = 5; mood >= 1; mood--) {
            double percentage = (moodCount[mood] * 100.0) / total;
            System.out.printf("Mood #%d: %.1f%%\n", mood, percentage);
        }
        System.out.println();
    }
    
    // ========== g) People with emotional disorders ==========
    public static void showEmotionalProblems(int[][] moods, int numPeople, int numDays) {
        System.out.println("g) People with emotional disorders:");
        
        boolean foundSomeone = false;
        
        // Check each person
        for (int person = 0; person < numPeople; person++) {
            // Count consecutive days with low mood (< 3)
            int longestStreak = 0;
            int currentStreak = 0;
            
            for (int day = 0; day < numDays; day++) {
                if (moods[person][day] < 3) {
                    // Low mood - increase streak
                    currentStreak = currentStreak + 1;
                    // Update longest if needed
                    if (currentStreak > longestStreak) {
                        longestStreak = currentStreak;
                    }
                } else {
                    // Good mood - reset streak
                    currentStreak = 0;
                }
            }
            
            // If 2 or more consecutive low days, they have a problem
            if (longestStreak >= 2) {
                System.out.printf("Person #%d  : %d consecutive days\n", person, longestStreak);
                foundSomeone = true;
            }
        }
        
        if (foundSomeone == false) {
            System.out.println("nobody");
        }
        System.out.println();
    }
    
    // ========== h) Display mood charts ==========
    public static void showMoodCharts(int[][] moods, int numPeople, int numDays) {
        System.out.println("h) People's Mood Level Charts:");
        
        // Create a chart for each person
        for (int person = 0; person < numPeople; person++) {
            System.out.printf("Person #%d:\n", person);
            
            // Find min and max mood for this person
            int minMood = moods[person][0];
            int maxMood = moods[person][0];
            
            for (int day = 0; day < numDays; day++) {
                if (moods[person][day] < minMood) {
                    minMood = moods[person][day];
                }
                if (moods[person][day] > maxMood) {
                    maxMood = moods[person][day];
                }
            }
            
            // Draw the chart from top (max) to bottom (min)
            for (int moodLevel = maxMood; moodLevel >= minMood; moodLevel--) {
                System.out.printf("%4d |", moodLevel);
                
                // For each day, print * if mood matches this level
                for (int day = 0; day < numDays; day++) {
                    if (moods[person][day] == moodLevel) {
                        System.out.print("*");
                    } else {
                        System.out.print(" ");
                    }
                }
                System.out.println();
            }
            
            // Draw bottom line
            System.out.print("Mood +");
            for (int i = 0; i < numDays; i++) {
                System.out.print("-");
            }
            System.out.println();
            
            // Draw day numbers at bottom
            System.out.print("      0");
            int nextNumber = 5;
            while (nextNumber < numDays) {
                for (int i = 0; i < 5; i++) {
                    System.out.print(" ");
                }
                System.out.print(nextNumber);
                nextNumber = nextNumber + 5;
            }
            System.out.println("\n");
        }
    }
    
    // ========== i) Recommended therapy ==========
    public static void showTherapyRecommendations(int[][] moods, int numPeople, int numDays) {
        System.out.println("i) Recommended therapy:");
        
        // Check each person
        for (int person = 0; person < numPeople; person++) {
            // Count longest streak of low mood days
            int longestStreak = 0;
            int currentStreak = 0;
            
            for (int day = 0; day < numDays; day++) {
                if (moods[person][day] < 3) {
                    currentStreak = currentStreak + 1;
                    if (currentStreak > longestStreak) {
                        longestStreak = currentStreak;
                    }
                } else {
                    currentStreak = 0;
                }
            }
            
            // Recommend therapy based on streak length
            if (longestStreak >= 5) {
                System.out.printf("Person #%d  : psychological support\n", person);
            } else if (longestStreak >= 2) {
                System.out.printf("Person #%d  : listen to music\n", person);
            }
            // If less than 2, no recommendation needed
        }
        System.out.println();
    }
    
    // ========== j) Most similar people ==========
    public static void showSimilarPeople(int[][] moods, int numPeople, int numDays) {
        System.out.print("j) People with the most similar moods: ");
        
        int maxSimilarity = 0;
        int bestPerson1 = -1;
        int bestPerson2 = -1;
        
        // Compare every pair of people
        for (int person1 = 0; person1 < numPeople - 1; person1++) {
            for (int person2 = person1 + 1; person2 < numPeople; person2++) {
                
                // Count how many days they have same mood
                int sameDays = 0;
                for (int day = 0; day < numDays; day++) {
                    if (moods[person1][day] == moods[person2][day]) {
                        sameDays = sameDays + 1;
                    }
                }
                
                // If this is the best similarity so far, remember it
                if (sameDays > maxSimilarity) {
                    maxSimilarity = sameDays;
                    bestPerson1 = person1;
                    bestPerson2 = person2;
                }
            }
        }
        
        // Print result
        if (maxSimilarity > 0) {
            System.out.printf("(Person #%d and Person #%d have the same mood on %d days)\n", 
                bestPerson1, bestPerson2, maxSimilarity);
        } else {
            System.out.println("None");
        }
    }
}
