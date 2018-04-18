
public class NegativeImageFilter implements Filter
{
   
    @Override 
    public void filter(PixelImage pi)
    {
        Pixel[][] data = pi.getData();
        for(int row = 0; row < data.length; row++)
        {
            for(int col = 0; col < data[0].length; col++)
            {
                data[row][col].setRed(255 - data[row][col].getRed());
                data[row][col].setBlue(255 - data[row][col].getBlue());
                data[row][col].setGreen(255 - data[row][col].getGreen());
            }
        }
        
        pi.setData(data);
    }
    
}