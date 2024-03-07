// Rishan Subagar 300287082
// Azaan Khan 3000304561

import java.io.*;
import java.util.Scanner;

public class ColorImage {
    private int width;
    private int height;
    private int depth;
    private int[][][] image;

    // Create an image from a .ppm file.
    public ColorImage(String filename) throws FileNotFoundException {
        File file = new File(filename);
        Scanner scanner = new Scanner(file);

        scanner.nextLine(); // Skip the 'P3' line
        scanner.nextLine(); // Skip the comment line

        this.width = scanner.nextInt();
        this.height = scanner.nextInt();
        this.depth = scanner.nextInt();

        int[][][] image = new int[height][width][3];

        // Read the pixel data
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                image[row][col][0] = scanner.nextInt(); // Red
                image[row][col][1] = scanner.nextInt(); // Green
                image[row][col][2] = scanner.nextInt(); // Blue
            }
        }

        scanner.close();

        this.image = image;
    }

    // Return width
    public int getWidth() {
        return width;
    }

    // Return height
    public int getHeight() {
        return height;
    }

    // Return depth
    public int getDepth() {
        return depth;
    }

    // Return image representation
    public int[][][] getImage() {
        return image;
    }

    // Return the R G B values at a pixel
    public int[] getPixel(int i, int j) {
        int[] arr = new int[3];
        arr[0] = image[i][j][0];
        arr[1] = image[i][j][1];
        arr[2] = image[i][j][2];
        return arr;
    }

    // Reduce existing image to depth d
    public void reduceColor(int d) {
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                image[row][col][0] = image[row][col][0] >> 8 - d;
                image[row][col][1] = image[row][col][1] >> 8 - d;
                image[row][col][2] = image[row][col][2] >> 8 - d;
            }
        }
    }
}
