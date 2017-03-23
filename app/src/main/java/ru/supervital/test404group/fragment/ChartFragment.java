package ru.supervital.test404group.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.scichart.charting.model.dataSeries.IXyDataSeries;
import com.scichart.charting.visuals.SciChartSurface;
import com.scichart.charting.visuals.axes.IAxis;
import com.scichart.charting.visuals.renderableSeries.IRenderableSeries;
import com.scichart.core.framework.UpdateSuspender;
import com.scichart.data.model.DoubleRange;

import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;
import ru.supervital.test404group.R;


public class ChartFragment extends Fragment{

    SciChartSurface surface;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_chart, container, false);
        surface = (SciChartSurface) rootView.findViewById(R.id.chart);

        return rootView;
    }

    protected void initExample() {

/*
        final IAxis xAxis = sciChartBuilder.newDateAxis()
                .withGrowBy(new DoubleRange(0.1d, 0.1d))
                .build();

        final IAxis yAxis = sciChartBuilder.newNumericAxis()
                .withGrowBy(new DoubleRange(0.1d, 0.1d))
                .build();

        final IXyDataSeries<Date, Double> dataSeries = sciChartBuilder.newXyDataSeries(Date.class, Double.class).build();
        final IRenderableSeries renderableSeries = sciChartBuilder.newMountainSeries()
                .withDataSeries(dataSeries)
                .withIsDigitalLine(true)
                .withStrokeStyle(0xAAFFC9A8)
                .withAreaFillLinearGradientColors(0xAAFF8D42,0x88090E11)
                .build();

        final PriceSeries priceData = DataManager.getInstance().getPriceDataIndu(getActivity());
        dataSeries.append(priceData.getDateData(), priceData.getCloseData());*/

        UpdateSuspender.using(surface, new Runnable() {
            @Override
            public void run() {
/*
                Collections.addAll(surface.getXAxes(), xAxis);
                Collections.addAll(surface.getYAxes(), yAxis);
                Collections.addAll(surface.getRenderableSeries(), renderableSeries);
                Collections.addAll(surface.getChartModifiers(), sciChartBuilder.newModifierGroupWithDefaultModifiers().build());
*/
            }
        });
    }
}