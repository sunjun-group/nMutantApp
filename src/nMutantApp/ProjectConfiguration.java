package nMutantApp;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.List;

public class ProjectConfiguration {
	private static String installFolder;
	
	static {
		String path = new File(ProjectConfiguration.class.getProtectionDomain().getCodeSource().getLocation().getPath())
				.getAbsolutePath();
		System.out.println("path: " + path);
		path = path.replace("\\", "/");
		if (path.endsWith("/.")) {
			path = path.substring(0, path.lastIndexOf("/."));
		} else if (path.endsWith("nMutantApp/bin")) {
			path = path.substring(0, path.indexOf("/nMutantApp/bin"));
		} else if (path.endsWith("/nMutant.jar")) {
			path = path.substring(0, path.indexOf("/nMutant.jar"));
		}
		installFolder = path;
	}
	
	public static void main(String[] args) {
		System.out.println(getInstallFolder());
	}
	
	public static String getInstallFolder() {
		return installFolder;
	}
	
	public static List<String> listJars(String relativePath) {
		String absFolder = getAbsolutePath(relativePath);
		List<String> jars = new ArrayList<>();
		new File(absFolder).listFiles(new FileFilter() {
			
			@Override
			public boolean accept(File file) {
				if (file.isFile()) {
					file.getName().endsWith(".jar");
					jars.add(file.getName());
				}
				return false;
			}
		});
		return jars;
	}

	public static String getAbsolutePath(String relativePath) {
		return installFolder + relativePath;
	}
}
