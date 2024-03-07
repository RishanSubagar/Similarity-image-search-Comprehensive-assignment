// Rishan Subagar 300287082
// Azaan Khan 3000304561

import java.io.File;
import java.io.FileNotFoundException;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

public class ColorHistogram {
    private double[] histogram;
    private int d;
    private ColorImage image;

    // Initialise an empty histogram with a certain depth
    public ColorHistogram(int d) {
        this.d = d;

        int bins = (int) Math.pow(2, d * 3);
        double[] histogram = new double[bins];

        this.histogram = histogram;
    }

    // Intialise a histogram from an existing histogram representation in a text
    // file
    public ColorHistogram(String filename) throws FileNotFoundException {
        File file = new File(filename);
        Scanner scanner = new Scanner(file);

        int bins = scanner.nextInt();
        double[] histogram = new double[bins];

        for (int i = 0; i < bins; i++) {
            int nextInt = scanner.nextInt();
            histogram[i] = nextInt;
        }

        this.histogram = histogram;

        scanner.close();
    }

    // Create an histogram using an image
    public void setImage(ColorImage image) {
        this.image = image;

        int[][][] realImage = this.image.getImage();
        int width = this.image.getWidth();
        int height = this.image.getHeight();

        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                int R = realImage[row][col][0];
                int G = realImage[row][col][1];
                int B = realImage[row][col][2];
                this.histogram[(R << (2 * d)) + (G << d) + B]++;
            }
        }
    }

    // Return the normalised histogram
    public double[] getHistogram() {
        double[] normalisedHistorgram = this.histogram;
        // Compute total sum of histogram bins
        double totalCount = 0;
        for (double value : this.histogram) {
            totalCount += value;
        }

        // Divide each bin by total sum to normalise
        for (int i = 0; i < this.histogram.length; i++) {
            normalisedHistorgram[i] /= totalCount;
        }
        return normalisedHistorgram;
    }

    // Compare a histogram using histogram intersection to the current instance
    public double compare(ColorHistogram histogram) {

        double[] histogram1 = getHistogram();
        double[] histogram2 = histogram.getHistogram();

        double intersection = 0;
        for (int i = 0; i < this.histogram.length; i++) {
            intersection += Math.min(histogram1[i], histogram2[i]);
        }

        return intersection;
    }

    // Save the histogram instance to a text file in the savedHistograms directory
    public void save(String filename) {
        String filePath = String.format("savedHistograms/%s", filename);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(Arrays.toString(histogram));
            System.out.println("Histogram saved at savedHistograms/" + filename);
        } catch (IOException e) {
            System.err.println("An error occurred while writing to the file: " + e.getMessage());
        }
    }
}
