import edu.neumont.ui.Picture;


public class main {

	public static void main(String[] args)
	{
		Picture p=new Picture("C:\\Users\\dtruman\\workspace\\Carved\\src\\overlayimagewithhiddenmessage.png");
	
		SeamCarver sc=new SeamCarver(p);
		
		for(int i=0; i<190; i++)
		{
			sc.removeVerticalSeam(sc.findVerticalSeam());
		}
		for(int i=0; i<140; i++)
		{
			sc.removeHorizontalSeam(sc.findHorizontalSeam());
		}
		
		sc.getPicture().save("C:\\Users\\dtruman\\Documents\\stuff.png");
	}

}