package me.mrmaurice.sg;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

import static me.mrmaurice.sg.StickerGenerator.getExt;

@Getter
public class StickerCollection {

	private String android_play_store_link = "";
	private String ios_app_store_link = "";
	private List<StickerPack> sticker_packs;

	public StickerCollection() {
		sticker_packs = new ArrayList<>();
		File file = new File(".");
		for (File folder : file.listFiles()) {
			if (getExt(folder) == null)
				sticker_packs.add(new StickerPack(folder));
		}
	}

}
