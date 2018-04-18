
/**
 * Converts the RGB image to a grayscale one
 * 
 * @author Benigno Chihuahua 
 * @version 1/26/2018
 */
public class GrayScaleFilter implements Filter
{
    
    @Override 
    public void filter(PixelImage pi)
    {
     Pixel[][] data = pi.getData();
     int average = 0;
     for(int r = 0; r < data.length; r++)
     {
         for(int c = 0; c < data[0].length; c++)
         {
             average = (data[r][c].getRed() + data[r][c].getBlue() + data[r][c].getGreen())/3;
             data[r][c].setRed(average);
             data[r][c].setBlue(average);
             data[r][c].setGreen(average);
         }
     }
        
     pi.setData(data);
        
    }
    
}