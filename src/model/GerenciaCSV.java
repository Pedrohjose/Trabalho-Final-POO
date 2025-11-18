package model;

public interface GerenciaCSV {

	String toCSV();
	
	Object fromCSV(String linha);
}