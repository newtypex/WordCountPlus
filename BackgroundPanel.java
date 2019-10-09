package wordCountPlus;

import java.awt.Graphics;
import java.awt.Image;
import javax.swing.JPanel;

public class BackgroundPanel extends JPanel  //也可以另外设�?个class
{  
    Image im;  
    public BackgroundPanel(Image im)  
    {  
        this.im=im;  
        this.setOpaque(true);                    //设置控件不�?��??,若是false,那么就是透�??
    }  
    //Draw the background again,继承自Jpanle,是Swing控件�?要继承实现�?方�?,而不是AWT中的Paint()
    public void paintComponent(Graphics g)       //绘图类,详�?可见博主的Java �? java-Graphics 
    {  
        super.paintComponents(g);  
        g.drawImage(im,0,0,this.getWidth(),this.getHeight(),this);  //绘制�?定图像中当前可用�?图像�?�图像的左上角位于该图形上下文坐�??空间�? (x, y)。图像中�?透�?�像�?不影响该�?已存在�?像�?

    }  
}
