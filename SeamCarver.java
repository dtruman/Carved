import java.awt.Color;

import edu.neumont.ui.Picture;


public class SeamCarver
{
	Picture SCPic;
	EnergyKeeper[][] EK;
	
	public SeamCarver(Picture pic)
	{
		SCPic=pic;
		
		EK=new EnergyKeeper[width()][height()];
		
		for(int i=0; i<width(); i++)
		{
			for(int j=0; j<height(); j++)
			{
				EK[i][j]=new EnergyKeeper();
			}
		}
	}
	
	public Picture getPicture()
	{
		return SCPic;
	}
	
	public int width()
	{
		return SCPic.width();
	}
	
	public int height()
	{
		return SCPic.height();
	}
	
	public double energy(int x, int y)
	{
		if(x<0 || x>width() || y<0 || y>height())
			throw new IndexOutOfBoundsException("Energy index does not meet requirements");
		
		int ROne;
		int GOne;
		int BOne;
		if(x==(width()-1))
		{
			ROne=SCPic.get(x-1, y).getRed()-SCPic.get(0, y).getRed();
			GOne=SCPic.get(x-1, y).getGreen()-SCPic.get(0,y).getGreen();
			BOne=SCPic.get(x-1, y).getBlue()-SCPic.get(0, y).getBlue();
		}
		else if(x==0)
		{
			ROne=SCPic.get(width()-1, y).getRed()-SCPic.get(x+1, y).getRed();
			GOne=SCPic.get(width()-1, y).getGreen()-SCPic.get(x+1,y).getGreen();
			BOne=SCPic.get(width()-1, y).getBlue()-SCPic.get(x+1, y).getBlue();
		}
		else
		{
			ROne=SCPic.get(x-1, y).getRed()-SCPic.get(x+1, y).getRed();
			GOne=SCPic.get(x-1, y).getGreen()-SCPic.get(x+1,y).getGreen();
			BOne=SCPic.get(x-1, y).getBlue()-SCPic.get(x+1, y).getBlue();
		}
		double sumOne=Math.pow(ROne+GOne+BOne,2);
		
		int RTwo;
		int GTwo;
		int BTwo;
		
		if(y==(height()-1))
		{
			RTwo=SCPic.get(x, y-1).getRed()-SCPic.get(x, 0).getRed();
			GTwo=SCPic.get(x,y-1).getGreen()-SCPic.get(x,0).getGreen();
			BTwo=SCPic.get(x, y-1).getBlue()-SCPic.get(x,0).getBlue();
		}
		else if(y==0)
		{
			RTwo=SCPic.get(x, height()-1).getRed()-SCPic.get(x, y+1).getRed();
			GTwo=SCPic.get(x,height()-1).getGreen()-SCPic.get(x,y+1).getGreen();
			BTwo=SCPic.get(x, height()-1).getBlue()-SCPic.get(x,y+1).getBlue();
		}
		else
		{
			RTwo=SCPic.get(x, y-1).getRed()-SCPic.get(x, y+1).getRed();
			GTwo=SCPic.get(x,y-1).getGreen()-SCPic.get(x,y+1).getGreen();
			BTwo=SCPic.get(x, y-1).getBlue()-SCPic.get(x,y+1).getBlue();
		}
		double sumTwo=Math.pow((RTwo+GTwo+BTwo), 2);
		
		return sumOne+sumTwo;
	}
	
	public int[] findHorizontalSeam()
	{
		for(int j=0; j<height(); j++)
		{
			for(int i=0; i<width(); i++)
			{
				EK[i][j].sumEnergy=energy(i,j);

				if(i>0)
				{
					if(j==0)
					{
						if(EK[i-1][j].sumEnergy<EK[i-1][j+1].sumEnergy)
						{
							EK[i][j].sumEnergy+=EK[i-1][j].sumEnergy;
							EK[i][j].parent=EK[i-1][j];
						}
						else
						{
							EK[i][j].sumEnergy+=EK[i-1][j+1].sumEnergy;
							EK[i][j].parent=EK[i-1][j+1];
						}
					}
					else if(j==height()-1)
					{
						if(EK[i-1][j].sumEnergy<EK[i-1][j-1].sumEnergy)
						{
							EK[i][j].sumEnergy+=EK[i-1][j].sumEnergy;
							EK[i][j].parent=EK[i-1][j];
						}
						else
						{
							EK[i][j].sumEnergy+=EK[i-1][j-1].sumEnergy;
							EK[i][j].parent=EK[i-1][j-1];
						}
					}
					else
					{
						if(EK[i-1][j].sumEnergy<EK[i-1][j-1].sumEnergy && EK[i-1][j].sumEnergy<EK[i-1][j+1].sumEnergy)
						{
							EK[i][j].sumEnergy+=EK[i-1][j].sumEnergy;
							EK[i][j].parent=EK[i-1][j];
						}
						else if(EK[i-1][j-1].sumEnergy<EK[i-1][j+1].sumEnergy && EK[i-1][j-1].sumEnergy<EK[i-1][j].sumEnergy)
						{
							EK[i][j].sumEnergy+=EK[i-1][j-1].sumEnergy;
							EK[i][j].parent=EK[i-1][j-1];
						}
						else
						{
							EK[i][j].sumEnergy+=EK[i-1][j+1].sumEnergy;
							EK[i][j].parent=EK[i-1][j+1];
						}
					}
					EK[i][j].trackPosition=j;
				}
			}
		}
		
		int[] ret=new int[width()];
		
		ret[0]=0;
		
		for(int i=1; i<height(); i++)
		{
			if(EK[width()-1][i].sumEnergy<EK[width()-1][ret[0]].sumEnergy)
			{
				ret[0]=i;
			}
		}
		EnergyKeeper temp=EK[width()-1][ret[0]];
		int trav=1;
		while(temp.parent!=null && trav<ret.length)
		{
			ret[trav++]=temp.parent.trackPosition;
			temp=temp.parent;
		}
		
		return ret;
	}
	
	public int[] findVerticalSeam()
	{
		for(int i=0; i<width(); i++)
		{
			for(int j=0; j<height(); j++)
			{
				EK[i][j].sumEnergy=energy(i,j);
				
				if(j>0)
				{
					if(i==0)
					{
						if(EK[i][j-1].sumEnergy<EK[i+1][j-1].sumEnergy)
						{
							EK[i][j].sumEnergy+=EK[i][j-1].sumEnergy;
							EK[i][j].parent=EK[i][j-1];
						}
						else
						{
							EK[i][j].sumEnergy+=EK[i+1][j-1].sumEnergy;
							EK[i][j].parent=EK[i+1][j-1];
						}
					}
					else if(i==width()-1)
					{
						if(EK[i][j-1].sumEnergy<EK[i-1][j-1].sumEnergy)
						{
							EK[i][j].sumEnergy+=EK[i][j-1].sumEnergy;
							EK[i][j].parent=EK[i][j-1];
						}
						else
						{
							EK[i][j].sumEnergy+=EK[i-1][j-1].sumEnergy;
							EK[i][j].parent=EK[i-1][j-1];
						}
					}
					else
					{
						if(EK[i][j-1].sumEnergy<EK[i-1][j-1].sumEnergy && EK[i][j-1].sumEnergy<EK[i+1][j-1].sumEnergy)
						{
							EK[i][j].sumEnergy+=EK[i][j-1].sumEnergy;
							EK[i][j].parent=EK[i][j-1];
						}
						else if(EK[i-1][j-1].sumEnergy<EK[i+1][j-1].sumEnergy && EK[i-1][j-1].sumEnergy<EK[i][j-1].sumEnergy)
						{
							EK[i][j].sumEnergy+=EK[i-1][j-1].sumEnergy;
							EK[i][j].parent=EK[i-1][j-1];
						}
						else
						{
							EK[i][j].sumEnergy+=EK[i+1][j-1].sumEnergy;
							EK[i][j].parent=EK[i+1][j-1];
						}
					}
					EK[i][j].trackPosition=i;
				}
			}
		}
		
		int[] ret=new int[height()];
		
		ret[0]=0;
		
		for(int i=1; i<width(); i++)
		{
			if(EK[i][height()-1].sumEnergy<EK[ret[0]][height()-1].sumEnergy)
				ret[0]=i;
		}
		EnergyKeeper temp=EK[ret[0]][height()-1];
		int trav=1;
		while(temp.parent!=null && trav<ret.length)
		{
			ret[trav++]=temp.parent.trackPosition;
			temp=temp.parent;
		}
		
		return ret;
	}
	
	public void removeHorizontalSeam(int[] indices)
	{
		if(height()==1 || indices.length!=width())
			throw new IllegalArgumentException("Removing the Horizontal Seam Failed");
		
		Picture temp=new Picture(width(), height()-1);
		
		for(int i=0; i<width(); i++)
		{
			for(int j=0; j<height()-1; j++)
			{
				if(indices[i]<0 || indices[i]>height())
					throw new IndexOutOfBoundsException("Removing the Horizontal Seam failed because the height " + indices[i] + " at index " + i + " does not meet the requirements");
				
				if(i>0 && i!=width()-1 && (indices[i]-indices[i-1]>=2 || indices[i]-indices[i-1]<=-2))
					throw new IllegalArgumentException("The indices " + indices[i] + " at index " + i + " has a difference from the last last vertice " + indices[i-1] + " position greater than length 1");
				
				if(j>=indices[i])
				{
					temp.set(i, j, SCPic.get(i, j+1));
				}
				else
				{
					temp.set(i,j, SCPic.get(i, j));
				}
			}
		}
		
		SCPic=temp;
	}
	
	public void removeVerticalSeam(int[] indices)
	{
		if(width()==1 || indices.length!=height())
			throw new IllegalArgumentException("Removing the Horizontal Seam Failed");
		
		Picture temp=new Picture(width()-1, height());
		
		for(int i=0; i<width()-1; i++)
		{
			for(int j=0; j<height(); j++)
			{
				if(indices[j]<0 || indices[j]>width())
					throw new IndexOutOfBoundsException("Removing the Vertical Seam failed because the width " + indices[j] + " at index " + j + " does not meet the requirements");
				
				if(j>0 && j!=height()-1 && (indices[j]-indices[j-1]>=2 || indices[j]-indices[j-1]<=-2))
					throw new IllegalArgumentException("The indices " + indices[j] + " at index " + j + " has a difference from the last vertice " + indices[j-1] + " position greater than length 1");
				
				if(i>=indices[j])
				{
					temp.set(i,j,SCPic.get(i+1,j));
				}
				else
				{
					temp.set(i,j,SCPic.get(i,j));
				}
			}
		}
		
		SCPic=temp;
	}
}
