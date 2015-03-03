import java.awt.Frame;
import java.util.Set;

import org.apache.commons.lang3.time.StopWatch;
import org.eclipse.swt.SWT;
import org.eclipse.swt.awt.SWT_AWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.SamplingXYLineRenderer;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYStepRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.experimental.chart.swt.ChartComposite;

public class BridgeChart
{

    private static JFreeChart createChart(XYDataset dataset) {

		final JFreeChart chart = ChartFactory.createXYStepChart(
				"Chart Test", // chart
							// title
				"Time", // domain axis label
				"Value", // range axis label
				dataset, // data
				PlotOrientation.VERTICAL, // orientation
				true, // include legend
				true, // tooltips
				false // urls
		);

		final XYPlot plot = (XYPlot) chart.getPlot();
		// customise the range axis...

		final NumberAxis firstRangeAxis = new NumberAxis("First");
		firstRangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
		firstRangeAxis.setAutoRangeIncludesZero(true);

		final NumberAxis DomainAxis = new NumberAxis();
		DomainAxis.setStandardTickUnits(NumberAxis.createStandardTickUnits());
		DomainAxis.setAutoRangeIncludesZero(true);
		plot.setDomainAxis(DomainAxis);
		
		final MyRender renderer = new MyRender();
//		final XYStepRenderer renderer = new XYStepRenderer();
//		final SamplingXYLineRenderer renderer = new SamplingXYLineRenderer();
		plot.setRenderer(0, renderer);
		return chart;
    }
   
    public static void main(String[] args) {
    	StopWatch sw = new StopWatch();
        sw.start();
        System.out.println("!!start!!!");
    	XYSeriesCollection dataset1 = new XYSeriesCollection();
		XYSeries series1 = new XYSeries("First");
		dataset1.addSeries(series1);
		
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
		
        
        final JFreeChart chart = createChart(dataset1);
       
        final Display display = new Display();
        Shell shell = new Shell(display);
        shell.setSize(600, 300);
        shell.setLayout(new FillLayout());
        shell.setText("Time series demo for jfreechart running with SWT_AWT Bridge");
        Composite frameComposite = new Composite(shell, SWT.EMBEDDED);
        frameComposite.setLayout(new FillLayout());
        Frame frame = SWT_AWT.new_Frame(frameComposite);
        ChartPanel panel = new ChartPanel(chart);
        frame.add(panel);
        shell.open();
        
        
        while (!shell.isDisposed()) {
//        	System.out.println(sw.getTime()); 
            if (!display.readAndDispatch()){
            	//try{
            		//sw.stop();
                    System.out.println(sw.getTime()); 
                //	}
                //	catch(Exception ex){}
            	display.sleep();
            }
        }
        
    }
}

