package es.uma.rysd.app;

import javax.net.ssl.HttpsURLConnection;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;

import com.google.gson.Gson;

import es.uma.rysd.entities.*;

public class SWClient {
	// Complete el nombre de la aplicacion
	private final String app_name = "Star Wars";
	private final int year = 2022;

	private final String url_api = "https://swapi.dev/api/";

	// Obtiene la URL del recurso id del tipo resource
	public String generateEndpoint(String resource, Integer id) {
		return url_api + resource + "/" + id + "/";
	}

	// Dada una URL de un recurso obtiene su ID
	public Integer getIDFromURL(String url) {
		String[] parts = url.split("/");

		return Integer.parseInt(parts[parts.length - 1]);
	}

	// Consulta un recurso y devuelve cuantos elementos tiene
	public int getNumberOfResources(String resource) {
		// Trate de forma adecuada las posibles excepciones que pueden producirse
		URL servicio = null;
		try {
			servicio = new URL(url_api + resource + "/");
		} catch (MalformedURLException e) {
			e.printStackTrace();
			System.err.println("Invalid URL. No se pudo crear la URL correctamente");
		}

		// Una vez tenemos la URL, debemos crear una conexion asociada a ella y
		// configurarla.
		HttpsURLConnection connection = null;
		try {
			connection = (HttpsURLConnection) servicio.openConnection();
		} catch (Exception e) {
			System.err.println("Error creando la conexion a partir de URL");
		}

		// Aniada las cabeceras User-Agent y Accept a la peticion (vea el enunciado)
		// setRequestProperty --> aniade a la cabecera 'key:value'
		connection.setRequestProperty("Accept", "application/json");
		connection.setRequestProperty("User-Agent", app_name + "-" + year);

		// Indique que el metodo a utilizar es una peticion GET
		try {
			// setRequestMethod --> establece el metodo de la peticion
			connection.setRequestMethod("GET");
		} catch (ProtocolException e) {
			e.printStackTrace(); // para ver el error donde se encuentra
		}

		// Compruebe que el codigo recibido en la respuesta es correcto
		int res = 0;
		try {
			// getResponseCode --> codigo/mensaje de la respuesta
			res = connection.getResponseCode(); // metodo devuelve un entero, comnprendido entre 200 y 299 (true).
		} catch (IOException e) {
			e.printStackTrace();
		}

		if (res < 200 || res > 299) {
			return 0;
		}

		// Deserialice la respuesta a ResourceCountResponse
		// GSON --> biblioteca java que convierte 'json' en 'objetos java'
		// JSON --> formato de intercambio de datos, original de JavaScript
		Gson parser = new Gson();
		InputStream in = null; // Obtenga el InputStream de la conexion
		try {
			// getImputStream --> obtiene el flujo para leer el cuerpo de la respuesta
			in = connection.getInputStream();
		} catch (IOException e) {
			e.printStackTrace();
		}
		ResourceCountResult c = parser.fromJson(new InputStreamReader(in), ResourceCountResult.class);

		// Devuelve el numero de elementos
		return c.count;
	}

	public Person getPerson(String urlname) {
		Person p = null;
		// Por si acaso viene como http la pasamos a https
		urlname = urlname.replaceAll("http:", "https:");

		// Trate de forma adecuada las posibles excepciones que pueden producirse
		URL servicio = null;
		try {
			servicio = new URL(urlname);
		} catch (MalformedURLException e) {
			e.printStackTrace();
			System.err.println("Invalid URL. No se pudo crear la URL correctamente");
		}

		// Una vez tenemos la URL, debemos crear una conexion asociada a ella y
		// configurarla.
		HttpsURLConnection connection = null;
		try {
			connection = (HttpsURLConnection) servicio.openConnection();
		} catch (Exception e) {
			System.err.println("Error creando la conexion a partir de URL");
		}

		// Aniada las cabeceras User-Agent y Accept (vea el enunciado)
		connection.setRequestProperty("Accept", "application/json");
		connection.setRequestProperty("User-Agent", app_name + "-" + year);

		// Indique que es una peticion GET
		try {
			connection.setRequestMethod("GET");
		} catch (ProtocolException e) {
			e.printStackTrace(); // para ver el error donde se encuentra
		}

		// Compruebe que el codigo recibido en la respuesta es correcto
		int res = 0;
		try {
			res = connection.getResponseCode(); // metodo devuelve un entero, comnprendido entre 200 y 299 (true).
		} catch (IOException e) {
			e.printStackTrace();
		}

		if (res < 200 || res > 299) {
			return null;
		}

		// Deserialice la respuesta a ResourceCountResponse
		Gson parser = new Gson();
		InputStream in = null; // Obtenga el InputStream de la conexion
		try {
			in = connection.getInputStream();
		} catch (IOException e) {
			e.printStackTrace();
		}
		p = parser.fromJson(new InputStreamReader(in), Person.class);

		// Para las preguntas 2 y 3 (no necesita completar esto para la pregunta
		// 1)
		// A partir de la URL en el campo homeworld obtenga los datos del planeta
		// y almacenelo en atributo homeplanet
		// OBJETIVO: obtener los datos del planeta.
		World homeplanet = new World();
		homeplanet = getWorld(p.homeworld);
		p.homeplanet = homeplanet;

		// Devuelve el numero de elementos
		return p;
	}

	public World getWorld(String urlname) {
		World pl = null;
		// Por si acaso viene como http la pasamos a https
		urlname = urlname.replaceAll("http:", "https:");

		// Trate de forma adecuada las posibles excepciones que pueden producirse
		URL servicio = null;
		try {
			servicio = new URL(urlname);
		} catch (MalformedURLException e) {
			e.printStackTrace();
			System.err.println("Invalid URL. No se pudo crear la URL correctamente");
		}

		// Una vez tenemos la URL, debemos crear una conexion asociada a ella y
		// configurarla.
		HttpsURLConnection connection = null;
		try {
			connection = (HttpsURLConnection) servicio.openConnection();
		} catch (Exception e) {
			System.err.println("Error creando la conexion a partir de URL");
		}

		// Aniada las cabeceras User-Agent y Accept (vea el enunciado)
		connection.setRequestProperty("Accept", "application/json");
		connection.setRequestProperty("User-Agent", app_name + "-" + year);

		// Indique que es una peticion GET
		try {
			connection.setRequestMethod("GET");
		} catch (ProtocolException e) {
			e.printStackTrace(); // para ver el error donde se encuentra
		}

		// Compruebe que el codigo recibido en la respuesta es correcto
		int res = 0;
		try {
			res = connection.getResponseCode(); // metodo devuelve un entero, comnprendido entre 200 y 299 (true).
		} catch (IOException e) {
			e.printStackTrace();
		}

		if (res < 200 || res > 299) {
			return null;
		}

		// Deserialice la respuesta a ResourceCountResponse
		Gson parser = new Gson();
		InputStream in = null; // Obtenga el InputStream de la conexion
		try {
			in = connection.getInputStream();
		} catch (IOException e) {
			e.printStackTrace();
		}
		pl = parser.fromJson(new InputStreamReader(in), World.class);

		// Devuelve el numero de elementos
		return pl;
	}

	public Person search(String name) {
		Person p = null;
		// Cree la conexi�n a partir de la URL (url_api + name tratado - vea el
		// enunciado)
		try {
			name = URLEncoder.encode(name, "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		// Trate de forma adecuada las posibles excepciones que pueden producirse
		URL servicio = null;
		try {
			servicio = new URL(url_api + "/people/?search=" + name + "/");
		} catch (MalformedURLException e) {
			e.printStackTrace();
			System.err.println("Invalid URL. No se pudo crear la URL correctamente");
		}

		// Una vez tenemos la URL, debemos crear una conexion asociada a ella y
		// configurarla.
		HttpsURLConnection connection = null;
		try {
			connection = (HttpsURLConnection) servicio.openConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}

		// Aniada las cabeceras User-Agent y Accept (vea el enunciado)
		connection.setRequestProperty("Accept", "application/json");
		connection.setRequestProperty("User-Agent", app_name + "-" + year);

		// Indique que es una peticion GET
		try {
			connection.setRequestMethod("GET");
		} catch (ProtocolException e) {
			e.printStackTrace(); // para ver el error donde se encuentra
		}

		// Compruebe que el codigo recibido en la respuesta es correcto
		int res = 0;
		try {
			res = connection.getResponseCode(); // metodo devuelve un entero, comnprendido entre 200 y 299 (true).
		} catch (IOException e) {
			e.printStackTrace();
		}

		if (res < 200 || res > 299) {
			return null;
		}

		/*
		 * JSON is a format that encodes objects in a string.
		 * Serialization means to convert an object into that string,
		 * and deserialization is its inverse operation (convert string -> object).
		 */
		// Deserialice la respuesta a ResourceCountResponse
		Gson parser = new Gson();
		InputStream in = null; // Obtenga el InputStream de la conexion
		try {
			in = connection.getInputStream();
		} catch (IOException e) {
			e.printStackTrace();
		}
		QueryResponse sr = parser.fromJson(new InputStreamReader(in), QueryResponse.class);

		// Para las preguntas 2 y 3 (no necesita completar esto para la pregunta
		// 1)
		// A partir de la URL en el campo homreworld obtenga los datos del planeta
		// y almacenelo en atributo homeplanet
		if (sr.count > 0) {
			p = sr.results[0];
			World homeplanet = new World();
			homeplanet = getWorld(p.homeworld);
			p.homeplanet = homeplanet;
		}
		// Devuelve el numero de elementos
		return p;
	}

}