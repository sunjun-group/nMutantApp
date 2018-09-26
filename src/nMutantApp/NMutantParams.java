package nMutantApp;

public class NMutantParams {
	private AttackFunction attackFunction;
	private DataSet dataset;
	private String samplePath;
	private int target;
	private String modelPath;
	private String storePath;
	private int nbClasses;

	public static NMutantParams getDefault() {
		NMutantParams params = new NMutantParams();
		params.setAttackFunction(AttackFunction.BLACK_BOX);
		params.setDataset(DataSet.mnist);
		String workingFolder = ProjectConfiguration.getAbsolutePath("/python/nMutant");
		params.setSamplePath(workingFolder + "/datasets/integration/mnist/2.png");
		params.setTarget(1);
		params.setModelPath(workingFolder + "/models/integration/mnist");
		params.setStorePath(ProjectConfiguration.getAbsolutePath("/mt_result/integration/blackbox/mnist"));
		params.setNbClasses(10);
		return params;
	}

	public AttackFunction getAttackFunction() {
		return attackFunction;
	}

	public void setAttackFunction(AttackFunction attackFunction) {
		this.attackFunction = attackFunction;
	}

	public DataSet getDataset() {
		return dataset;
	}

	public void setDataset(DataSet dataset) {
		this.dataset = dataset;
	}

	public String getSamplePath() {
		return samplePath;
	}

	public void setSamplePath(String samplePath) {
		this.samplePath = samplePath;
	}

	public int getTarget() {
		return target;
	}

	public void setTarget(int target) {
		this.target = target;
	}

	public String getModelPath() {
		return modelPath;
	}

	public void setModelPath(String modelPath) {
		this.modelPath = modelPath;
	}

	public String getStorePath() {
		return storePath;
	}

	public void setStorePath(String storePath) {
		this.storePath = storePath;
	}

	public int getNbClasses() {
		return nbClasses;
	}

	public void setNbClasses(int nbClasses) {
		this.nbClasses = nbClasses;
	}

}
