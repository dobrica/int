package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import beans.Device;
import beans.User;

public class Model {

	private static Model instance = null;
	
	public ArrayList<User> users;
	public ArrayList<Device> devices;
	public ArrayList<String> admins;
	
	protected Model() {
		users = new ArrayList<User>();
		devices = new ArrayList<Device>();
		admins = new ArrayList<String>();
		load();
	}

	public static Model getInstance() {
		if (instance == null) {
			instance = new Model();
		}
		return instance;
	}
	
	public boolean authUser(String username, String password){
		for (User u : users)
			if(username.equals(u.getUsername()) && password.equals(u.getPassword()))
				return true;
		return false;
	}
	
	public boolean addNewUser(User u, String confirm_pass){
		if (!authUser(u.getUsername(), u.getPassword()))
			if (u.getPassword().equals(confirm_pass)){
				users.add(u);
				return true;
			}
		return false;
	}
	
	public boolean isLoggedOn(String token){
		for (String t : admins)
			if (t.equals(token))
				return true;
		return false;
	}
	
	public boolean logoff(String token){
		for (String t : admins)
			if (t.equals(token)){
				admins.remove(token);
				return true;
			}
		return false;
	}
	
	public String addAdmin(String token){ 
		// username as token as it is unique
		admins.add(token);
		return token;
	}
	
	public boolean containsDevice(Device d){
		for (Device device : devices) 
			if (device.getName().equals(d.getName()))
				return true;
		return false;
	}
	
	public Device getDeviceByName(String name){
		for (Device device : devices) 
			if (device.getName().equals(name))
				return device;
		return null;
	}
	
	public boolean removeDevice(String name){
		for (Device device : devices) 
			if (device.getName().equals(name)){
				devices.remove(device);
				return true;
			}
		return false;
	}
	
	public boolean addNewDevice(Device d){
		if (!containsDevice(d)) {
			devices.add(d);
			return true;
		}
		return false;
	} 

	public String devicesToJson(){
		String res =",\"_devices\": [ ";
		for (Device d : devices) {
			res+=d.toJSON(null)+",";
		}
		res = res.substring(0, res.length() - 1);
		res+="]";
		return res;
	}

	public void save() {
		try {
			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("data/users.txt"));
			out.writeObject(users);
			out.close();
			
			out = new ObjectOutputStream(new FileOutputStream("data/devices.txt"));
			out.writeObject(devices);
			out.close();
			
			out = new ObjectOutputStream(new FileOutputStream("data/admins.txt"));
			out.writeObject(admins);
			out.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("unchecked")
	public void load() {
		try {
			File file = new File("data/users.txt");
			if (file.exists()){
				ObjectInputStream in = new ObjectInputStream(new FileInputStream("data/users.txt"));
				users = (ArrayList<User>) in.readObject();
				in.close();
			}
			
			file = new File("data/devices.txt");
			if (file.exists()){
				ObjectInputStream in = new ObjectInputStream(new FileInputStream("data/devices.txt"));
				devices = (ArrayList<Device>) in.readObject();
				in.close();
			}
			
			file = new File("data/admins.txt");
			if (file.exists()){
				ObjectInputStream in = new ObjectInputStream(new FileInputStream("data/admins.txt"));
				admins = (ArrayList<String>) in.readObject();
				in.close();
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
}
