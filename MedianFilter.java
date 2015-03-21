import java.io.*;
import javax.imageio.*;
import java.awt.*;
import java.awt.image.*;
import static java.lang.System.out;
public class MedianFilter
{
	private static int ORDER=3;
	public static void main(String ars[])throws IOException
	{
		BufferedImage raw,median_filtered;
		raw = ImageIO.read(new File("3.JPG"));
		int W,H;
		W = raw.getWidth();
		H = raw.getHeight();
		median_filtered = new BufferedImage(W,H,raw.getType());
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
		ImageIO.write(median_filtered,"PNG", new File("3_median_filtered.png"));
	}
}
