package utils;

import utils.audio.AudioService;
import utils.graphics.GraphicsService;
import utils.input.InputService;

public class Locator {
	
	private static GraphicsService graphics;
	private static InputService input;
	private static AudioService audio;

	public static GraphicsService getGraphics() {
		return graphics;
	}
	
	public static void setGraphics(GraphicsService g){
		Locator.graphics = g;
	}

	public static InputService getInput() {
		return input;
	}

	public static void setInput(InputService input) {
		Locator.input = input;
	}

	public static AudioService getAudio() {
		return audio;
	}

	public static void setAudio(AudioService audio) {
		Locator.audio = audio;
	}

}
