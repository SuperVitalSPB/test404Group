package ru.supervital.test404group.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.scichart.charting.model.AxisCollection;
import com.scichart.charting.model.ChartModifierCollection;
import com.scichart.charting.model.RenderableSeriesCollection;
import com.scichart.charting.model.dataSeries.XyDataSeries;
import com.scichart.charting.modifiers.PinchZoomModifier;
import com.scichart.charting.modifiers.ZoomExtentsModifier;
import com.scichart.charting.modifiers.ZoomPanModifier;
import com.scichart.charting.visuals.SciChartSurface;
import com.scichart.charting.visuals.axes.AxisAlignment;
import com.scichart.charting.visuals.axes.DateAxis;
import com.scichart.charting.visuals.axes.NumericAxis;
import com.scichart.charting.visuals.renderableSeries.FastLineRenderableSeries;
import com.scichart.core.framework.UpdateSuspender;
import com.scichart.core.model.DateValues;
import com.scichart.core.model.DoubleValues;
import com.scichart.core.utility.DateIntervalUtil;
import com.scichart.drawing.common.PenStyle;
import com.scichart.drawing.utility.ColorUtil;

import java.util.Date;
import java.util.Random;

import ru.supervital.test404group.R;


public class ChartFragment extends Fragment{

    SciChartSurface chartSurface;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_chart, container, false);

        chartSurface = (SciChartSurface) rootView.findViewById(R.id.chart);

        try {
            chartSurface.setRuntimeLicenseKeyFromResource(getActivity(), "app\\scr\\main\\res\raw\\liense.xml");
        }catch (Exception e){
            e.printStackTrace();
        }

        initGraph();

/*
       UpdateSuspender.using(chartSurface, new Runnable() {
            @Override
            public void run() {
                initGraph();
            }
        });
*/


        return rootView;
    }

    protected void initGraph() {
        DateAxis xAxis = new DateAxis(getActivity().getBaseContext());
        AxisCollection xAxes = chartSurface.getXAxes();
        xAxes.add(xAxis);


        NumericAxis yAxis = new NumericAxis(getActivity().getBaseContext());
        AxisCollection yAxes = chartSurface.getYAxes();
        yAxes.add(yAxis);
        yAxis.setAxisAlignment(AxisAlignment.Left);


        FastLineRenderableSeries lineSeries = new FastLineRenderableSeries();
        RenderableSeriesCollection renderableSeries = chartSurface.getRenderableSeries();
        renderableSeries.add(lineSeries);


        // PenStyle PenStylepenStyle = new PenStyle(ColorUtil.Green, true, 2f);

        PenStyle penStyle = new PenStyle(ColorUtil.Blue, true, 2);
        lineSeries.setStrokeStyle(penStyle);

        XyDataSeries dataSeries = new XyDataSeries(Date.class, Double.class);

        int size = 100;
        DateValues xValues = new DateValues(size);
        DoubleValues yValues = new DoubleValues(size);

        Random random = new Random();
        for (double i = 0; i < size; ++i) {
            long xValue = new Date().getTime() + DateIntervalUtil.fromDays(i);
            xValues.add(new Date(xValue));
            yValues.add(random.nextDouble()*100);
        }

        dataSeries.append(xValues, yValues);

        chartSurface.zoomExtents();
        PinchZoomModifier zoomModifier = new PinchZoomModifier();
        ZoomPanModifier panModifier = new ZoomPanModifier();

        ChartModifierCollection chartModifiers = chartSurface.getChartModifiers();// getChartModifiersCollection();
        chartModifiers.add(zoomModifier);
        chartModifiers.add(panModifier);

        ZoomExtentsModifier zoomExtentsModifier = new ZoomExtentsModifier();
        chartModifiers.add(zoomExtentsModifier);


    }
}


