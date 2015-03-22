import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.Color;
import java.io.File;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.IOException;
import static java.lang.System.out;
public class Convolution
{
	public static String filename = "test.jpg";
	public static void main(String args[])throws IOException
	{
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		
		out.print("Kernel Order:");
		int order = Integer.parseInt(reader.readLine());
		
		float[][] kernel = new float[order][order];
		float sum_of_elements = 0.0f;
		float mult_factor = 1.0f;
		float bias = 0f;
		
		// Getting the Kernel Matrix as input from the user
		for(int i=0; i < order; i++)
		for(int j=0; j < order; j++)
		{
			out.print(i+","+j+":");
			kernel[i][j] = Float.parseFloat(reader.readLine());
		}
		
		out.println("\nThe Kernel Matrix is:\n");
		
		for(int i=0; i < order; i++)
		{
			for(int j=0; j < order; j++)
			{
				out.print("\t"+kernel[i][j]);
				sum_of_elements += kernel[i][j];
			}
			out.println();
		}
		
		out.println("\nThe sum of matrix elements is: "+sum_of_elements);
		
		// mult_factor is the value with which each computed sum is multiplied
		// mult_factor = 1 gives no changes to input kernel matrix 
		out.print("\nMultiplication Factor: ");
		mult_factor = Float.parseFloat(reader.readLine());
		
		// Bias can be added to increase brightness of the image 
		// bias = 0 gives no change in brightness if the sum_of_elements is 1
		out.print("Bias: ");
		bias = Float.parseFloat(reader.readLine());
		
		BufferedImage input,output;
		
		input = ImageIO.read(new File(filename));
		int WIDTH = input.getWidth();
		int HEIGHT = input.getHeight();
		
		output = new BufferedImage(WIDTH, HEIGHT, input.getType());
		out.println("[*] Rendering the image...");
		for(int x=0;x<WIDTH;x++)
		{
			for(int y=0;y<HEIGHT;y++)
			{
				float gray=0f;
				for(int i=0;i<order;i++)
				{
					for(int j=0;j<order;j++)
					{
						// Calculating X and Y coordinates of the pixel to be multiplied with current kernel element
						// In case of edges of image the '% WIDTH' wraps the image and the pixel from opposite edge is used
						int imageX = (x - order / 2 + i + WIDTH) % WIDTH; 
						int imageY = (y - order / 2 + j + HEIGHT) % HEIGHT; 
						
						int RGB = input.getRGB(imageX,imageY);
						int GRAY = (RGB) & 0xff; // Blue Channel
						
						// The RGB is multiplied with current kernel element and added on to the variables red, blue and green
						gray += (GRAY*kernel[i][j]);
					}
				}
				int outG;
				// The value is truncated to 0 and 255 if it goes beyond
				outG = Math.min(Math.max((int)(gray*mult_factor),0),255);
				// Pixel is written to output image
				output.setRGB(x,y,new Color(outG,outG,outG).getRGB());
			}
		}
		out.print("[?] Save file as:");
		String outputfname = reader.readLine();
		ImageIO.write(output,"JPG", new File(outputfname+".jpg"));
		out.print("[+] File saved as "+outputfname+".jpg");
	}
}
