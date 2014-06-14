package services;

import services.audio.AudioService;
import services.graphics.GraphicsService;
import services.input.InputService;

public class ServiceLocator {
	
	private static GraphicsService graphics;
	private static InputService input;
	private static AudioService audio;

	public static GraphicsService getGraphics() {
		return graphics;
	}
	
	public static void setGraphics(GraphicsService g){
		ServiceLocator.graphics = g;
	}

	public static InputService getInput() {
		return input;
	}

	public static void setInput(InputService input) {
		ServiceLocator.input = input;
	}

	public static AudioService getAudio() {
		return audio;
	}

	public static void setAudio(AudioService audio) {
		ServiceLocator.audio = audio;
	}

}
