
/**
 * Defines filter functionality for filters that use matrixes
 * 
 * @author Benigno Chihuahua 
 * @version 1/26/2018
 */
public class MatrixFilter implements Filter
{
    //could replace with a new Kernel class to extend functionality 
    //to matrixes with a radius greater than 1
    protected int[][] filterMatrix = new int[3][3];
    protected boolean scale = false;
    
    protected enum Color 
    {
        RED ,
        GREEN ,
        BLUE
    }
    
    public MatrixFilter(int[][] fMat, boolean scale)
    {
        filterMatrix = fMat;
        this.scale = scale;
    
    }
    
    public MatrixFilter()
    {
        
    }
    
    @Override
    public void filter(PixelImage pi)
    {
        Pixel[][] data = pi.getData();
        Pixel[][] newData = pi.getData();
        
        for(int i = 1; i < data.length - 1; i++)
        {
            for(int j = 1; j < data[0].length - 1; j++)
            {
                newData[i][j].setRed(calculateWeightedAverage(data, i, j, filterMatrix, Color.RED,scale));
                newData[i][j].setGreen(calculateWeightedAverage(data, i, j, filterMatrix, Color.GREEN,scale));
                newData[i][j].setBlue(calculateWeightedAverage(data, i, j, filterMatrix, Color.BLUE,scale));
            }
        }
        
        pi.setData(newData);
    }
    
    public int calculateWeightedAverage(Pixel[][] imageData,int row,int col,int[][] fMat, Color channel, boolean divideBySixteen)
    {
        int sum = 0;
        
        for(int i = 0; i < fMat.length; i++)
        {
            for(int j = 0;j < fMat[0].length; j++)
            {
                if(channel == Color.RED)
                    sum += imageData[row - 1 + i][col - 1 + j].getRed() * fMat[i][j];
                
                else if(channel == Color.BLUE)
                    sum += imageData[row - 1 + i][col - 1 + j].getBlue() * fMat[i][j];
                
                else if(channel == Color.GREEN)
                    sum += imageData[row -1 + i][col - 1 + j].getGreen() * fMat[i][j];
            }
        }
       
        return divideBySixteen ? sum/16 : picVal(sum);
    }
    
    protected int picVal(int val)
    {
        return (val > 255) ? 255: (val < 0) ? 0: val;
    }
}
    
    
