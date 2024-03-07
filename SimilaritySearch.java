import java.io.FileNotFoundException;
import java.io.File;
import java.util.PriorityQueue;
import java.util.Comparator;

public class SimilaritySearch {
    private static int K = 5;
    private static int D = 3;

    // Comparator for sorting the intersections in the priority queue
    private static class Intersection implements Comparable<Intersection> {
        double value;
        String identifier;

        public Intersection(double value, String identifier) {
            this.value = value;
            this.identifier = identifier;
        }

        @Override
        public int compareTo(Intersection other) {
            return Double.compare(this.value, other.value);
        }
    }

    public static void main(String[] args) throws FileNotFoundException {

        // Length check the arguments
        if (args.length != 2) {
            System.out.println("Example Usage: java SimilaritySearch q01.ppm imageDataset2_15_20");
            System.exit(1);
        }

        String queryFileName = args[0];
        String datasetFolderName = args[1];

        // image Number
        ColorImage queryImage = new ColorImage(String.format("queryImages/%s", queryFileName));
        queryImage.reduceColor(D); // Reduce created image

        ColorHistogram queryHistogram = new ColorHistogram(D); // Set empty histogram
        queryHistogram.setImage((queryImage)); // Initialise histogram with image

        queryHistogram.save(queryFileName);

        // Priority queue holding the K nearest intersections
        PriorityQueue<Intersection> kNearestIntersections = new PriorityQueue<>(K * 2);

        for (int j = 25; j < 5000; j++) {
            String filePath = String.format("%1$s/%2$s.jpg.txt", datasetFolderName, j);
            File file = new File(filePath);

            // Check if the file exists, since it is not safe to iterate from 25 -> 5000
            // (some numbers dont exist in the dataset)
            if (file.exists()) {
                ColorHistogram datasetHistogram = new ColorHistogram(filePath);
                double intersection = queryHistogram.compare(datasetHistogram); // Calculate intersection

                kNearestIntersections.add(new Intersection(intersection, String.format("%s.jpg.txt", j)));

                if (kNearestIntersections.size() > K) {
                    kNearestIntersections.poll(); // Removes the smallest element
                }

            } else {
                continue; // File doesn't exist, skip
            }
        }

        System.out.println("Top " + K + " intersections for image " + queryFileName + ":");
        Intersection[] intersectionsArray = kNearestIntersections.toArray(new Intersection[0]);

        // Sort to display in descending order
        java.util.Arrays.sort(intersectionsArray, Comparator.reverseOrder());

        for (Intersection intersection : intersectionsArray) {
            System.out.println(intersection.identifier + ": " + intersection.value);
        }
    }
}