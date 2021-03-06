/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

/**
 *
 * @author Renegade
 */
import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;
import java.awt.font.*;
import dtn.Network;
import results.RoutingTime;

public class GraphingData extends JPanel {

    double[] routingTime;
    double[] initDistance;
    final int PAD = 20;

    public GraphingData(Network network, String type, int binSize) {
        
        /* Initializing data according to garaph type*/
        RoutingTime rt = new RoutingTime(network);
        if(type.equalsIgnoreCase("Average")) {
            routingTime = RoutingTime.binSort(rt.getInitDistance(), rt.getInitDistance(), binSize);
            initDistance = new double[routingTime.length];
            for(int i=0; i < initDistance.length; i++) {
                initDistance[i] = i * binSize;
            }
        } else if(type.equalsIgnoreCase("Scatter")) {
            routingTime = rt.getRoutingTime();
            initDistance = rt.getInitDistance();
        }
        
        initComponents();
    }

    private void initComponents() {
        setBackground(new java.awt.Color(255, 255, 255));
        setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 600, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 600, Short.MAX_VALUE)
        );
    }
    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        int w = getWidth();
        int h = getHeight();

        /* Draw ordinate */
        g2.draw(new Line2D.Double(PAD, PAD, PAD, h - PAD));

        /* Draw abcissa */
        g2.draw(new Line2D.Double(PAD, h - PAD, w - PAD, h - PAD));

        /* Draw labels */
        Font font = g2.getFont();
        FontRenderContext frc = g2.getFontRenderContext();
        LineMetrics lm = font.getLineMetrics("0", frc);
        float sh = lm.getAscent() + lm.getDescent();

        /* Ordinate label */
        String s = "Routing Time";
        float sy = PAD + ((h - 2 * PAD) - s.length() * sh) / 2 + lm.getAscent();
        for (int i = 0; i < s.length(); i++) {
            String letter = String.valueOf(s.charAt(i));
            float sw = (float) font.getStringBounds(letter, frc).getWidth();
            float sx = (PAD - sw) / 2;
            g2.drawString(letter, sx, sy);
            sy += sh;
        }

        /* Abcissa label */
        s = "Initial Distance";
        sy = h - PAD + (PAD - sh) / 2 + lm.getAscent();
        float sw = (float) font.getStringBounds(s, frc).getWidth();
        float sx = (w - sw) / 2;
        g2.drawString(s, sx, sy);
        double xInc = (double) (w - 2 * PAD) / (routingTime.length - 1);
        double scale = (double) (h - 2 * PAD) / getMax();

        /* Mark routingTime points. */
        g2.setPaint(Color.red);
        for (int i = 0; i < routingTime.length; i++) {
            if (routingTime[i] > 0) {
                double x = PAD + 6 * initDistance[i] * xInc;
                double y = h - PAD - scale * routingTime[i];
                g2.fill(new Ellipse2D.Double(x - 1, y - 1, 2, 2));
            }
        }
    }

    private double getMax() {
        double max = -Integer.MAX_VALUE;
        for (int i = 0; i < routingTime.length; i++) {
            if (routingTime[i] > max) {
                max = routingTime[i];
            }
        }
        return max;
    }

}
