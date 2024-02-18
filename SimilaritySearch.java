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
        
    }
}