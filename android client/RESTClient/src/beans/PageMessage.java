package beans;

import java.util.ArrayList;
import java.util.Iterator;

public class PageMessage {
	
	public String title;
	public String backgroundUrl;
	public String token;
	public ArrayList<Link> links;
	public ArrayList<Element> elements;
	public ArrayList<Device> devices;
	public ArrayList<String> filters;
	public int marginTop;
	
	public PageMessage() {
		title = new String();
		links = new ArrayList<Link>();
		elements = new ArrayList<Element>();
		devices = new ArrayList<Device>();
		filters = new ArrayList<String>();
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public Iterator<Link> getLinksIter(){
		return links.iterator();
	}
	
	public void addToLinks(Link link){
		links.add(link);
	}
	
	public Iterator<Element> getElementsIter(){
		return elements.iterator();
	}
	
	public void addToElements(Element el){
		elements.add(el);
	}
	
	public Element findElementByText(String rel){
		for (Element el : elements)
			if (el.getText().equals(rel))
				return el;
		return null;
	}
	
	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Link findLinkByRel(String rel){
		for (Link lnk : links)
			if (lnk.getRel().equals(rel))
				return lnk;
		return null;
	}
	
	public Iterator<Device> getDevicesIter(){
		return devices.iterator();
	}
	
	public void addToDevices(Device d){
		devices.add(d);
	}
	
	public Device findDeviceByName(String name){
		for (Device d : devices)
			if (d.getName().equals(name))
				return d;
		return null;
	}
	
	public Iterator<String> getFiltersIter(){
		return filters.iterator();
	}
	
	public void addToFilters(String filter){
		filters.add(filter);
	}

	public String getBackgroundUrl() {
		return backgroundUrl;
	}

	public void setBackgroundUrl(String backgroundUrl) {
		this.backgroundUrl = backgroundUrl;
	}

	public int getMarginTop() {
		return marginTop;
	}

	public void setMarginTop(int marginTop) {
		this.marginTop = marginTop;
	}

	public String toString(){
		String temp="";
		for (Link l: links) {
			temp+="\nhref: "+l.getHref()+"\nrel: "+l.getRel()+"\nmethod: "+l.getMethod();
		}
		return "title: "+title+temp;
	}
	
}
