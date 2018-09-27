package nMutantApp;

import java.io.File;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.nio.file.Paths;

import nMutantApp.PythonRunner.PythonVmConfiguration;
import sav.common.core.SavException;
import sav.common.core.utils.FileUtils;
import sav.strategies.vm.ProgramArgumentBuilder;
import sav.strategies.vm.VMRunner;

public class NMutant extends VMRunner {

	public NMutantOutput run(NMutantParams params) throws SavException {
		final NMutantOutput output = new NMutantOutput();
		final String logFile = ProjectConfiguration.getAbsolutePath("/nmutant.log");
		System.out.println("Log file: " + logFile);
		clearIfTooLarge(logFile);
		PythonRunner pythonRunner = new PythonRunner(new PythonRunner.Listener() {
			
			@Override
			public void printOut(String line) {
				String token = "$$$ori_img{";
				if (line.startsWith(token)) {
					output.setOrgImgPath(line.substring(token.length(), line.indexOf("}")).trim());
				} else {
					token = "$$$adv_img{";
					if (line.startsWith(token)) {
						output.setAveImgPath(line.substring(token.length(), line.indexOf("}")).trim());
					}
				}
				FileUtils.appendFile(logFile, line + "\n");
			}
		});
		pythonRunner.setWorkingDir(ProjectConfiguration.getAbsolutePath("/python/nMutant/nmutant_integration"));
		PythonVmConfiguration config = new PythonVmConfiguration();
		config.setPythonHome("python");
		config.setLaunchClass(params.getAttackFunction().getText() + ".py");
		ProgramArgumentBuilder argBuilder = new ProgramArgumentBuilder();
		argBuilder.addArgument("datasets", params.getDataset().name());
		argBuilder.addArgument("sample", params.getSamplePath());
		argBuilder.addArgument("target", String.valueOf(params.getTarget()));
		argBuilder.addArgument("model_path", params.getModelPath());
		argBuilder.addArgument("store_path", params.getStorePath());
		argBuilder.addArgument("nb_classes", String.valueOf(params.getNbClasses()));
		config.setProgramArgs(argBuilder.build());
		pythonRunner.start(config);
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return output;
	}

	private void clearIfTooLarge(final String logFile) {
		File file = new File(logFile);
		if (file.exists()) {
			boolean tooLarge = false;
			Path filePath = Paths.get(logFile);
			FileChannel fileChannel;
			try {
				fileChannel = FileChannel.open(filePath);
				long fileSize = fileChannel.size();
				System.out.println(fileSize + " bytes");
				if (fileSize > (10 * 1024 * 1024)) {
					tooLarge = true;
				}
				fileChannel.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			if (tooLarge) {
				file.delete();
				try {
					file.createNewFile();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static class NMutantOutput {
		private String orgImgPath;
		private String aveImgPath;

		public String getOrgImgPath() {
			return orgImgPath;
		}

		public void setOrgImgPath(String orgImgPath) {
			this.orgImgPath = orgImgPath;
		}

		public String getAveImgPath() {
			return aveImgPath;
		}

		public void setAveImgPath(String aveImgPath) {
			this.aveImgPath = aveImgPath;
		}

	}
}
