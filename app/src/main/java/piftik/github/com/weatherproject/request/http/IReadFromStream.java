package piftik.github.com.weatherproject.request.http;

import java.io.IOException;
import java.io.InputStream;

interface IReadFromStream {
    String readFromStream(InputStream inputStream) throws IOException;
}
