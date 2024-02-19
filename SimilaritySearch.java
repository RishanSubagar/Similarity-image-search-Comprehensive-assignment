import java.io.File;

public class SimilaritySearch {
    public static void main(String[] args) { 
        // Check if the correct number of command-line arguments is provided
        if (args.length != 2) {
            System.out.println("Usage: java SimilaritySearch <image_filename> <image_dataset_directory>");
            System.exit(1);
        }

        // Retrieve the command-line arguments
        String queryImageFilename = args[0];
        String imageDatasetDirectory = args[1];

        // Check if the specified image dataset directory exists
        File datasetDirectory = new File(imageDatasetDirectory);
        if (!datasetDirectory.exists() || !datasetDirectory.isDirectory()) {
            System.out.println("Error: Image dataset directory does not exist or is not a directory.");
            System.exit(1);
        }
        
        ColorImage queryImage = new ColorImage(queryImageFilename);
        queryImage.reduceColor(3);
        ColorHistogram histogram = new ColorHistogram(queryImage.getDepth());
        int height = queryImage.getHeight();
        int width = queryImage.getWidth();
        int[] pixels;
        int j = 0;
        for (int i = 0; i < height*width / 3; i++) {
        	pixels = queryImage.getPixel(i, j);
        	if (histogram[i] == ((pixels[0] << (2*queryImage.getDepth()) + (pixels[1] << queryImage.getDepth()) + pixels[2]))); 
        }
        for (int j = 0; j < height; j++) {
            for (int i = 0; i < width; i++) {
                if (i == )
            }
        }
        
    }
}