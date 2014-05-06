package rest;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import model.Model;
import utility.ReadFile;
import beans.Device;
import beans.User;

@Path("/home")
public class HomeService {

	public Model model = Model.getInstance();
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String getDefaultMessage(@QueryParam("token") String token) throws IOException {
		String msg = ReadFile.getFileContent(new File("messages/Home.txt"));
		msg = msg.substring(0, msg.length() - 1);
		if (token!=null)
			msg += ",\"param\":"+"\"" + token+"\"" + "}";
		else
			msg += ",\"param\":"+"\"" + "1\"" + "}";
	
		return msg;
	}
	
	@GET
	@Path("/login")
	@Produces(MediaType.APPLICATION_JSON)
	public String login(@QueryParam("token") String token) throws IOException {
		String msg = ReadFile.getFileContent(new File("messages/Login.txt"));
		msg = msg.substring(0, msg.length() - 1);
		if (token!=null)
			msg += ",\"param\":"+"\"" + token+"\"" + "}";
		else
			msg += ",\"param\":"+"\"" + "1\"" + "}";
	
		return msg;
	}

	@POST
	@Path("/login")
	@Produces(MediaType.APPLICATION_JSON)
	public String login(@QueryParam("token") String token,
			@QueryParam("username") String username,
			@QueryParam("password") String password) throws IOException {

		if (!model.isLoggedOn(username))
			if (model.authUser(username, password)) {
				String msg = ReadFile.getFileContent(new File("messages/Devices.txt"));
				msg = msg.substring(0, msg.length() - 1);
				msg += model.devicesToJson();
				msg += ",\"param\":"+"\"" + model.addAdmin(username) + "\"" + "}";
				model.save();
				return msg;
			}
			else{}//log
		else{}//log
		String msg = ReadFile.getFileContent(new File("messages/Login.txt"));
		msg = msg.substring(0, msg.length() - 1);
		msg += ",\"param\":"+"\"" + "1\"" + "}";
		return msg;
	}

	@POST
	@Path("/logoff")
	@Produces(MediaType.APPLICATION_JSON)
	public String logoff(@QueryParam("token") String token)
			throws IOException {
		if (token!=null){
			if (model.isLoggedOn(token)){
				if (model.logoff(token))
					model.save();
				else{}//log
				
				String msg = ReadFile.getFileContent(new File("messages/Home.txt"));
				msg = msg.substring(0, msg.length() - 1);
				msg += ",\"param\":"+"\"" + "1\"" + "}";
				return msg;
			}else{}//log
		}else{}//log
		String msg = ReadFile.getFileContent(new File("messages/Home.txt"));
		msg = msg.substring(0, msg.length() - 1);
		msg += ",\"param\":"+"\"" + "1\"" + "}";
		return msg;
	}

	@GET
	@Path("/register")
	@Produces(MediaType.APPLICATION_JSON)
	public String register(@QueryParam("token") String token) throws IOException {
		String msg = ReadFile.getFileContent(new File("messages/Register.txt"));
		msg = msg.substring(0, msg.length() - 1);
		if (token!=null)
			msg += ",\"param\":"+"\"" + token+"\"" + "}";
		else
			msg += ",\"param\":"+"\"" + "1\"" + "}";
		return msg;
	}

	@POST
	@Path("/register")
	@Produces(MediaType.APPLICATION_JSON)
	public String register(@QueryParam("token") String token,
			@QueryParam("firstname") String firstname,
			@QueryParam("lastname") String lastname,
			@QueryParam("email") String email,
			@QueryParam("username") String username,
			@QueryParam("password") String password,
			@QueryParam("confirm_password") String confirm_password) throws IOException {

		User user = new User(firstname, lastname, email, username, password);

		if (model.addNewUser(user, confirm_password)) {
			model.save();
			String msg = ReadFile.getFileContent(new File("messages/Login.txt"));
			msg = msg.substring(0, msg.length() - 1);
			msg += ",\"param\":"+"\"" + "1\"" + "}";
			return msg;
		}else{}//log
		String msg = ReadFile.getFileContent(new File("messages/Register.txt"));
		msg = msg.substring(0, msg.length() - 1);
		msg += ",\"param\":"+"\"" + "1\"" + "}";
		return msg;
	}

	@GET
	@Path("/devices")
	@Produces(MediaType.APPLICATION_JSON)
	public String devices(@QueryParam("token") String token) throws IOException {
		String msg = ReadFile.getFileContent(new File("messages/Devices.txt"));
		msg = msg.substring(0, msg.length() - 1);
		msg += model.devicesToJson();
		if (token!=null)
			msg += ",\"param\":"+"\"" + token+"\"" + "}";
		else
			msg += ",\"param\":"+"\"" + "1\"" + "}";
		return msg;
	}

	@GET
	@Path("/devices/add")
	@Produces(MediaType.APPLICATION_JSON)
	public String addDevice(@QueryParam("token") String token) throws IOException {
		String msg = ReadFile.getFileContent(new File("messages/AddDevice.txt"));
		msg = msg.substring(0, msg.length() - 1);
		if (token!=null)
			msg += ",\"param\":"+"\"" + token+"\"" + "}";
		else
			msg += ",\"param\":"+"\"" + "1\"" + "}";
	
		return msg;
	}

	@POST
	@Path("/devices/add")
	@Produces(MediaType.APPLICATION_JSON)
	public String addDevice(@QueryParam("name") String name, @QueryParam("token") String token)
			throws IOException {

		Device d = new Device(name);

		if (model.addNewDevice(d)) {
			model.save();
			String msg = ReadFile.getFileContent(new File("messages/AddDevice.txt"));
			msg = msg.substring(0, msg.length() - 1);
			if (token!=null)
				msg += ",\"param\":"+"\"" + token+"\"" + "}";
			else
				msg += ",\"param\":"+"\"" + "1\"" + "}";
		
			return msg;
		}else{}//log
		String msg = ReadFile.getFileContent(new File("messages/AddDevice.txt"));
		msg = msg.substring(0, msg.length() - 1);
		if (token!=null)
			msg += ",\"param\":"+"\"" + token+"\"" + "}";
		else
			msg += ",\"param\":"+"\"" + "1\"" + "}";
	
		return msg;
	}

	@GET
	@Path("/devices/remove")
	@Produces(MediaType.APPLICATION_JSON)
	public String removeDevice(@QueryParam("token") String token) throws IOException {
		String msg = ReadFile.getFileContent(new File("messages/RemoveDevice.txt"));
		msg = msg.substring(0, msg.length() - 1);
		msg += model.devicesToJson();
		if (token!=null)
			msg += ",\"param\":"+"\"" + token+"\"" + "}";
		else
			msg += ",\"param\":"+"\"" + "1\"" + "}";
		return msg;
	}

	@POST
	@Path("/devices/remove")
	@Produces(MediaType.APPLICATION_JSON)
	public String removeDevice(@QueryParam("name") String name, @QueryParam("token") String token)
			throws IOException {
		if (model.removeDevice(name)) {
			model.save();
			String msg = ReadFile.getFileContent(new File("messages/RemoveDevice.txt"));
			msg = msg.substring(0, msg.length() - 1);
			msg += model.devicesToJson();
			if (token!=null)
				msg += ",\"param\":"+"\"" + token+"\"" + "}";
			else
				msg += ",\"param\":"+"\"" + "1\"" + "}";
			return msg;
		}else{}//log
		String msg = ReadFile.getFileContent(new File("messages/RemoveDevice.txt"));
		msg = msg.substring(0, msg.length() - 1);
		if (token!=null)
			msg += ",\"param\":"+"\"" + token+"\"" + "}";
		else
			msg += ",\"param\":"+"\"" + "1\"" + "}";
	
		return msg;
	}

	@POST
	@Path("/devices/graph")
	@Produces(MediaType.APPLICATION_JSON)
	public String drawGraph(@QueryParam("name") String name,
							 @QueryParam("criteria") String criteria,
							 @QueryParam("token") String token) throws IOException {
		
		Device d = model.getDeviceByName(name);
			
		String msg = ReadFile.getFileContent(new File("messages/Graph.txt"));
		msg = msg.substring(0, msg.length() - 1);
		if (d!=null)
			msg += ",\"_devices\": [ "+d.toJSON(d.sortRecordsByCriteria(criteria))+"]";
		else{}//log
		if (token!=null){
			msg += ",\"param\":"+"\"" + token+"\"}" ;
		}else
			msg += ",\"param\":"+"\"" + "1\"" + "}";
		return msg;
	}
	
	@GET
	@Path("background/{imageId}")
	@Produces("image/*")
	public Response getImage(@PathParam(value = "imageId") int imageId) {

		File sourceimage = null;
		if (imageId == 1) {
			sourceimage = new File("res/background.jpg");
		} else if (imageId == 2) {
			sourceimage = new File("res/home.jpg");
		}else{}//log

		Image image = null;
		try {
			image = ImageIO.read(sourceimage);
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		if (image != null) {

			final ByteArrayOutputStream out = new ByteArrayOutputStream();
			try {
				ImageIO.write((BufferedImage) image, "jpg", out);

				final byte[] imgData = out.toByteArray();

				final InputStream bigInputStream = new ByteArrayInputStream(
						imgData);

				return Response.ok(bigInputStream).build();
			} catch (final IOException e) {
				return Response.noContent().build();
			}
		}else{}//log

		return Response.noContent().build();

	}
}