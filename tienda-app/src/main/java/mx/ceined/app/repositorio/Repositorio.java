package mx.ceined.app.repositorio;

import java.util.List;

public interface Repositorio<T> {
    List<T> listar();
    T porId(Integer id);
    void guardar(T t);
    void eliminar(Integer id);
}
