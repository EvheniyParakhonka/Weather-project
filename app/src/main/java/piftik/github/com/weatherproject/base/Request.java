package piftik.github.com.weatherproject.base;

public class Request<T> {
    private Operations id;
    private final T object;

    Request(final Operations id, final T object) {
        this.id = id;
        this.object = object;
    }

    public Operations getId() {
        return id;
    }

    public void setId(final Operations id) {
        this.id = id;
    }

    T getObject() {
        return object;
    }
}
