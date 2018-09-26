package nMutantApp;

import nMutantApp.PythonRunner.PythonVmConfiguration;
import sav.common.core.SavException;
import sav.strategies.vm.ProgramArgumentBuilder;
import sav.strategies.vm.VMRunner;

public class NMutant extends VMRunner {

	public NMutantOutput run(NMutantParams params) throws SavException {
		NMutantOutput output = new NMutantOutput();
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
