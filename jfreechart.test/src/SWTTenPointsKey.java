import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.experimental.chart.swt.ChartComposite;

public class SWTTenPointsKey {
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

		final XYPlot plot = (XYPlot) chart.getPlot();

		final NumberAxis rangeAxis = new NumberAxis();
		rangeAxis.setAutoRangeIncludesZero(true);
		plot.setRangeAxis(rangeAxis);

		final NumberAxis domainAxis = new NumberAxis();
		domainAxis.setAutoRangeIncludesZero(true);
		plot.setDomainAxis(domainAxis);

		return chart;
	}

	private static XYDataset createDataset() {

		final XYSeriesCollection dataset = new XYSeriesCollection();
		final XYSeries series1 = new XYSeries("10 points");
		dataset.addSeries(series1);

		double x = 0.0;
		final int numberOfPoints = 10;
		for (int pointNumber = 0; pointNumber < numberOfPoints; pointNumber++) {
			Integer y = (int) (pointNumber + 10 * Math.random());
			series1.add(x, y);
			x = x + 1.0;
		}

		return dataset;
	}

	public static void main(String[] args) {
		final XYDataset dataset = createDataset();
		final JFreeChart chart = createChart(dataset);
		final Display display = new Display();
		final Shell shell = new Shell(display);
		shell.setSize(600, 300);
		shell.setLayout(new FillLayout());
		shell.setText("Ten points demo for jfreechart running with SWT");
		final ChartComposite frame = new MyChartComposite(shell, SWT.NONE,
				chart, true);
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

class MyChartComposite extends ChartComposite implements KeyListener {

	public MyChartComposite(Composite comp, int style, JFreeChart chart,
			boolean useBuffer) {
		super(comp, style, chart, useBuffer);
		addSWTListener(this);
	}

	@Override
	public void keyPressed(org.eclipse.swt.events.KeyEvent e) {
		if (e.keyCode == ' ') {
			final XYSeriesCollection dataset = (XYSeriesCollection) this
					.getChart().getXYPlot().getDataset();
			final XYSeries series2 = dataset.getSeries(0);

			final int pointNumber = dataset.getItemCount(0);
			final double lastX = dataset.getXValue(0, pointNumber - 1);
			final double x = lastX + 1.0;
			final int y = (int) (pointNumber + 10 * Math.random());
			series2.add(x, y);
		}
	}

	@Override
	public void keyReleased(org.eclipse.swt.events.KeyEvent e) {
	}
}
