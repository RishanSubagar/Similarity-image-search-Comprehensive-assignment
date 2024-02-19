// Rishan Subagar 300287082
// Azaan Khan 3000304561

import java.io.*;

public class ColorHistogram {

	private int[] histogram;
	private ColorImage image;
	
	public ColorHistogram(int d) {
		
		histogram = new int[(int) Math.pow(2, d*3)];
		
    }
	
	public ColorHistogram(String filename) {
		ColorImage temp = new ColorImage(filename);
		int d = temp.getDepth();
		histogram = new int[(int) Math.pow(2, d*3)];
    }
	
	public void setImage(ColorImage image) {
        this.image = image;
    }
	
	public double[] getHistogram() {
        int totalPixels = image.getWidth() * image.getHeight();
        double[] normalizedHistogram = new double[histogram.length];
        for (int i = 0; i < histogram.length; i++) {
            normalizedHistogram[i] = (double) histogram[i] / totalPixels;
        }
        return normalizedHistogram;
    }

    public double[] getDataHistogram() {
        int length = histogram.length;
        double[] doubleArray = new double[length];

        for (int i = 0; i < length; i++) {
            doubleArray[i] = (double) histogram[i]; // Casting each element to double
        }

        return doubleArray;
    }
	
	public int getHistLength() {
		return histogram.length;
	}
	
	public double compare(ColorHistogram hist) {
        double[] hist1 = this.getHistogram();
        double[] hist2 = hist.getDataHistogram();
        double intersection = 0.0;
        for (int i = 0; i < hist1.length; i++) {
            intersection += Math.min(hist1[i], hist2[i]);
        }
        return intersection;
    }
	
	public void insertCustom(int index, int amount) {
		histogram[index] = amount;
	}

    public void insert(int index){
        histogram[index] = histogram[index]+1;
    }
	
	public void save(String filename) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
            for (int value : histogram) {
                writer.write(Integer.toString(value));
                writer.newLine();
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	
}
