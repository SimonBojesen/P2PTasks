package UDP;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

public class ImgFunctionality {
    // Converts a .jpg file to a ByteArray
    public byte[] ImageToByteArray(File img) throws IOException {
        BufferedImage bImg = ImageIO.read(img);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ImageIO.write(bImg, "jpg", bos);
        byte[] data = bos.toByteArray();
        return data;
    }

    // Convert a ByteArray back into a .jpg file and saves it to the
    // path that is specified through the arguments
    public void ByteArrayToImg(byte[] imgData, String path, String filename) throws IOException {
        ByteArrayInputStream bis = new ByteArrayInputStream(imgData);
        BufferedImage bImg = ImageIO.read(bis);
        ImageIO.write(bImg, "jpg", new File(path + "\\" + filename));
    }
}
