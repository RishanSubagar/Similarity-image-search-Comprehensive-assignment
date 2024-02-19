import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

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
        File[] listOfFiles = datasetDirectory.listFiles();
        
        if (!datasetDirectory.exists() || !datasetDirectory.isDirectory()) {
            System.out.println("Error: Image dataset directory does not exist or is not a directory.");
            System.exit(1);
        }
        
        ColorImage queryImage = new ColorImage(queryImageFilename);
        ColorImage reducedImage = new ColorImage(queryImageFilename);
        int D = 3;
        reducedImage.reduceColor(D);

        ColorHistogram histogram = new ColorHistogram(D);
        int height = reducedImage.getHeight();
        int width = reducedImage.getWidth();

        for (int i = 0; i < width; i++) {
            for (int j=0; j<height; j++) {
                int[] pixel = reducedImage.getPixel(i, j);
                int index = ((pixel[0] << (2*D)) + (pixel[1] << D) + pixel[2]);
                histogram.insert(index);
            }
        }
        
        ColorHistogram datasetHist = new ColorHistogram(3);
        Double[] similarImages = new Double[5];
        String[] similarImagesName = new String[5];
        
        for (int i = 0; i < listOfFiles.length; i++) {
        	  File file = listOfFiles[i];
        	  if (file.isFile() && file.getName().endsWith(".txt")) {
        		  try (BufferedReader br = new BufferedReader(new FileReader(file))) {
        			br.readLine();
                    String[] line = br.readLine().split(" ");
					for (int j = 0; j < datasetHist.getHistLength(); j++) {
						datasetHist.insertCustom(j, Integer.parseInt(line[j]));
					}

                    histogram.setImage(reducedImage);
					double distance = histogram.compare(datasetHist);
					if (similarImages[4] == null) {
						for (int k = 0; k < similarImages.length; k++) {
							if (similarImages[k] == null) {
								similarImages[k] = distance;
								similarImagesName[k] = file.getName();
							}
						}
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
        	    
        	  } 
        }
    
        for (int i = 0; i<5;i++) {
        	System.out.println(similarImagesName[i]);
        }
        
    }
}