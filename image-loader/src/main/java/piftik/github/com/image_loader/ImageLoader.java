package piftik.github.com.image_loader;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v4.util.LruCache;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;

public enum ImageLoader {
    INSTANCE;

    private static final int MAX_MEMORY_FOR_IMAGES = 64 * 1024 * 1024;

    private final BlockingDeque<Request> queue;
    private final LruCache<String, Bitmap> lruCache;
    private final ExecutorService executorService;
    private final Object lock = new Object();

    ImageLoader() {
        queue = new LinkedBlockingDeque<>();
        executorService = Executors.newFixedThreadPool(3);
        lruCache = new LruCache<String, Bitmap>(getCacheSize()) {

            @Override
            protected int sizeOf(final String pKey, final Bitmap pBitmap) {
                return pKey.length() + pBitmap.getByteCount();
            }

        };
    }

    public Request.Builder load(final String pUrl) {
        return new Request.Builder(this).load(pUrl);
    }

    private int getCacheSize() {
        return Math.min((int) (Runtime.getRuntime().maxMemory() / 4), MAX_MEMORY_FOR_IMAGES);
    }

    @SuppressLint("StaticFieldLeak")
    private void dispatchLoading() {

        new ImageResultAsyncTask().executeOnExecutor(executorService);
    }

    private void processImageResult(final Result pResult) {

        if (pResult != null) {
            final Request request = pResult.getRequest();
            final ImageView imageView = request.mTarget.get();

            if (imageView != null) {
                final Object tag = imageView.getTag();

                if (tag != null && tag.equals(request.mUrl)) {
                    imageView.setImageBitmap(pResult.getBitmap());
                }
            }
        }
    }

    void enqueue(final Request pRequest) {
        final ImageView imageView = pRequest.mTarget.get();

        if (imageView == null) {
            return;
        }

        imageView.setImageBitmap(null);

        if (imageHasSize(pRequest)) {
            imageView.setTag(pRequest.mUrl);
            queue.addFirst(pRequest);
            dispatchLoading();
        } else {
            deferImageRequest(pRequest);
        }
    }

    private void deferImageRequest(final Request pRequest) {
        final ImageView imageView = pRequest.mTarget.get();
        imageView.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                final ImageView view = pRequest.mTarget.get();

                if (view == null) {
                    return true;
                }

                view.getViewTreeObserver().removeOnPreDrawListener(this);

                if (view.getWidth() > 0 && view.getHeight() > 0) {
                    pRequest.mWidth = view.getWidth();
                    pRequest.mHeight = view.getHeight();
                    enqueue(pRequest);
                }
                return true;
            }
        });
    }

    private boolean imageHasSize(final Request pRequest) {

        if (pRequest.mWidth > 0 && pRequest.mHeight > 0) {
            return true;
        }

        final ImageView imageView = pRequest.mTarget.get();

        if (imageView != null && imageView.getWidth() > 0 && imageView.getHeight() > 0) {
            pRequest.mWidth = imageView.getWidth();
            pRequest.mHeight = imageView.getHeight();
            return true;
        }

        return false;
    }

    private Bitmap getScaledBitmap(final InputStream pInputStream, final int w, final int h) throws IOException {


        final BitmapFactory.Options options = new BitmapFactory.Options();
        final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(pInputStream.available());
        final byte[] chunk = new byte[1 << 16];
        int bytesRead;

        while ((bytesRead = pInputStream.read(chunk)) > 0) {
            byteArrayOutputStream.write(chunk, 0, bytesRead);
        }
        final byte[] bytes = byteArrayOutputStream.toByteArray();

        options.inJustDecodeBounds = true;
        BitmapFactory.decodeByteArray(bytes, 0, bytes.length, options);
        options.inSampleSize = calculateInSampleSize(options, w, h);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length, options);
    }

    private static int calculateInSampleSize(final BitmapFactory.Options pOptions, final int pReqWidth, final int pReqHeight) {
        // Raw mHeight and mWidth of image
        final int height = pOptions.outHeight;
        final int width = pOptions.outWidth;
        int inSampleSize = 1;

        if ((height / inSampleSize) >= height && (width / inSampleSize) >= width) {

            int halfHeight = height / 2;
            int halfWidth = width / 2;

            while (halfHeight > pReqHeight
                && halfWidth > pReqWidth) {
                inSampleSize *= 2;
                halfHeight /= 2;
                halfWidth /= 2;
            }
        }
        return inSampleSize;
    }


    @SuppressLint("StaticFieldLeak")
    private class ImageResultAsyncTask extends AsyncTask<Void, Void, Result> {

        @Override
        protected Result doInBackground(final Void... voids) {

            Result result = null;

            try {

                final Request request = queue.takeFirst();

                result = new Result(request);

                synchronized (lock) {
                    final Bitmap bitmap = lruCache.get(request.mUrl);
                    if (bitmap != null) {
                        result.setBitmap(bitmap);
                        return result;
                    }
                }

                final InputStream inputStream = new StreamProvider().get(request.mUrl);

                final Bitmap bitmap = getScaledBitmap(inputStream, request.mHeight, request.mWidth);

                if (bitmap != null) {
                    result.setBitmap(bitmap);
                    synchronized (lock) {
                        lruCache.put(request.mUrl, bitmap);
                    }
                } else {
                    throw new IllegalStateException("Bitmap is null");
                }

                return result;
            } catch (final Exception pE) {

                if (result != null) {
                    result.setException(pE);
                }
                return result;
            }
        }

        @Override
        protected void onPostExecute(final Result pResult) {
            processImageResult(pResult);
        }

    }
}
