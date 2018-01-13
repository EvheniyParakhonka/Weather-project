package piftik.github.com.weatherproject.models.dbHelpers;

public interface IDBHelperForModel<Model> {
    long add(Model model);

    Model get(long id);

    int delete(long id);

}
