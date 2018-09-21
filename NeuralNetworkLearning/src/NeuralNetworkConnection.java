
public class NeuralNetworkConnection {
	private NeuralNetworkNode from;
	public NeuralNetworkNode getFrom() {
		return from;
	}

	public void setFrom(NeuralNetworkNode from) {
		this.from = from;
	}

	public NeuralNetworkNode getTo() {
		return to;
	}

	public void setTo(NeuralNetworkNode to) {
		this.to = to;
	}

	public float getWeight() {
		return weight;
	}

	public void setWeight(float weight) {
		this.weight = weight;
	}

	private NeuralNetworkNode to;
	private float weight;
	
	public NeuralNetworkConnection(NeuralNetworkNode _from, NeuralNetworkNode _to) 
	{
		from = _from;
		to = _to;
		
		_from.setOut(this);
		_to.setIn(this);
	}

	public NeuralNetworkConnection(NeuralNetworkNode from, NeuralNetworkNode to, float weight) {
		this.from = from;
		this.to = to;
		this.weight = weight;
		
		from.setOut(this);
		to.setIn(this);
	}
}
