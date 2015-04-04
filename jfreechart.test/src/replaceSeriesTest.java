package seriesTest;

import java.util.ArrayList;
import java.util.List;

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

public class replaceSeriesTest {
	public static List<Integer> points = new ArrayList<Integer>();

	private static JFreeChart createChart(XYDataset dataset) {

		final JFreeChart chart = ChartFactory.createXYStepChart(
				"10 points chart", "Time", "Value", dataset,
				PlotOrientation.VERTICAL, true, true, false);

		final XYPlot plot = (XYPlot) chart.getPlot();

		final NumberAxis rangeAxis = new NumberAxis();
		plot.setRangeAxis(rangeAxis);

		final NumberAxis domainAxis = new NumberAxis();
		plot.setDomainAxis(domainAxis);

		return chart;
	}

	private static XYDataset createDataset() {

		final XYSeriesCollection dataset = new XYSeriesCollection();
		final XYSeries series1 = new XYSeries("series test");
		dataset.addSeries(series1);
		int n = 5;
		for (int i = 0; i < n; i++) {
			points.add(n - i);
		}
		for (int i = 0; i < n + 1; i++) {
			points.add(i);
		}

		for (int pointNumber = 0; pointNumber < points.size(); pointNumber++) {
			series1.add(pointNumber, points.get(pointNumber));
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
		shell.setText("Series Test");
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

			final int n = replaceSeriesTest.points
					.get(replaceSeriesTest.points.size() - 1) + 1;
			replaceSeriesTest.points.add(n);
			replaceSeriesTest.points.add(0, n);

			final XYSeries series = new XYSeries("array");
			for (int pointNumber = 0; pointNumber < replaceSeriesTest.points.size(); pointNumber++) {
				series.add(pointNumber, replaceSeriesTest.points.get(pointNumber));
			}

			final XYSeriesCollection dataset = (XYSeriesCollection) this
					.getChart().getXYPlot().getDataset();
			dataset.removeAllSeries();
			dataset.addSeries(series);
		}
	}

	@Override
	public void keyReleased(org.eclipse.swt.events.KeyEvent e) {
	}
}
