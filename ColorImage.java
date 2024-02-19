import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ColorImage {
    private int width;
    private int height;
    private int depth; // Number of bits per pixel
    private int[][][] pixels; // Array representation of pixel values 

    // Constructor that creates an image from ppm file 
    public ColorImage(String filename) {
        if (filename.toLowerCase().endsWith(".ppm")) {
            readPpmImage(filename);
        } else {
            System.err.println("Unsupported image format.");
        }
    }

    private void readPpmImage(String filename) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            // Skip the first two lines
            for (int i = 0; i < 2; i++) {
                br.readLine();
            }

            String[] dimensions = br.readLine().split(" ");
            width = Integer.parseInt(dimensions[0]);
            height = Integer.parseInt(dimensions[1]);
            depth = Integer.parseInt(br.readLine());
            pixels = new int[width][height][3];

            // Read pixel values
            for (int j = 0; j < height; j++) {
                for (int i = 0; i < width; i++) {
                    pixels[i][j][0] = Integer.parseInt(br.readLine());
                    pixels[i][j][1] = Integer.parseInt(br.readLine());
                    pixels[i][j][2] = Integer.parseInt(br.readLine());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Getter methods for image attributes
    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getDepth() {
        return depth;
    }

    // Get the 3-channel value of pixel at column i, row j
    public int[] getPixel(int i, int j) {
        return pixels[i][j];
    }

    // Reduce the color space to a d-bit representation
    public void reduceColor(int d) {
        //int scale = (int) Math.pow(2, 8 - d);
        depth = 8 - d;

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                for (int c = 0; c < 3; c++) {
                    pixels[i][j][c] = pixels[i][j][c] >> (8 - d);
                }
            }
        }
    }
}
