package model;

import java.io.IOException;
import java.util.List;

public interface InterfaceCRUD<T>  {
    
    void inserir(T objeto) throws IOException;
    
    List<T> ler() throws IOException;
    
    void atualizar(T objeto) throws IOException;
    
    void deletar(T objeto) throws IOException;
}