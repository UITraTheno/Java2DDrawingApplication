/*
 * Assignment 5
 * Yilin Li
 * 10/25/2021
 */
package java2ddrawingapplication;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Paint;
import java.awt.Point;
import java.awt.Stroke;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;

/**
 *
 * @author acv
 */
public class DrawingApplicationFrame extends JFrame
{
    ArrayList<MyShapes> Drawn = new ArrayList<>();
    private final JCheckBox fill;
    private final JCheckBox useG;
    private final JCheckBox dash;
    private final JPanel top1;
    private final JPanel top2;
    private final JPanel topPanel;
    private final JPanel shp;
    private final JButton undo;
    private final JButton clear;
    private final JButton color1;
    private final JButton color2;
    private Color col1 = Color.BLACK;
    private Color col2 = Color.WHITE;
    private final JLabel spe;
    public final JComboBox shapes;
    private final BorderLayout layout;
    private final DrawPanel drawpanel;
    private final JLabel mouseTracker = new JLabel("( , )");
    private final JLabel width;
    private final JSpinner wT;
    private final JLabel length;
    private final JSpinner lT;
    
    


    // Create the panels for the top of the application. One panel for each
    // line and one to contain both of those panels.

    // create the widgets for the firstLine Panel.

    //create the widgets for the secondLine Panel.

    // Variables for drawPanel.

    // add status label
  
    // Constructor for DrawingApplicationFrame
    public DrawingApplicationFrame()
    {
        super("Java 2D Drawings");
        layout = new BorderLayout();
        setLayout(layout);
        
        drawpanel = new DrawPanel();
        
        
        shp = new JPanel();
        spe = new JLabel("Shape:");
        shp.setBackground(Color.CYAN);
        shapes = new JComboBox<>(new String[] { "Line", "Oval", "Rectangle" });

        
        shp.add(spe);
        shp.add(shapes);
        
        color1 = new JButton("1st Color...");
        color1.addActionListener(
            new ActionListener(){
             public void actionPerformed(ActionEvent ec1){
                col1 = JColorChooser.showDialog(
                    DrawingApplicationFrame.this, "Choose a color",col1);
                    if (col1 == null)
                        col1 = Color.BLACK;
                    
             }
            }
        );

        
        color2 = new JButton("2nd Color...");
        color2.addActionListener(
        new ActionListener(){
            public void actionPerformed(ActionEvent ec1){
                col2 = JColorChooser.showDialog(
                    DrawingApplicationFrame.this, "Choose a color",col2);
                    if (col2 == null)
                        col2 = Color.WHITE;

            }
        }
        );
        
        
        undo = new JButton("Undo");
        undo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e1) {
		if (Drawn.size() != 0) {
                    Drawn.remove(Drawn.size() - 1);
                    repaint();
		}
            }
	});
        
        clear = new JButton("Clear");
        clear.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e2) {
                    Drawn.clear();
                    repaint();
            }
	});
        
        fill = new JCheckBox("Filled");   
        useG = new JCheckBox("Use Gradient");
        dash = new JCheckBox("Dashed");
        lT = new JSpinner();
        lT.setBounds(40,40,60,60);
        wT = new JSpinner();
        wT.setBounds(40,40,60,60);
        width = new JLabel("Line Width:");
        length = new JLabel("Dash Length:");
        JLabel option= new JLabel("Options:");
        
        
        top1 = new JPanel();
        top1.add(shp);
        top1.add(color1);
        top1.add(color2);
        top1.add(undo);
        top1.add(clear);
        top1.setBackground(Color.CYAN);
        top2 = new JPanel();
        top2.setBackground(Color.CYAN);
        top2.add(option);
        top2.add(fill);
        top2.add(useG);
        top2.add(dash);
        top2.add(width);
        top2.add(wT);
        top2.add(length);
        top2.add(lT);
        topPanel = new JPanel();
        topPanel.setBackground(Color.CYAN);
        topPanel.setLayout(new GridLayout(2,1));
        topPanel.add(top1);
        topPanel.add(top2);
        add(topPanel,BorderLayout.NORTH);
        add(drawpanel, BorderLayout.CENTER);
        add(mouseTracker, BorderLayout.SOUTH);
        
        // firstLine widgets

        // secondLine widgets

        // add top panel of two panels

        // add topPanel to North, drawPanel to Center, and statusLabel to South
        
        //add listeners and event handlers
    }

    // Create event handlers, if needed

    // Create a private inner class for the DrawPanel.
    private class DrawPanel extends JPanel
    {
        public DrawPanel()
        {
            MouseHandler mh = new MouseHandler();
            addMouseListener(mh);
            addMouseMotionListener(mh);
            
        }

        public void paintComponent(Graphics g)
        {
            super.paintComponent(g);
            setBackground(Color.WHITE);
            Graphics2D g2d = (Graphics2D) g;
            for(MyShapes s: Drawn){
                s.draw(g2d);
            }
        }

            //loop through and draw each shape in the shapes arraylist

        }


        private class MouseHandler extends MouseAdapter implements MouseMotionListener
        {
            MyShapes cShapes;
            
            public void mousePressed(MouseEvent event)
            {
                Paint paint = new GradientPaint(0, 0, col1, 50, 50, col1, true);
                if(useG.isSelected()){
                    paint = new GradientPaint(0, 0, col1, 50, 50, col2, true);
                }
                BasicStroke stroke;
                
                float linewidth = Float.parseFloat(String.valueOf(wT.getValue()));
                float[] dashlength = new float[1];
                dashlength[0] = Float.parseFloat(String.valueOf(lT.getValue()));
                
                if (dash.isSelected())
                    stroke = new BasicStroke(linewidth, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 10,dashlength , 0);
                else
                    stroke = new BasicStroke(linewidth, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
                
                String selected = (String) shapes.getSelectedItem();
                
                if(selected == "Line")
                    cShapes = new MyLine(event.getPoint(), event.getPoint(), paint, stroke);
                else if(selected == "Rectangle")
                    cShapes = new MyRectangle(event.getPoint(), event.getPoint(), paint, stroke, fill.isSelected());
                else
                    cShapes = new MyOval(event.getPoint(), event.getPoint(), paint, stroke, fill.isSelected());              
                Drawn.add(cShapes);
                repaint();

            }

            public void mouseReleased(MouseEvent event)
            {
            }

            @Override
            public void mouseDragged(MouseEvent event)
            {
                cShapes.setEndPoint(event.getPoint());
                repaint();
            }

            @Override
            public void mouseMoved(MouseEvent event)
            {
                String p = "(" + event.getPoint().x + "," + event.getPoint().y + ")";
                mouseTracker.setText(p);
            }
        }


}
