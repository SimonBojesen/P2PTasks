package whoisclient;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

public class ImgFunctionality {
    public byte[] ImageToByteArray(File img) throws IOException {
        BufferedImage bImg = ImageIO.read(img);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ImageIO.write(bImg, "jpg", bos);
        byte[] data = bos.toByteArray();
        return data;
    }

    public BufferedImage ByteArrayToImg(byte[] imgData) throws IOException {
        ByteArrayInputStream bis = new ByteArrayInputStream(imgData);
        BufferedImage bImg = ImageIO.read(bis);
        ImageIO.write(bImg, "jpg", new File("output.jpg"));
        return bImg;
    }
}
