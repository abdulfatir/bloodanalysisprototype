import java.io.*;
import javax.imageio.*;
import java.awt.*;
import java.awt.image.*;
import static java.lang.System.out;
public class OtsuSegmentation
{
	public static void main(String args[])throws IOException
	{
		BufferedImage raw,binary;
		raw = ImageIO.read(new File("test.jpg"));
		int W,H;
		W = raw.getWidth();
		H = raw.getHeight();
		binary = new BufferedImage(W,H,raw.getType());
		// Making Histogram
		int[] histogram=new int[256];
		for (int Y=0;Y<H;Y++)
		{
			for(int X=0;X<W;X++)
			{
				int RGB = raw.getRGB(X,Y);
				int GRAY = RGB & 0xff;
				histogram[GRAY]++;
			}
		}
		// Finding the Otsu threshold
		int N_OF_PIXELS = W*H;
		float sum = 0;
        for(int i=0; i<256; i++) 
			sum += i*histogram[i];
		float sumB = 0;
		int wB = 0;
		int wF = 0;
		float max_var = 0;
		int threshold = 0;
		for(int i=0 ; i<256 ; i++) 
		{
			wB += histogram[i];
			if(wB == 0) 
				continue;
			wF = N_OF_PIXELS-wB;
			if(wF == 0) 
				break;
			sumB += (i*histogram[i]);
			float mB = sumB / wB;
			float mF = (sum - sumB) / wF;
			float cur_var = (float) wB*(float) wF*(mB-mF)*(mB-mF);
			if(cur_var > max_var) 
			{
				max_var = cur_var;
				threshold = i;
			}
		}
		out.println(threshold);
		// Binarizing based on Ostu Threshold
		for (int Y=0;Y<H;Y++)
		{
			for(int X=0;X<W;X++)
			{
				int RGB = raw.getRGB(X,Y);
				int GRAY = RGB & 0xff;
				if(GRAY > threshold)
				{
					binary.setRGB(X,Y, 0xffffff);
				}
				else
				{
					binary.setRGB(X,Y, 0x00);
				}
			}
		}
		ImageIO.write(binary,"JPG",new File("binary.jpg"));
	}
}
