package piftik.github.com.image_loader;

import android.graphics.Bitmap;

public class Result {
    private final Request request;
    private Bitmap bitmap;
    private Exception exception;

    Result(final Request pRequest) {
        this.request = pRequest;
    }

    void setBitmap(final Bitmap pBitmap) {
        this.bitmap = pBitmap;
    }

    void setException(final Exception pException) {
        this.exception = pException;
    }

    Request getRequest() {
        return request;
    }

    Bitmap getBitmap() {
        return bitmap;
    }

    public Exception getException() {
        return exception;
    }
}
