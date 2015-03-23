import java.io.*;
import javax.imageio.*;
import java.awt.*;
import java.awt.image.*;
import static java.lang.System.out;
public class SingleSpotAnalysis
{
	private static final int ORDER = 3;
	public static void main(String args[])throws IOException
	{
		BufferedImage raw;
		raw = ImageIO.read(new File("test.jpg"));
		int W,H;
		W = raw.getWidth();
		H = raw.getHeight();
		
		// Pre-processing OR Noise Reduction
		BufferedImage pre_proc = medianFilter(raw);
		ImageIO.write(pre_proc, "JPG",new File("pre_proc.jpg"));
		
		// Segmentation using Otsu's Method OR Detection
		int[] histogram = getHistogram(pre_proc);
		int T = getThreshold(histogram, W*H);
		BufferedImage binary = getBinaryImage(pre_proc,T);
		ImageIO.write(binary, "JPG",new File("bin.jpg"));
		
		// Measurement
		int area = getSpotArea(binary);
		int[] pixeldata = new int[area];
		int i=0;
		for (int Y=0;Y<H;Y++)
		{
			for(int X=0;X<W;X++)
			{
				if((binary.getRGB(X,Y) & 0xff) == 0x00)
				{
					int RGB = pre_proc.getRGB(X,Y);
					int GRAY = RGB & 0xff;
					pixeldata[i++] = GRAY;
				}
			}
		}
		
		out.println("Spot Area: "+area+" pixels.");
		float MEAN = mean(pixeldata);
		out.println("Mean Intensity: "+MEAN);
		out.println("Std. Deviation: "+stdDev(pixeldata, MEAN));
		out.println("Spot Volume: "+area*MEAN);
	}
	
	public static int[] getHistogram(BufferedImage raw)
	{
		int W,H;
		W = raw.getWidth();
		H = raw.getHeight();
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
		return histogram;
	}
	
	public static int getThreshold(int[] histogram, int N_OF_PIXELS)
	{
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
		return threshold;
	}
	
	public static BufferedImage getBinaryImage(BufferedImage raw, int threshold)
	{
		int W,H;
		W = raw.getWidth();
		H = raw.getHeight();
		BufferedImage binary = new BufferedImage(W,H,raw.getType());
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
		return binary;
	}
	
	public static float mean(int[] data)
	{
		float sum = 0f;
		int min=255,max=0;
		for(int i:data)
		{
			sum+=i;
			if(i>max)
				max=i;
			if(i<min)
				min=i;
		}
		out.println("Min: "+min);
		out.println("Max: "+max);
		return sum/data.length;
	}
	
	public static float stdDev(int[] data, float mean)
	{
		float sum = 0f;
		for(int i:data)
		{
			float diff = (i-mean);
			sum += (diff*diff);
		}
		return (float)Math.sqrt(sum/data.length);
	}
	
	public static int getSpotArea(BufferedImage binary)
	{
		int W,H;
		W = binary.getWidth();
		H = binary.getHeight();
		int area = 0;
		for (int Y=0;Y<H;Y++)
		{
			for(int X=0;X<W;X++)
			{
				int RGB = binary.getRGB(X,Y);
				int GRAY = RGB & 0xff;
				if (GRAY == 0x00)
				{
					area++;
				}
			}
		}
		return area;
	}
	
	public static BufferedImage medianFilter(BufferedImage raw)
	{
		int W,H;
		W = raw.getWidth();
		H = raw.getHeight();
		BufferedImage median_filtered = new BufferedImage(W,H,raw.getType());
		for (int Y=0;Y<H;Y++)
		{
			for(int X=0;X<W;X++)
			{
				int[] neighbors = new int[ORDER*ORDER];
				int k=0;
				for(int y=0;y<ORDER;y++)
				{
					for(int x=0;x<ORDER;x++)
					{
						int tx = x+X-1;
						int ty = y+Y-1;
						if((tx>=0 && tx<W) && (ty>=0 && ty<H))
						{
							neighbors[k++] = raw.getRGB(tx,ty) & 0xff;
						}
					}
				}
				java.util.Arrays.sort(neighbors);
				median_filtered.setRGB(X,Y,new Color(neighbors[(ORDER*ORDER)/2],neighbors[(ORDER*ORDER)/2],neighbors[(ORDER*ORDER)/2]).getRGB());
			}
		}
		return median_filtered;
	}
}
