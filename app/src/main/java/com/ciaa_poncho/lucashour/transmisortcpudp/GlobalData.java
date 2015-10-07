package com.ciaa_poncho.lucashour.transmisortcpudp;

public class GlobalData {

	private static GlobalData singleton_instance = null;
	private String ip_address;
	private int port_number;

	protected GlobalData(){
		/* Aplicación de patrón Singleton para mantener una única instancia de la clase
		 * accesibles desde cualquier sector de la applicación */
	}

	public static GlobalData getInstance() {
		if(singleton_instance == null) {
			singleton_instance = new GlobalData();
		}
		return singleton_instance;
	}
	
	public void setPortNumber(int num){
		port_number = num;
	}

	public int getPortNumber(){
		return port_number;
	}

	public void setIpAddress(String address){
		ip_address = address;
	}

	public String getIpAddress(){
		return ip_address;
	}

}
