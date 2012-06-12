package simulation;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import javax.swing.JFrame;

import org.jfree.data.xy.XYSeries;

import sim.display.Controller;
import sim.util.media.chart.TimeSeriesChartGenerator;

public class StatsManager implements PropertyChangeListener {
	ArrayList<String> types;
	HashMap<String, Double> populations;
	HashMap<String, Integer> bornThisStep;
	HashMap<String, Integer> diedThisStep;
	HashMap<String, Double> weightEatenThisStep;
	HashMap<String, Integer> weightThisStep;
	HashMap<String, Double> ageThisStep;

	HashMap<String, XYSeries> populationsSeries;
	HashMap<String, XYSeries> bornThisStepSeries;
	HashMap<String, XYSeries> diedThisStepSeries;
	HashMap<String, XYSeries> weightEatenThisStepSeries;
	HashMap<String, XYSeries> weightThisStepSeries;
	HashMap<String, XYSeries> ageThisStepSeries;

	private TimeSeriesChartGenerator chartPop = new sim.util.media.chart.TimeSeriesChartGenerator();
	private TimeSeriesChartGenerator chartBorn = new sim.util.media.chart.TimeSeriesChartGenerator();
	private TimeSeriesChartGenerator chartDied = new sim.util.media.chart.TimeSeriesChartGenerator();
	private TimeSeriesChartGenerator chartAte = new sim.util.media.chart.TimeSeriesChartGenerator();
	private TimeSeriesChartGenerator chartWeight = new sim.util.media.chart.TimeSeriesChartGenerator();
	private TimeSeriesChartGenerator chartAge = new sim.util.media.chart.TimeSeriesChartGenerator();

	@SuppressWarnings("deprecation")
	public StatsManager() {
		types = new ArrayList<String>();
		populations = new HashMap<String, Double>();
		bornThisStep = new HashMap<String, Integer>();
		diedThisStep = new HashMap<String, Integer>();
		weightEatenThisStep = new HashMap<String, Double>();
		weightThisStep = new HashMap<String, Integer>();
		ageThisStep = new HashMap<String, Double>();

		populationsSeries = new HashMap<String, XYSeries>();
		bornThisStepSeries = new HashMap<String, XYSeries>();
		diedThisStepSeries = new HashMap<String, XYSeries>();
		weightEatenThisStepSeries = new HashMap<String, XYSeries>();
		weightThisStepSeries = new HashMap<String, XYSeries>();
		ageThisStepSeries = new HashMap<String, XYSeries>();

		chartPop.setTitle("Species populations");
		chartPop.setRangeAxisLabel("Individuals");
		chartPop.setDomainAxisLabel("Days");

		chartBorn.setTitle("Species birthrates");
		chartBorn.setRangeAxisLabel("Individuals/Individuals");
		chartBorn.setDomainAxisLabel("Days");

		chartDied.setTitle("Species deathrates");
		chartDied.setRangeAxisLabel("Individuals/Individuals");
		chartDied.setDomainAxisLabel("Days");

		chartAte.setTitle("Species feeding rates");
		chartAte.setRangeAxisLabel("Kg/Individuals");
		chartAte.setDomainAxisLabel("Days");

		chartAge.setTitle("Species age average");
		chartAge.setRangeAxisLabel("days/Individuals");
		chartAge.setDomainAxisLabel("Days");

		chartWeight.setTitle("Species weight average");
		chartWeight.setRangeAxisLabel("Kg/Individuals");
		chartWeight.setDomainAxisLabel("Days");

	}

	public void addStatForSpecies(String speciesType) {
		// keep type
		types.add(speciesType);

		// keep datas
		populations.put(speciesType, (double) 0);
		bornThisStep.put(speciesType, 0);
		diedThisStep.put(speciesType, 0);
		weightEatenThisStep.put(speciesType, (double) 0f);
		ageThisStep.put(speciesType, (double) 0f);
		weightThisStep.put(speciesType, 0);

		// kep series
		populationsSeries.put(speciesType, new XYSeries(
				(speciesType + " population"), false));
		bornThisStepSeries.put(speciesType, new XYSeries(
				(speciesType + " birthrate"), false));
		diedThisStepSeries.put(speciesType, new XYSeries(
				(speciesType + " deathrate"), false));
		weightEatenThisStepSeries.put(speciesType, new XYSeries(
				(speciesType + " feeding rate"), false));
		ageThisStepSeries.put(speciesType, new XYSeries(
				(speciesType + " age average"), false));
		weightThisStepSeries.put(speciesType, new XYSeries(
				(speciesType + " weight average"), false));

		// setCharts
		chartPop.addSeries(populationsSeries.get(speciesType), null);
		chartBorn.addSeries(bornThisStepSeries.get(speciesType), null);
		chartDied.addSeries(diedThisStepSeries.get(speciesType), null);
		chartAte.addSeries(weightEatenThisStepSeries.get(speciesType), null);
		chartAge.addSeries(ageThisStepSeries.get(speciesType), null);
		chartWeight.addSeries(weightThisStepSeries.get(speciesType), null);

	}

	public void updateCharts(double x) {
		Iterator<String> it = types.iterator();
		XYSeries currentSerie;
		while (it.hasNext()) {
			String sp = it.next();

			// population
			double currentPop = populations.get(sp);
			currentSerie = populationsSeries.get(sp);
			currentSerie.add(x, populations.get(sp) / 1f, true);
			populationsSeries.put(sp, currentSerie);

			// born
			currentSerie = bornThisStepSeries.get(sp);
			float newVal;
			if (currentPop != 0) {
				newVal = bornThisStep.get(sp)/* /((float)currentPop) */;
				System.out.println("new born " + newVal);
			} else {
				newVal = 0;
			}

			currentSerie.add(x, newVal, true);
			bornThisStepSeries.put(sp, currentSerie);
			bornThisStep.put(sp, 0);

			// died
			currentSerie = diedThisStepSeries.get(sp);
			if (currentPop != 0) {
				newVal = diedThisStep.get(sp)/* /((float)currentPop) */;
				System.out.println("new died " + newVal);
			} else {
				newVal = 0;
			}

			currentSerie.add(x, newVal, true);
			diedThisStepSeries.put(sp, currentSerie);
			diedThisStep.put(sp, 0);

			// ate
			currentSerie = weightEatenThisStepSeries.get(sp);
			if (currentPop != 0) {
				newVal = (float) (weightEatenThisStep.get(sp) / ((double) currentPop));
				System.out.println("new ate " + newVal);
			} else {
				newVal = 0;
			}

			currentSerie.add(x, newVal, true);
			weightEatenThisStepSeries.put(sp, currentSerie);
			weightEatenThisStep.put(sp, (double) 0f);

			// age
			currentSerie = ageThisStepSeries.get(sp);
			if (currentPop != 0) {
				newVal = (float) ((ageThisStep.get(sp) / 4f / 365f) / ((float) currentPop));
				System.out.println("new age " + newVal);
			} else {
				newVal = 0;
			}

			currentSerie.add(x, newVal, true);
			ageThisStepSeries.put(sp, currentSerie);
			ageThisStep.put(sp, (double) 0);

			// weight
			currentSerie = weightThisStepSeries.get(sp);
			if (currentPop != 0) {
				newVal = (float) (weightThisStep.get(sp) / ((float) currentPop));
				System.out.println("new weight " + newVal);
			} else {
				newVal = 0;
			}

			currentSerie.add(x, newVal, true);
			weightThisStepSeries.put(sp, currentSerie);
			weightThisStep.put(sp, (int) 0);

		}
	}

	@Override
	public void propertyChange(PropertyChangeEvent arg0) {
		if (arg0.getPropertyName().equals("was_born")) {
			// System.out.println("bornthisStep is :"+bornThisStep);
			// System.out.println("type is : "+arg0.getOldValue());

			bornThisStep.put((String) arg0.getOldValue(),
					bornThisStep.get(arg0.getOldValue()) + 1);
			populations.put((String) arg0.getOldValue(),
					populations.get(arg0.getOldValue()) + 1);
		} else if (arg0.getPropertyName().equals("died")) {
			diedThisStep.put((String) arg0.getOldValue(),
					diedThisStep.get(arg0.getOldValue()) + 1);
			populations.put((String) arg0.getOldValue(),
					populations.get(arg0.getOldValue()) - 1);
		} else if (arg0.getPropertyName().equals("ate")) {
			// System.out.println("ate "+((Float)arg0.getNewValue())+" received !");
			weightEatenThisStep.put((String) arg0.getOldValue(),
					weightEatenThisStep.get(arg0.getOldValue())
							+ ((Double) arg0.getNewValue()));
		} else if (arg0.getPropertyName().equals("age")) {
			ageThisStep.put(
					(String) arg0.getOldValue(),
					ageThisStep.get(arg0.getOldValue())
							+ ((Double) arg0.getNewValue()));
		} else if (arg0.getPropertyName().equals("weight")) {
			weightThisStep
					.put((String) arg0.getOldValue(), (int) (weightThisStep
							.get(arg0.getOldValue()) + ((Double) arg0
							.getNewValue())));
		}

	}

	@SuppressWarnings("deprecation")
	public void createFrames(Controller controller) {

		// System.out.println("createFrames called !");

		JFrame currentFrame = chartPop.createFrame();
		currentFrame.show();
		currentFrame.pack();
		controller.registerFrame(currentFrame);

		currentFrame = chartBorn.createFrame();
		currentFrame.show();
		currentFrame.pack();
		controller.registerFrame(currentFrame);

		currentFrame = chartDied.createFrame();
		currentFrame.show();
		currentFrame.pack();
		controller.registerFrame(currentFrame);

		currentFrame = chartAte.createFrame();
		currentFrame.show();
		currentFrame.pack();
		controller.registerFrame(currentFrame);

		currentFrame = chartAge.createFrame();
		currentFrame.show();
		currentFrame.pack();
		controller.registerFrame(currentFrame);

		currentFrame = chartWeight.createFrame();
		currentFrame.show();
		currentFrame.pack();
		controller.registerFrame(currentFrame);

	}

	public Double getPopulationOfSpecies(String type) {
		return populations.get(type);
	}

}

/*
 * series = new XYSeries("Ipop",false); chart.addSeries(series, null);
 */

// TODO population
// TODO natalité
// TODO mortalité
// TODO quantité mangé