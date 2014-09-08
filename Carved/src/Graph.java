
public class Graph
{
	private double[][] matrix;
	private int numEdge;
	private int[] Mark;

	public Graph(int v)
	{
		Init(v);
	}
	
	private void Init(int n)
	{
		Mark=new int[n];
		matrix=new double[n][n];
		numEdge=0;
	}
	
	public int vcount()
	{
		return Mark.length;
	}
	
	public int ecount()
	{
		return numEdge;
	}
	
	public int first(int v)
	{
		for(int i=0; i<Mark.length; i++)
		{
			if(matrix[v][i]!=0)
				return i;
		}
		return Mark.length;
	}
	
	public int next(int v, int w)
	{
		for(int i=w+1; i<Mark.length; i++)
		{
			if(matrix[v][i]!=0)
				return i;
		}
		return Mark.length;
	}
	
	public void addEdge(int v, int w, double wt)
	{
		if(matrix[v][w]==0 && wt!=0)
		{
			numEdge+=2;
			matrix[v][w]=wt;
			matrix[w][v]=wt;
		}
	}
	
	public void removeEdge(int v, int w)
	{
		if(matrix[v][w]!=0)
		{
			numEdge-=2;
			matrix[v][w]=0;
			matrix[w][v]=0;
		}
	}
	
	public void resetMarks()
	{
		for(int i=0; i<this.Mark.length; i++)
		{
			this.Mark[i]=0;
		}
	}
	
	public boolean isEdge(int v, int w)
	{
		return matrix[v][w]!=0;
	}
	
	public int degree(int v)
	{
		int ret=0;
		for(int i=0; i<Mark.length; i++)
		{
			if(matrix[v][i]!=0)
				ret++;
		}
		return ret;
	}
	
	public int getMark(int v)
	{
		return Mark[v];
	}
	
	public void setMark(int v, int m)
	{
		Mark[v]=m;
	}
}
