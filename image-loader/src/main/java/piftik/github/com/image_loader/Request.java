package piftik.github.com.image_loader;

import android.widget.ImageView;

import java.lang.ref.WeakReference;

final class Request {
    String mUrl;
    WeakReference<ImageView> mTarget;
    int mWidth;
    int mHeight;

    private Request(final Builder pBuilder) {
        mUrl = pBuilder.mUrl;
        mTarget = pBuilder.mTarget;
    }

    public static final class Builder {
        private final ImageLoader mImageLoader;
        private String mUrl;
        private WeakReference<ImageView> mTarget;

        Builder(final ImageLoader pImageLoader) {
            this.mImageLoader = pImageLoader;
        }

        Builder load(final String pUrlS) {
            mUrl = pUrlS;
            return this;
        }

        public void into(final ImageView pView) {
            mTarget = new WeakReference<>(pView);
            mImageLoader.enqueue(this.build());
        }

        Request build() {
            return new Request(this);
        }
    }
}
