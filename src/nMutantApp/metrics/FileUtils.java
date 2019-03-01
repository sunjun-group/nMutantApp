package nMutantApp.metrics;

import java.io.File;

/**
 * @author LLT
 *
 */
public class FileUtils {
	
	public static void deleteFolder(File file) {
		if (!file.exists()) {
			return;
		}
		if (!file.isDirectory()) {
			if (file.getAbsolutePath().length() > 260) {
				File newFile = new File(sav.common.core.utils.FileUtils.getFilePath(file.getParent(), "$"));
				file.renameTo(newFile);
				newFile.delete();
			} else {
				file.delete();
			}
		} else {
			for (File sub : file.listFiles()) {
				deleteFolder(sub);
			}
		}
		file.delete();
	}
	
	public static File createFolder(String folderPath) {
		File folder = new File(folderPath);
		if (folder.exists()) {
			if (folder.isDirectory()) {
				return folder;
			}
			throw new IllegalArgumentException(String.format("Path %s is not a folder!", folderPath));
		}
		folder.mkdirs();
		return folder;
	}
}
