
/**
 * A filter to darken image
 * 
 * @author Benigno Chihuahua 
 * @version 1/26/2018
 */
public class DarkenImage implements Filter
{
 
    @Override
    public void filter(PixelImage pi)
    {
        Pixel[][] data = pi.getData();
        for(int row = 0; row < data.length; row++)
        {
            for(int col = 0; col < data[0].length; col++)
            {
                data[row][col].setRed(getPicVal(data[row][col].getRed() - 10));
                data[row][col].setBlue(getPicVal(data[row][col].getBlue() - 10));
                data[row][col].setGreen(getPicVal(data[row][col].getGreen() - 10));
            }
        }
        
        pi.setData(data);
    }
    
    public int getPicVal(int val)
    {
        return (val < 0) ? 0: (val > 255) ? 255: val;
    }
}