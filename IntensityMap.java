import java.io.*;
import javax.imageio.*;
import java.awt.*;
import java.awt.image.*;
import static java.lang.System.out;
public class IntensityMap
{
	public static void main(String ars[])throws IOException
	{
		BufferedImage raw,map;
		raw = ImageIO.read(new File("3.JPG"));
		int W,H;
		W = raw.getWidth();
		H = raw.getHeight();
		map = new BufferedImage(W,H,raw.getType());
		for (int Y=0;Y<H;Y++)
		{
			for(int X=0;X<W;X++)
			{
				int pixel = raw.getRGB(X,Y);
				float gray = pixel & 0xff;
				map.setRGB(X,Y,Color.getHSBColor(0.7f*(gray/255.0f),1,1).getRGB());
			}
		}
		ImageIO.write(map,"PNG", new File("3_map.png"));
	}
}
