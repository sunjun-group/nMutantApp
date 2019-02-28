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
	MetricsOutputHandler outputHandler;
	
	public MetricsCalculator(DataSet dataset, AttackFunction attack, String model,
			MetricsOutputHandler outputHandler) {
		this.dataset = dataset;
		this.attack = attack;
		this.model = model;
		this.outputHandler = outputHandler;
	}
	
	@Override
	public void run() {
		try {
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
	
	public static interface MetricsOutputHandler {

		void printOutConsole(String line);

		void printOutResult(String output);

		void enterCalculateMetrics();
		
	}

}
