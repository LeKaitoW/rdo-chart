import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.Range;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.experimental.chart.swt.ChartComposite;

public class SWTTenPoints {
	private static JFreeChart createChart(XYDataset dataset) {

		final JFreeChart chart = ChartFactory.createXYStepChart(
				"10 points chart", 
				"Time", 
				"Value", 
				dataset,
				PlotOrientation.VERTICAL, 
				true, 
				true, 
				false);

		XYPlot plot = (XYPlot) chart.getPlot();

		final NumberAxis rangeAxis = new NumberAxis();
		rangeAxis.setRangeWithMargins(10, 20);
		plot.setRangeAxis(rangeAxis);

		final NumberAxis domainAxis = new NumberAxis();
		domainAxis.setRangeWithMargins(dataset.getXValue(0, 0),
				dataset.getXValue(0, 9));
		plot.setDomainAxis(domainAxis);

		return chart;
	}

	private static XYDataset createDataset() {

		XYSeriesCollection dataset = new XYSeriesCollection();
		XYSeries series1 = new XYSeries("10 points");
		dataset.addSeries(series1);

		Double x = 0.0;
		final int numberOfPoints = 10;
		for (int pointNumber = 0; pointNumber < numberOfPoints; pointNumber++) {
			Integer y = (int) (numberOfPoints + 10 * Math.random());
			series1.add(x, y);
			x = x + 1.0;
		}
		return dataset;
	}

	public static void main(String[] args) {
		final JFreeChart chart = createChart(createDataset());
		final Display display = new Display();
		Shell shell = new Shell(display);
		shell.setSize(600, 300);
		shell.setLayout(new FillLayout());
		shell.setText("Ten points demo for jfreechart running with SWT");
		ChartComposite frame = new ChartComposite(shell, SWT.NONE, chart, true);
		frame.setDisplayToolTips(true);
		frame.setHorizontalAxisTrace(false);
		frame.setVerticalAxisTrace(false);
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
	}
}