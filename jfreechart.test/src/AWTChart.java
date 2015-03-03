import java.awt.Color; 
import java.awt.BasicStroke; 
import java.awt.Graphics;

import org.apache.commons.lang3.time.StopWatch;
import org.jfree.chart.ChartPanel; 
import org.jfree.chart.JFreeChart; 
import org.jfree.data.xy.XYDataset; 
import org.jfree.data.xy.XYSeries; 
import org.jfree.ui.ApplicationFrame; 
import org.jfree.ui.RefineryUtilities; 
import org.jfree.chart.plot.XYPlot; 
import org.jfree.chart.ChartFactory; 
import org.jfree.chart.plot.PlotOrientation; 
import org.jfree.data.xy.XYSeriesCollection; 
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;

public class AWTChart extends ApplicationFrame 
{
   public AWTChart( String applicationTitle, String chartTitle )
   {
      super(applicationTitle);
      JFreeChart xylineChart = ChartFactory.createXYLineChart(
         "AWTChart" ,
         "Category" ,
         "Score" ,
         createDataset() ,
         PlotOrientation.VERTICAL ,
         true , true , false);
         
      	ChartPanel chartPanel = new ChartPanel( xylineChart );
//		chartPanel.setPreferredSize( new java.awt.Dimension( 600 , 300 ) );
      	final XYPlot plot = xylineChart.getXYPlot( );
//		final pixelRender renderer = new pixelRender();
      	final MyRender renderer = new MyRender();
      	plot.setRenderer(0, renderer);
      	setContentPane( chartPanel ); 
   }
   
   private XYDataset createDataset( )
   {
	   	XYSeriesCollection dataset1 = new XYSeriesCollection();
		XYSeries series1 = new XYSeries("First");
		Double next = 0.0;
        int num = 1000000;
        int maxX = 496;
        int minX = 497;
		
        for (int i = 0; i < num; i++)
        {
        	Integer value1 = (int) (100 + Math.random() * Math.pow(i,1./2));
    		if (i == maxX)
    			value1 = 980;
    		if (i == minX)
    			value1 = 20;
        	series1.add(next, value1);
        	next = next + 0.01;
        }
		dataset1.addSeries(series1);
		return dataset1;
   }

   public static void main( String[ ] args ) 
   {
	  StopWatch sw = new StopWatch();
	  sw.start();
	  System.out.println("start");
      AWTChart chart = new AWTChart("Time series demo for jfreechart running with AWT", "AWTChart"){
    	  @Override 
    	  public void paint(Graphics g){
    		  super.paint(g);
    		  System.out.println(sw.getTime());
    	  }
      };
      chart.pack( );
      
      RefineryUtilities.centerFrameOnScreen( chart );          
      chart.setVisible( true ); 
   }
}