package bk.control;

import java.util.List;

public interface Dao <T> {

    List<Object> exportData();

    void importData(List <String[]> list);

    List<T> getAll();

    void addNew(T t);

    void delete(T t);

}
