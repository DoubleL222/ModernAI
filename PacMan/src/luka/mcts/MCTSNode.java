package luka.mcts;
import java.util.ArrayList;
import java.util.List;

public class MCTSNode<T> {
	private T game_state = null;
	private List<MCTSNode<T>> children = new ArrayList<MCTSNode<T>>();
	private MCTSNode<T> parent = null;
	public int parent_action=-1;
	public float reward =0;
	public int timesvisited = 0;
}
