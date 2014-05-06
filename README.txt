Server java/jersey 
	build/run with maven/jetty
	Server\RESTfulExample\messages
	links for client if messages.* files are set for genymotion android emulator
	change this 10.0.3.2:8080 for localhost:port for devices you will use for testing 
	if you won't use genymotion

Client android 
	also change BASE_URL 10.0.3.2:8080 for localhost:port 
	if you are not using genymotion android emulator to run app
	you can find that in this Activity in client code
	android client\RESTClient\src\com\example\restclient\MainActivity.java
	// BASE_URL 
	line 49 	String url = "http://10.0.3.2:8080/rest/home";
	
		