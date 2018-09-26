package nMutantApp;

public enum AttackFunction {
	BLACK_BOX ("blackbox"),
	CW("cw"),
	FGSM("fgsm"),
	JSMA("jsma");
	
	String text;
	private AttackFunction(String text) {
		this.text = text;
	}
	
	public String getText() {
		return text;
	}

}
