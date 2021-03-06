package maig;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Monte Carlo Tree Search algorithm implementing UCT method
 * Run main method to test
 * 
 * @author D.Vitonis
 * @modified A. Hartzen
 *
 */
public class UCT {

	/*
	 * Maze used to control the game
	 */
	public Map maze;
	private Random random = new Random();
	
	/*
	 * rootNode is the starting point of the present state
	 */
	Node rootNode;
	
	/*
	 * currentNode refers to the node we work at every step
	 */
	Node currentNode;
	
	/*
	 * Exploration coefficient
	 */
	private float C = (float) (1.0/Math.sqrt(2));
	
	/*
	 * Computational limit
	 */
	protected final int maxIterations = 100;
	
	
	/**
	 * Constructor
	 * Initialize the maze game
	 */
	UCT(){
		maze = new Map();
		maze.resetMaze();
	}
	
	/**
	 * run the UCT search and find the optimal action for the root node state
	 * @return
	 * @throws InterruptedException
	 */
	public int runUCT() throws InterruptedException{
		
            /*
             * Create root node with the present state
             */
            rootNode = new Node(maze.map.clone());
            
            /*
             * Apply UCT search inside computational budget limit (default=100 iterations) 
             */
            int iterations = 0;
            while(!Terminate(iterations)){
            	iterations ++;
            	
            	//Implement UCT algorithm here
            }
            
            /*
             * Get the action that directs to the best node
             */
            currentNode = rootNode;
            //rootNode is the one we are working with 
            //and we apply the exploitation of it to find the child with the highest average reward
            int bestAction = 0;
            
            return bestAction;
	}
	
	/**
	 * Expand the nonterminal nodes with one available child. 
	 * Chose a node to expand with BestChild(C) method
	 */
	private void TreePolicy() {
		currentNode = rootNode;
		
	}
	
	/**
	 * Simulation of the game. Choose random actions up until the game is over (goal reached or dead)
	 * @return reward (1 for win, 0 for loss)
	 */
	private float DefaultPolicy() {
		char[] st = currentNode.state.clone();
		while(!TerminalState(st)){
			int action = RandomAction(st);
			st = maze.getNextState(action, st);
			int ghostAction = RandomGhostAction(st);
			st = maze.getNextGhostState(ghostAction, st);
		}
		return maze.getReward(st);
	}

	/**
	 * Assign the received reward to every parent of the parent up to the rootNode
	 * Increase the visited count of every node included in backpropagation
	 * @param reward
	 */
	private void Backpropagate(float reward) {
	}
	
	/**
	 * Check if the node is fully expanded
	 * @param nt
	 * @return
	 */
	private boolean FullyExpanded(Node nt) {
		return false;
	}

	/**
	 * Check if the state is the end of the game
	 * @param state
	 * @return
	 */
	private boolean TerminalState(char[] state) {
		return maze.isGoalReached(state) || maze.isAvatarDead(state);
	}

	/**
	 * Choose the best child according to the UCT value
	 * Assign it as a currentNode
	 * @param c Exploration coefficient
	 */
	private void BestChild(float c) {
		Node nt = currentNode;
		Node bestChild = null;

		
		
		currentNode = bestChild;
	}

	/**
	 * Calculate UCT value for the best child choosing
	 * @param n child node of currentNode
	 * @param c Exploration coefficient
	 * @return
	 */
	private float UCTvalue(Node n, float c) {
		return 0;
	}

	/**
	 * Expand the current node by adding new child to the currentNode
	 */
	private void Expand() {
		/*
		 * Choose untried action
		 */
		int action = UntriedAction(currentNode);
		
		/*
		 * Create a child, set its fields and add it to currentNode.children
		 */
		Node child = new Node(maze.getNextGhostState(RandomGhostAction(maze.getNextState(action, currentNode.state)), 
				maze.getNextState(action, currentNode.state)));
		
	}

	/**
	 * Returns the first untried action of the node
	 * @param n
	 * @return
	 */
	private int UntriedAction(Node n) {
		outer:
		for (int i=0;i<4;i++){
			for (int k=0;k<n.children.size();k++){
				if (n.children.get(k).parentAction == i){
					continue outer;
				}
			}
			if (maze.isValidMove(i, n.state))
				return i;
		}
		return -1;
	}

	/**
	 * Check if the algorithm is to be terminated, e.g. reached number of iterations limit
	 * @param i
	 * @return
	 */
	private boolean Terminate(int i) {
		if (i>maxIterations) return true;
		return false;
	}

	/**
	 * Used in game simulation to pick random action for the agent
	 * @param state st
	 * @return action
	 */
	private int RandomAction(char[] st) {
		int action = random.nextInt(4);
        while (!maze.isValidMove(action,st)){
        	action = random.nextInt(4);
        }
        return action;
	}
	
	/**
	 * Used in game simulation to pick random action for the ghost
	 * @param state st
	 * @return action
	 */
	private int RandomGhostAction(char[] st) {
		int action = random.nextInt(4);
        while (!maze.isValidGhostMove(action,st)){
        	action = random.nextInt(4);
        }
        return action;
	}
	
	/**
	 * UCT maze solving test
	 * @param args
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws InterruptedException {

		UCT uct = new UCT();
		
		while(true){
            // PRINT MAP
			uct.maze.printMap();
            // CHECK IF WON OR LOST, THEN RESET
            if(uct.maze.isGoalReached()){
                System.out.println("GOAL REACHED");
                uct.maze.resetMaze();
                return;
            }
            
            if(uct.maze.isAvatarDead(uct.maze.map)){
                System.out.println("AVATAR DEAD");
                uct.maze.resetMaze();
                return;
            }
            
            //FIND THE OPTIMAL ACTION VIA UTC
            int bestAction = uct.runUCT();
            
            //ADVANCE THE GAME WITH MOVES OF AGENT AND GHOST
            uct.maze.goToNextState(bestAction);
            int bestGhostAction = uct.random.nextInt(4);
            while (!uct.maze.isValidGhostMove(bestGhostAction)){
            	bestGhostAction = uct.random.nextInt(4);
            }
            uct.maze.goToNextGhostState(bestGhostAction);
            
            //TRACK THE GAME VISUALY
            Thread.sleep(1000);
        }
		
	}

}

