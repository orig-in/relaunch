package com.github.origin.relaunch;

import static java.lang.String.format;
import static java.lang.Thread.sleep;
import static java.nio.file.Files.move;
import static java.nio.file.Paths.get;
import static java.util.Arrays.asList;

import java.io.File;
import java.io.IOException;
import java.util.Date;

public class Relaunch {

	public static void main(String[] args) throws InterruptedException, IOException {
		String oldApp = args[0];
		String newApp = args[1];
		String logLocation = args[2];
		String[] launchScript =  new String[args.length-3];
		System.arraycopy(args, 3, launchScript, 0, launchScript.length);
		System.out.println(format("Relaunching %s %s %s %s", oldApp, newApp, logLocation, asList(launchScript)));
		checkFile(oldApp);
		checkFile(newApp);
		while(!new File(oldApp).delete()) {
			System.out.println(new Date() + " waiting to delete "+oldApp);
			sleep(1000);
		}
		
		move(get(newApp), get(oldApp));
		new ProcessBuilder(launchScript)
		  .redirectErrorStream(true)
		  .redirectOutput(new File(logLocation))
		  .start();
	}

	private static void checkFile(String oldApp) {
		if(!new File(oldApp).exists()) {
			System.out.println(oldApp + " does not exist");
			System.exit(1);
		}
	}

}
