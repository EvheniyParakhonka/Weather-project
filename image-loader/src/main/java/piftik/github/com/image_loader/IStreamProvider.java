package piftik.github.com.image_loader;

import java.io.IOException;
import java.io.InputStream;

public interface IStreamProvider<T> {
    InputStream get(T pPath) throws IOException;
}
