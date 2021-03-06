package gui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

//https://www.developer.com/java/data/working-with-javafx-chart-apis.html
//credits to: Manoj Debnath

import javafx.scene.chart.LineChart;
import javafx.scene.chart.LineChart.SortingPolicy;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
import smrt2.SmartTableModel;



public class GraphBuilder{
	
	private ObservableList<XYChart.Series<Number, Number>> data;
	private String title;
	private String xName;
	private String yName;
	
	public GraphBuilder(SmartTableModel tableModel, int[] xy, String title) {
		Double[] xData = tableModel.getColumnAt(xy[0]);
		Double[] yData = tableModel.getColumnAt(xy[1]);
		data = FXCollections.observableArrayList();
		Series<Number, Number> seriesData = ArraysToSeries(xData, yData);
		seriesData.setName(title);
		this.data.add(seriesData);
		this.title = title;
		xName = tableModel.getColumnName(xy[0]);
		yName = tableModel.getColumnName(xy[1]);
		 
		
	}
	
	public GraphBuilder(SmartTableModel tableModel, int[] toPlot) {
		data = FXCollections.observableArrayList();
		fillToObservableList(tableModel, toPlot);
		this.title = "";
		for (int i : toPlot) {
			title += " " + tableModel.getColumnName(i);
		}
		xName = "Time";
		yName = title;
	}

	
	private void fillToObservableList(SmartTableModel tm, int[] toPlot) {
		for (int xIndex : toPlot) {
			Series<Number, Number> series = ArraysToSeries(tm.getColumnAt(0), tm.getColumnAt(xIndex));
			series.setName(tm.getColumnName(xIndex));
			data.add(series);
		}
	}

	public static XYChart.Series<Number, Number> ArraysToSeries(Double[] xData, Double[] yData){
		XYChart.Series<Number, Number> chartdata = new XYChart.Series<Number, Number>();
		for (int i=0; i < yData.length; i++) {
			chartdata.getData().add(new XYChart.Data<Number, Number>(xData[i], yData[i]));
		}
		return chartdata;
	}


	public LineChart<Number, Number> start() {
	    NumberAxis xAxis = new NumberAxis();
	    NumberAxis yAxis = new NumberAxis();

	    final LineChart<Number, Number> lineChart = new LineChart<Number, Number>(xAxis, yAxis);
	   
		lineChart.setAxisSortingPolicy(SortingPolicy.NONE);
	    
	    lineChart.setData(data);
		lineChart.setTitle(title);
	    xAxis.setLabel(xName);
	    yAxis.setLabel(yName);
	    return lineChart;
	
	}
}
