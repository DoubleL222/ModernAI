import java.util.ArrayList;

public class NeuralNetworkNode {
	public enum ForwardActivationFunction
	{
		Sigmoid
	}
	
	private float value;
	
	public float getValue() {
		return value;
	}

	public void setValue(float value) {
		this.value = value;
	}

	private ArrayList<NeuralNetworkConnection> in;
	private ArrayList<NeuralNetworkConnection> out;
	
	public void AddInConnection(NeuralNetworkConnection _in) 
	{
		in.add(_in);
	}
	
	public void AddOutConnection(NeuralNetworkConnection _out) 
	{
		out.add(_out);
	}

	public NeuralNetworkNode() 
	{
		in = new ArrayList<NeuralNetworkConnection>();
		out = new ArrayList<NeuralNetworkConnection>();
	}
	
	public void ForwardActivation(ForwardActivationFunction _fun) 
	{
		if(_fun == ForwardActivationFunction.Sigmoid) 
		{
			float sum = 0;
			for(NeuralNetworkConnection _con : in) 
			{
				sum += _con.getWeight()*_con.getFrom().getValue();
			}
			value = sum;
		}
	}
}
