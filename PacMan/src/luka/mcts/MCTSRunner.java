package luka.mcts;

public class MCTSRunner implements Runnable{

    private boolean doStop = false;
    private int i = 0;
    
    public synchronized void doStop() {
        this.doStop = true;
    }

    public synchronized void doStart() {
        this.doStop = false;
    }

    
    private synchronized boolean keepRunning() {
        return this.doStop == false;
    }
	
    public synchronized int GetBestMove() 
    {
    	System.out.println("-------------------------------------------Gettin' best move-----------------------------------------");
    	return i;
    }
    
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(keepRunning()) {
            // keep doing what this thread should do.
            //
			i++;
			//System.out.println("Running");
        }
	}
	
}
