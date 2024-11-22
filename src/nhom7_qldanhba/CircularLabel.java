
package nhom7_qldanhba;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;

class CircularLabel extends JLabel {
    public CircularLabel(String text) {
        super(text);
        setOpaque(false); // Cho phép vẽ hình tròn
        setHorizontalAlignment(SwingConstants.CENTER);
        setVerticalAlignment(SwingConstants.CENTER);
    }

    @Override
    protected void paintComponent(Graphics g) {
        // Vẽ hình tròn
        Graphics2D g2d = (Graphics2D) g.create();
        int width = getWidth();
        int height = getHeight();
        g2d.setClip(new Ellipse2D.Double(0, 0, width, height));
        super.paintComponent(g2d);
        g2d.dispose();
    }

    @Override
    public void paintBorder(Graphics g) {
        // Vẽ viền hình tròn
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setColor(getForeground()); // Màu viền
        g2d.setStroke(new BasicStroke(3)); // Độ dày viền
        g2d.draw(new Ellipse2D.Double(0, 0, getWidth(), getHeight()));
        g2d.dispose();
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(140, 140); // Kích thước ưa thích
    }

    @Override
    public void setIcon(Icon icon) {
        super.setIcon(icon);
        setHorizontalAlignment(SwingConstants.CENTER);
        setVerticalAlignment(SwingConstants.CENTER);
    }
}