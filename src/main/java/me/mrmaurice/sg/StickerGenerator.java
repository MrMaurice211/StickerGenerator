package me.mrmaurice.sg;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.file.Files;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonWriter;
import com.sun.net.httpserver.*;

@SuppressWarnings("restriction")
public class StickerGenerator {

	private final File content;

	public static void main(String[] args) throws IOException {
		StickerGenerator gen = new StickerGenerator();
		HttpServer server = HttpServer.create(new InetSocketAddress(8000), 5000);

		server.createContext("/stickers", (exchange) -> {
			String path = exchange.getRequestURI().getPath();
			path = path.substring(1);
			String[] options = path.split("/");

			if(options.length != 3) {
				exchange.sendResponseHeaders(405, -1);
				return;
			}
			
			File sticker = new File(".");
			sticker = new File(sticker, options[1]);
			sticker = new File(sticker, options[2]);
			
			if(!sticker.exists()) {
				exchange.sendResponseHeaders(404, -1);
				return;
			}

			exchange.sendResponseHeaders(200, sticker.length());
			OutputStream os = exchange.getResponseBody();
			Files.copy(sticker.toPath(), os);
			exchange.close();
		});

		server.createContext("/contents.json", (exchange) -> {
			gen.generate();
			exchange.sendResponseHeaders(200, gen.content.length());
			OutputStream os = exchange.getResponseBody();
			Files.copy(gen.content.toPath(), os);
			exchange.close();
		});
		server.setExecutor(null);
		server.start();
	}

	public StickerGenerator() {
		File root = new File(".");
		content = new File(root, "contents.json");
	}
	
	public void generate() {
		Gson gson = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
		try {
			StickerCollection col = new StickerCollection();
			JsonWriter jw = gson.newJsonWriter(new FileWriter(content));
			gson.toJson(col, StickerCollection.class, jw);
			jw.flush();
			jw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static String getExt(File file) {
		if(file.isDirectory())
			return null;
		String name = file.getName();
		return name.substring(name.lastIndexOf('.') + 1);
	}

}
