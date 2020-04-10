package me.mrmaurice.sg;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import lombok.Cleanup;
import lombok.Getter;
import lombok.Setter;

import static me.mrmaurice.sg.StickerGenerator.getExt;

@Getter
@Setter
public class StickerPack {

	private final String identifier;
	private String name;
	private String publisher;
	private String tray_image_file;
	private String image_data_version = "1";
	private boolean avoid_cache = true;
	private String publisher_email;
	private String publisher_website;
	private String privacy_policy_website;
	private String license_agreement_website;

	private List<Sticker> stickers = new ArrayList<>();

	public StickerPack(File folder) {
		identifier = folder.getName();
		for (File file : folder.listFiles()) {
			String ext = getExt(file);
			if (ext.equals("json"))
				setSettings(file);
			if (ext.equals("webp"))
				stickers.add(new Sticker(file));
			if (ext.equals("png"))
				tray_image_file = file.getName();
		}
	}

	private void setSettings(File file) {
		Gson gson = new Gson();
		try {
			@Cleanup
			JsonReader reader = gson.newJsonReader(new FileReader(file));
			PackSetting sett = gson.fromJson(reader, PackSetting.class);
			sett.apply(this);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
