package pl.jacek.samoloty;

import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

/**
 * Created by breku on 12.11.17.
 */
public class ImageReader {

	public Mat loadImage(final String fileName){
		final String imagePath = String.format("%s%s", getClass().getResource("img/").getPath(), fileName);
		final Mat imread = Imgcodecs.imread(imagePath, Imgcodecs.CV_LOAD_IMAGE_GRAYSCALE);
		return imread;
	}

	public Mat loadImageInColor(final String fileName){
		final String imagePath = String.format("%s%s", getClass().getResource("img/").getPath(), fileName);
		final Mat imread = Imgcodecs.imread(imagePath, Imgcodecs.CV_LOAD_IMAGE_ANYCOLOR);
		return imread;
	}
}
