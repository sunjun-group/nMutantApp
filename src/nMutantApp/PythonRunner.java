package nMutantApp;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import sav.common.core.SavException;
import sav.common.core.utils.CollectionBuilder;
import sav.strategies.vm.VMRunner;

public class PythonRunner extends VMRunner {
	private Listener listener;
	
	public PythonRunner(PythonRunner.Listener listner) {
		setPrintOutExecutionTrace(true);
		this.listener = listner;
	}

	public void start(PythonVmConfiguration vmConfig) throws SavException {
		List<String> commands = buildCommandsFromConfiguration(vmConfig);
		super.startVm(commands, true);
	}

	private List<String> buildCommandsFromConfiguration(PythonVmConfiguration config) {
		CollectionBuilder<String, Collection<String>> builder = CollectionBuilder.init(new ArrayList<String>());
		builder.append(config.getPythonHome()).append(config.getLaunchClass());
		for (String arg : config.getProgramArgs()) {
			builder.append(arg);
		}
		return builder.getResult();
	}
	
	@Override
	protected void printOut(String line, boolean error) {
		super.printOut(line, error);
		listener.printOut(line);
	}
	
	public static interface Listener {

		public void printOut(String line);
		
	}
	
	public static class PythonVmConfiguration {
		private String pythonHome;
		private String launchClass;
		private List<String> programArgs = new ArrayList<>();

		public String getPythonHome() {
			return pythonHome;
		}

		public void setPythonHome(String pythonHome) {
			this.pythonHome = pythonHome;
		}

		public String getLaunchClass() {
			return launchClass;
		}

		public void setLaunchClass(String launchClass) {
			this.launchClass = launchClass;
		}

		public List<String> getProgramArgs() {
			return programArgs;
		}

		public void setProgramArgs(List<String> programArgs) {
			this.programArgs = programArgs;
		}
		
		public void addProgramArg(String newArg) {
			programArgs.add(newArg);
		}
	}
}