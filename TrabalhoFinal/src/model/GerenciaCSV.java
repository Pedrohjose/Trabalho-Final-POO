package model;

public interface GerenciaCSV {

	String toCSV();
	
	void fromCSV(String linha);

}