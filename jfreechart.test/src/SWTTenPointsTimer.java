import java.util.Timer;
import java.util.TimerTask;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
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

public class SWTTenPointsTimer {
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

	private static XYDataset createDataset(Display display) {

		final XYSeriesCollection dataset = new XYSeriesCollection();
		final XYSeries series = new XYSeries("10 points");
		dataset.addSeries(series);

		double x[] = { 0 };
		final int numberOfPoints = 10;
		for (int pointNumber = 0; pointNumber < numberOfPoints; pointNumber++) {
			Integer y = (int) (pointNumber + 10 * Math.random());
			series.add(x[0], y);
			x[0] = x[0] + 1.0;
		}

		int[] timerPointNumber = { 0 };
		double[] xRealtime = x;

		new Timer().scheduleAtFixedRate(new TimerTask() {

			@Override
			public void run() {
				if (timerPointNumber[0]++ <= 100) {
					display.syncExec(() -> {
						Integer yTimer = (int) (timerPointNumber[0] + 10 * Math
								.random());
						series.add(xRealtime[0], yTimer);
						xRealtime[0] = xRealtime[0] + 1.0;
					});
				}
			}
		}, 3000, 100);
		return dataset;
	}

	public static void main(String[] args) {
		final Display display = new Display();
		final JFreeChart chart = createChart(createDataset(display));
		final Shell shell = new Shell(display);
		shell.setSize(600, 300);
		shell.setLayout(new FillLayout());
		shell.setText("Ten points demo for jfreechart running with SWT");
		final ChartComposite frame = new ChartComposite(shell, SWT.NONE, chart,
				true);
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