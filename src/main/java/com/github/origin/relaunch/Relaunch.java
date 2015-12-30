package com.github.origin.relaunch;

import static java.lang.String.format;
import static java.lang.Thread.sleep;
import static java.nio.file.Files.move;
import static java.nio.file.Paths.get;

import java.io.File;
import java.io.IOException;
import java.util.Date;

public class Relaunch {

	public static void main(String[] args) throws InterruptedException, IOException {
		String oldApp = args[0];
		String newApp = args[1];
		String launchScript = args[2];
		System.out.println(format("Relaunching %s %s %s", oldApp, newApp, launchScript));
		
		while(!new File(oldApp).delete()) {
			System.out.println(new Date() + " waiting to delete "+oldApp);
			sleep(1000);
		}
		
		move(get(newApp), get(oldApp));
		new ProcessBuilder("cmd.exe", "/C", launchScript).start();
	}

}
