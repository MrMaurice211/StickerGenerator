package me.mrmaurice.sg;

import java.io.File;

import lombok.Getter;

@Getter
public class Sticker {

	private String image_file;
	
	public Sticker(File file) {
		image_file = file.getName();
	}
	
}
