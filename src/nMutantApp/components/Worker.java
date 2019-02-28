package nMutantApp.components;

import javax.swing.SwingWorker;

public class Worker extends SwingWorker<String, Runnable> {
	static final String DONE = "done";
	private Runnable task;
	
	public Worker(Runnable task) {
		this.task = task;
	}

	@Override
	protected String doInBackground() throws Exception {
		task.run();
		firePropertyChange(DONE, false, true);
		return null;
	}
	
	public static enum State {
		RUNNING,
		DONE
	}
}