package com.gyula;

import org.opencv.core.*;
import org.opencv.core.Point;
import org.opencv.highgui.HighGui;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;


public class Main {

    public static void main(String[] args) {
        ArrayList<Integer> dobások = new ArrayList<>();
        int sum = 0;
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        
        for (int i = 10; i <21 ; i++) {
            String képszám = "D:\\Kocka/Kocka" + i + ".JPG";
            Mat kép = Imgcodecs.imread(képszám, Imgcodecs.IMREAD_COLOR);
            int x = 0;
            Mat szürke = new Mat();
            Imgproc.cvtColor(kép, szürke, Imgproc.COLOR_BGR2GRAY);
            Imgproc.medianBlur(szürke, szürke, 3);
            Mat körök = new Mat();
            Imgproc.HoughCircles(szürke, körök, Imgproc.HOUGH_GRADIENT, 1.0,
                (double)szürke.rows()/50,
                60.0, 9.0, 3, 5);

            for (x = 0; x < körök.cols(); x++) {
                double[] c = körök.get(0, x);
                Point center = new Point(Math.round(c[0]), Math.round(c[1]));
                Imgproc.circle(kép, center, 1, new Scalar(0,100,100), 3, 8, 0 );
                int radius = (int) Math.round(c[2]);
                Imgproc.circle(kép, center, radius, new Scalar(255,0,255), 3, 8, 0 );
            }
            dobások.add(x);
            System.out.println("\nA pontok száma: " + x);
            HighGui.imshow("A dobókockák", kép);
            HighGui.waitKey(3000);

        }
        for (Integer értékek : dobások){
            sum+= értékek;
        }
        System.out.println("\nAz összes dobás érétke együtt: " + sum);

        System.exit(0);



    }
}
