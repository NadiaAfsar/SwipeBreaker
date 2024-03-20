package Models;

import javax.swing.*;
import java.awt.*;

public class Arrow extends JComponent {
    static int x1;
    static int y1;
    static int x2;
    static int y2;
    static int[] xPoints;
    static int[] yPoints;
    static int xo;
    static int yo;
    static double sin;
    static double cos;

    public Arrow(int x1, int y1, int x2, int y2) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        trianglePoints();
    }
    public static void draw(Graphics g) {
        Graphics2D g2D = (Graphics2D)g;
        g2D.setStroke(new BasicStroke(7));
        g2D.setColor(Color.PINK);
        g2D.drawLine(x1, y1, xo, yo);
        g2D.fillPolygon(xPoints, yPoints, 3);
        float[] dashingPattern = {2f, 2f};
        Stroke stroke = new BasicStroke(2f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 1.0f,
                dashingPattern, 2.0f);
        g2D.setStroke(stroke);
        g2D.drawLine(xo, yo, xo + (int)(cos*900), yo - (int)(sin*900));
    }
    private void trianglePoints() {
        int dy = y1 - y2;
        int dx = x2 - x1;
        double d = Math.sqrt(dx*dx + dy*dy);
        sin = dy/d;
        cos = dx/d;
        xo = x1 + (int)(150*cos);
        yo = y1 - (int)(150*sin);
        double a = sin*0.7;
        double b = cos*0.7;
        double sin1 = a - b;
        double cos1 = b + a;
        double sin2 = b - a;
        double cos2 = a + b;
        xPoints = new int[]{x1 + (int)(160*cos), xo - (int)(cos1*20), xo - (int)(sin2*20)};
        yPoints = new int[]{y1 - (int)(160*sin), yo + (int)(sin1*20), yo + (int)(cos2*20)};
    }
}
