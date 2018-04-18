/**
 * A class to flip the image vertically
 * 
 * @author Benigno Chihuahua 
 * @version 1/26/2018
 */
public class FlipVerticalFilter implements Filter
{        
   public void filter(PixelImage pi) {
       Pixel[][] data = pi.getData();    
       

       
        for (int row = 0; row < data.length/2; row++) 
        {    
            for (int col = 0; col < data[row].length; col++) {
                //get the current pixel because we are going to change it
                Pixel temp = data[row][col];
            
                //reassign the pixel value to be the value of the last pixel in the col
                data[row][col] = data[data.length - 1 - row][col];
                
                //get the temp pixel and place it in the pixel that you switched from
                data[data.length - 1 - row][col] = temp;
            }
            

        }
        
        pi.setData(data);
    }   
}       