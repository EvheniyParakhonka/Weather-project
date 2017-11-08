package piftik.github.com.weatherproject.request.http;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

 public class ReadFromStream implements IReadFromStream {

    @Override
    public String readFromStream(final InputStream pInputStream) throws IOException {

        ByteArrayOutputStream result;
        try {
            result = new ByteArrayOutputStream();
            if (pInputStream != null) {

                final byte[] buffer = new byte[1024];
                int length;
                while ((length = pInputStream.read(buffer)) != -1) {
                    result.write(buffer, 0, length);
                }

            }
        } finally {
            if (pInputStream != null) {
                pInputStream.close();
            }
        }

        return result.toString();
    }
}
