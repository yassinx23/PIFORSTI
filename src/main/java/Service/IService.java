package Service;

import models.Application;

public interface IService<T> {

    void ajouter (T a);
    void supprimer (T a);
    void modifier (T a);
    List<T> recuperer();
}
