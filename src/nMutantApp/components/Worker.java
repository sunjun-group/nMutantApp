package nMutantApp.components;

import javax.swing.SwingWorker;

public class Worker extends SwingWorker<String, Runnable> {
	public static final String PROPERTY_NAME = "done";
	private Runnable task;
	
	public Worker(Runnable task) {
		this.task = task;
	}

	@Override
	protected String doInBackground() throws Exception {
		task.run();
		firePropertyChange(PROPERTY_NAME, false, true);
		return null;
	}
}