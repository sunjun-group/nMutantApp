package nMutantApp.metrics;

import java.io.File;

import org.apache.commons.io.FileUtils;

import nMutantApp.AttackFunction;
import nMutantApp.DataSet;
import nMutantApp.ProjectConfiguration;
import nMutantApp.PythonRunner;
import nMutantApp.PythonRunner.Listener;
import nMutantApp.PythonRunner.PythonVmConfiguration;
import sav.strategies.vm.ProgramArgumentBuilder;

public class MetricsCalculator implements Runnable {
	DataSet dataset;
	AttackFunction attack;
	String model;
//	String trainedDataFolder;
//	String testDataFolder;
	String dataSetFolder;
	MetricsOutputHandler outputHandler;
	
	public MetricsCalculator(MetricsOutputHandler outputHandler, DataSet dataset, AttackFunction attack, String model,
			/* String trainedDataFolder, String testDataFolder*/
			String dataSetFolder) {
		this.dataset = dataset;
		this.attack = attack;
		this.model = model;
		this.outputHandler = outputHandler;
		this.dataSetFolder = dataSetFolder;
//		this.trainedDataFolder = trainedDataFolder;
//		this.testDataFolder = testDataFolder;
	}
	

	@Override
	public void run() {
		try {
			outputHandler.printOutConsole("Calculate metrics..\n");
			String modelFolder = sav.common.core.utils.FileUtils.getFilePath(
					ProjectConfiguration.getPythonAgentWorkingDir(),
					"../adv_result", dataset.name(), attack.getText(), model);
			File target = new File(modelFolder);
			File source = new File(dataSetFolder);
			if (!(target.exists() && source.exists() && target.getCanonicalPath().equals(source.getCanonicalPath()))) {
				clearFolder(modelFolder);
//			String dataFolder = sav.common.core.utils.FileUtils.getFilePath(modelFolder, "train_data");
//			nMutantApp.metrics.FileUtils.createFolder(dataFolder);
//			FileUtils.copyDirectory(new File(trainedDataFolder), new File(dataFolder));
//			
//			dataFolder = sav.common.core.utils.FileUtils.getFilePath(modelFolder, "test_data");
//			nMutantApp.metrics.FileUtils.createFolder(dataFolder);
//			FileUtils.copyDirectory(new File(testDataFolder), new File(dataFolder));
				
				String dataFolder = sav.common.core.utils.FileUtils.getFilePath(modelFolder);
				nMutantApp.metrics.FileUtils.createFolder(dataFolder);
				FileUtils.copyDirectory(new File(dataSetFolder), new File(dataFolder));
			} 
			
			outputHandler.enterCalculateMetrics();
			PythonRunner pythonRunner = new PythonRunner(new Listener() {
				
				@Override
				public void printOut(String line) {
					outputHandler.printOutConsole(line);
					outputHandler.printOutConsole("\n");
				}
			});
			pythonRunner.setWorkingDir(ProjectConfiguration.getPythonAgentWorkingDir());
			PythonVmConfiguration config = new PythonVmConfiguration();
			config.setPythonHome(ProjectConfiguration.pythonHome);
			config.setLaunchClass("cal.py");
			ProgramArgumentBuilder argBuilder = new ProgramArgumentBuilder();
			argBuilder.addArgument("datasets", dataset.name());
			argBuilder.addArgument("attacks", attack.getText());
			argBuilder.addArgument("models", model);
			config.setProgramArgs(argBuilder.build());
			pythonRunner.start(config);
			File file = new File(ProjectConfiguration.getPythonAgentWorkingDir() + "/result.txt");
			if (file.exists()) {
				String output = FileUtils.readFileToString(file);
				outputHandler.printOutResult(output);
			}
		} catch (Exception e) {
			outputHandler.printOutConsole(e.getMessage());
		}
	}
	
	private void clearFolder(String folder) {
		File file = new File(folder);
		if (file.exists()) {
			nMutantApp.metrics.FileUtils.deleteFolder(file);
		}
		nMutantApp.metrics.FileUtils.createFolder(folder);
	}

	public static interface MetricsOutputHandler {

		void printOutConsole(String line);

		void printOutResult(String output);

		void enterCalculateMetrics();
		
	}

}
