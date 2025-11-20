package model;

public interface GerenciaCSV {

	String toCSV();
	
	static Object fromCSV(String linha) {
        return null;
    }
}