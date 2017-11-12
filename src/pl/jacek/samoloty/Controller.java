package pl.jacek.samoloty;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.image.ImageView;
import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;
import org.opencv.imgproc.Moments;

import java.net.URL;
import java.util.*;

public class Controller implements Initializable{

	@FXML
	private ImageView image1;

	@FXML
	private ImageView image2;

	@FXML
	private Button button1;

	@FXML
	private ComboBox<String> myCombobox;

	private ImageReader imageReader;

	public void loadImage(ActionEvent actionEvent) {
		System.out.println("> Loading image");
		final Mat mat = imageReader.loadImageInColor(myCombobox.getValue());
		final Mat resizedMat = new Mat();
		final Mat changedImage = new Mat();
		Imgproc.resize(mat,resizedMat,new Size(600,300));
		resizedMat.copyTo(changedImage);

		Imgproc.cvtColor(changedImage,changedImage,Imgproc.COLOR_BGR2GRAY);

		Imgproc.GaussianBlur(changedImage,changedImage,new Size(3,3),0);
		Imgproc.threshold(changedImage,changedImage,140,255,Imgproc.THRESH_BINARY);

		findAndDrawContours(resizedMat, changedImage);





		Utils.onFXThread(image1.imageProperty(), Utils.mat2Image(resizedMat));
		Utils.onFXThread(image2.imageProperty(), Utils.mat2Image(changedImage));

		System.out.println("< Finished");
	}

	private void findAndDrawContours(Mat resizedMat, Mat changedImage) {
		Imgproc.Canny(changedImage,changedImage,2, 2*2, 3, false);
		List<MatOfPoint> contours = getContours(changedImage);
		Random random = new Random();
		for (MatOfPoint contour : contours) {
			Imgproc.drawContours(resizedMat, Arrays.asList(contour),-1,new Scalar(random.nextInt(255),random.nextInt(255),random.nextInt(255)));
			final Moments moments = Imgproc.moments(contour);
			int cX =  (int) (moments.m10 / moments.m00);
			int cY =  (int) (moments.m01 / moments.m00);

			Imgproc.circle(resizedMat,new Point(cX,cY),2,new Scalar(50,255,255),3);
		}
	}

	private List<MatOfPoint> getContours(Mat changedImage) {
		List<MatOfPoint> contours = new ArrayList<>();
		Mat hierarchy = new Mat();
		Imgproc.findContours(changedImage,contours,hierarchy,Imgproc.RETR_EXTERNAL,Imgproc.CHAIN_APPROX_SIMPLE);

		List<MatOfPoint> result = new ArrayList<>();
		for (MatOfPoint contour : contours) {
			final double contourArea = Imgproc.contourArea(contour);
			if(contourArea >=0){
				result.add(contour);
			}
		}
		return result;

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		imageReader = new ImageReader();
	}
}
