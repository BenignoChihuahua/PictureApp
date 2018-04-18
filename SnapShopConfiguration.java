/**
 * A class to configure the SnapShop application
 * 
 * @author Benigno Chihuahua 
 * @version 1/26/2018
 */
public class SnapShopConfiguration
{
    static int[][] guassMat =   {{1,2,1},
                                {2,4,2},
                                {1,2,1}};
                                
    static int[][] laplaceMat = {{-1, -1, -1},
                                {-1, 8, -1},
                                {-1, -1,-1}};
                                
    static int[][] ebsomMat = {{-1, -1, 0},
                               {-1, 0, 1},
                               {0, 1, 1}};
                               
    static int[][] unsharpMat = {{-1,-2,-1},
                                 {-2, 28, -2},
                                 {-1, -2, -1}};
                                 
    static int[][] edgyMat = {{-1,-1,-1},
                           {-1, 9, -1},
                           {-1, -1, -1}};
    /**
     * Method to configure the SnapShop.  Call methods like addFilter
     * and setDefaultFilename here.
     * @param theShop A pointer to the application
     */
    public static void configure(SnapShop theShop)
    {   theShop.setDefaultFilename("monet.jpg");
        theShop.addFilter(new FlipHorizontalFilter(), "Flip Horizontal");
        theShop.addFilter(new FlipVerticalFilter(), "Flip Vertical");
        theShop.addFilter(new NegativeImageFilter(), "Reverse Image");  
        theShop.addFilter(new GrayScaleFilter(), "Gray Scale Image" );   
        theShop.addFilter(new MatrixFilter(guassMat,true), "Guass");
        theShop.addFilter(new MatrixFilter(laplaceMat,false), "Laplace");
        theShop.addFilter(new MatrixFilter(ebsomMat,false), "Ebsom");
        theShop.addFilter(new MatrixFilter(unsharpMat, true), "Unsharp Masking");
        theShop.addFilter(new MatrixFilter(edgyMat, false), "Edgy");
        theShop.addFilter(new DarkenImage(), "Intensify");
        theShop.addFilter(new LightenBy10Filter(), "Lighten");
        //theShop.addFilter(new LightenBy10Filter(), "Lighten by 10 Image" );
        //theShop.addFilter(new IntensifyFilter(), "Intensify Image" );
        //theShop.addFilter(new EmbossFilter(), "Emboss Filter" );
    }
}