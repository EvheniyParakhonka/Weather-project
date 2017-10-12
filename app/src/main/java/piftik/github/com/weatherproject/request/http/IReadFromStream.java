package piftik.github.com.weatherproject.request.http;

import java.io.IOException;
import java.io.InputStream;

public interface IReadFromStream {
    String readFromStream(InputStream inputStream) throws IOException;
}
