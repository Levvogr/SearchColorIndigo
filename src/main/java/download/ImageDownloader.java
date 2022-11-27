package download;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

public class ImageDownloader {
    public static void downloadImage(String fileName, String strImageURL) throws IOException {
        URL urlImage = new URL(strImageURL);
        InputStream in = urlImage.openStream();
        byte[] buffer = new byte[4096];
        int n = -1;
        OutputStream os = new FileOutputStream(fileName);
        while ( (n = in.read(buffer)) != -1 )
            os.write(buffer, 0, n);
        os.close();
    }
}
