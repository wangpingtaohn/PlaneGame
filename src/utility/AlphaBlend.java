package utility;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public class AlphaBlend
{
    private Image m_changeImage;

    private int m_changeValue = 5;
    private int m_Alpha = 0;
    private int[] rawInt;
    
    public AlphaBlend(Image img)
    {
        m_changeImage = img;    
        m_changeValue = 5;

       rawInt = new int[m_changeImage.getWidth() * m_changeImage.getHeight()];
       m_changeImage.getRGB(rawInt, 0, m_changeImage.getWidth(), 0, 0, m_changeImage.getWidth(), m_changeImage.getHeight());
    }

    private void blend(int[] raw, int alphaValue)	//当前图片的Alpha值
    {  
        for(int i=0; i<raw.length; i++)
        {
            int a = 0;
            if( (raw[i] & 0xFF000000) != 0){
            	            int color = (raw[i] & 0x00FFFFFF); // get the color of the pixel.
            a = alphaValue;     // set the alpha value we vant to use 0-255
            a = (a<<24);    // left shift the alpha value 24 bits.

            color += a;
            raw[i] = color;
            }

        }
    }
    
    public boolean paintImage(Graphics g)
    {
    	m_Alpha += m_changeValue;
        if(m_Alpha>=200){
        	m_changeValue = m_changeValue *-1;
        }
        else if(m_Alpha< 0){
        	return false;
        }

        blend(rawInt, m_Alpha);
        Image fadingImage = Image.createRGBImage(rawInt, m_changeImage.getWidth(), m_changeImage.getHeight(), true);
        
        g.drawImage(fadingImage, 0, 50, 0);
        
        return true;
    }

}