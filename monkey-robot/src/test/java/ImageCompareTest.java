import com.yunwow.os.robot.core.utils.ImageUtil;
import lombok.extern.slf4j.Slf4j;

import java.awt.image.BufferedImage;
import java.io.IOException;

@Slf4j
public class ImageCompareTest {

    public static void main(String[] args) throws IOException {
        String aimg = "f:/temp.png";
        String bimg = "f:/temp2.png";
        String cimg = "f:/temp3.png";
        String dimg = "f:/temp4.png";
        String eimg = "f:/temp5.png";
        BufferedImage abuffer = ImageUtil.readImage(aimg);
        BufferedImage bbuffer = ImageUtil.readImage(bimg);
        BufferedImage cbuffer = ImageUtil.readImage(cimg);
        BufferedImage dbuffer = ImageUtil.readImage(dimg);
        BufferedImage ebuffer = ImageUtil.readImage(eimg);

        int imageWidth = 230;
        int imageHeight = 438;


        System.out.println(ImageUtil.compare(abuffer, bbuffer, imageWidth, imageHeight));
        System.out.println(ImageUtil.compare(abuffer, cbuffer, imageWidth, imageHeight));
        System.out.println(ImageUtil.compare(abuffer, dbuffer, imageWidth, imageHeight));
        System.out.println(ImageUtil.compare(abuffer, ebuffer, imageWidth, imageHeight));
    }
}
