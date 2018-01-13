package piftik.github.com.weatherproject.base;

public class RequestAdapter<Model> {

    public Request get(final Long id) {
        return new Request<>(Operations.KEY_GET, id);
    }

    public Request delete(final Long id) {
        return new Request<>(Operations.KEY_DELETE, id);
    }

    public Request add(final Model model) {
        return new Request<>(Operations.KEY_ADD, model);
    }

}
