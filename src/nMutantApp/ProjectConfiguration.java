package nMutantApp;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;

import nMutantApp.metrics.SettingDialog;

public class ProjectConfiguration {
	private static String installFolder;
	private static String pythonAgentWorkingDir;
	public static String pythonHome;
	public static String settingFile;
	
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
//		pythonAgentWorkingDir = "/Users/lylytran/Projects/Python/nMutant/attack_metrics";
		pythonAgentWorkingDir = ProjectConfiguration.getAbsolutePath("/python/nMutant/attack_metrics");
		loadSettings();
		if (pythonHome == null) {
			pythonHome = "python";
		}
	}
	
	private static void loadSettings() {
		settingFile = getAbsolutePath("/nmutant.conf");
		if (new File(settingFile).exists()) {
			try {
				List<?> lines = FileUtils.readLines(new File(settingFile));
				for (Object obj : lines) {
					String line = (String) obj;
					if (line.startsWith(SettingDialog.PY_HOME_KEY)) {
						pythonHome = line.substring(SettingDialog.PY_HOME_KEY.length());
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
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
	
	public static String getPythonAgentWorkingDir() {
		return pythonAgentWorkingDir;
	}
}
