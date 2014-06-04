package dwarfcraft;

import com.googlecode.lanterna.terminal.Terminal;
import com.googlecode.lanterna.terminal.swing.SwingTerminal;


public class ConsoleGraphics extends Graphics {
	
	public ConsoleGraphics() {
		Terminal terminal = new SwingTerminal(80, 20);
		terminal.enterPrivateMode();
		terminal.putCharacter('H');
		terminal.putCharacter('e');
		terminal.putCharacter('l');
		terminal.putCharacter('l');
		terminal.putCharacter('o');
	}
}
