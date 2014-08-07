package de.medavis.vaadin.addon.baiduchart.demo;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.data.Property;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

import de.medavis.vaadin.addon.chart.BaiduChart;


@Theme("demo")
@Title("BaiduChart Add-on Demo")
@SuppressWarnings("serial")
public class DemoUI extends UI {

    @WebServlet(value = "/*", asyncSupported = true)
    @VaadinServletConfiguration(productionMode = false, ui = DemoUI.class)
    public static class Servlet extends VaadinServlet {
    }

    // Show it in the middle of the screen
    private final VerticalLayout vlayout = new VerticalLayout();
    private String jsonData;
    
    @Override
    protected void init(final VaadinRequest request) {
        try {
            // Set the default locale of the UI
            UI.getCurrent().setLocale(new Locale("en"));
            // Set the page title (window or tab caption)
            Page.getCurrent().setTitle("BaiduChart");
            // Set the root panel
            //vlayout.setSizeFull();
            vlayout.setStyleName("demoContentLayout");
            // Enable layout margins. Affects all four sides of the layout
            vlayout.setMargin(true); 
            vlayout.setSpacing(true);
            
            final ComboBox combox = new ComboBox("Please select workflow:", Arrays.asList(WorkflowDummy.values()));
            combox.setImmediate(true);
            combox.addValueChangeListener(new Property.ValueChangeListener() {
                @Override
                public void valueChange(Property.ValueChangeEvent event) {
                    final WorkflowDummy workflow = (WorkflowDummy)event.getProperty().getValue();
                    Notification.show(workflow.getName());
                    vlayout.removeComponent(baiduChart);
                    if(workflow.getName().equals("ris_his")){
                        showChart("ris_his");
                    }else if(workflow.getName().equals("pacs_g4m")){
                        showChart("pacs_g4m");
                    }else if(workflow.getName().equals("relabit_pabs_refer")){
                        showChart("relabit_pabs_refer");
                    }
                }
            });
            combox.setValue("ris_his");
            vlayout.addComponent(combox);
            
            showChart("ris_his");
            
            setContent(vlayout);
        } catch(Exception ex) {
            ex.printStackTrace(System.err);
        }
    }
    private BaiduChart baiduChart;
    private void showChart(String workflowName){
        // data-1
        List<String> list = new ArrayList<>();
        list.add("800");list.add("200");list.add("1000");list.add("0");
        list.add("0");list.add("0");list.add("100");list.add("900");
        list.add("1000");list.add("0");list.add("0");list.add("800");
        // data-2
        List<String> list1 = new ArrayList<>();
        list1.add("800");list1.add("200");list1.add("1000");list1.add("0");
        list1.add("0");list1.add("0");list1.add("100");list1.add("900");
        list1.add("1000");list1.add("0");list1.add("0");list1.add("800");
        
        Map<String, List<String>> map = new HashMap<>();
        map.put("Received Message",list);
        map.put("Sent Message", list1);
        // legenda
        String legend[] = {"Received Message", "Sent Message"};
        String xAxis[] = {"Jan.","Feb.","Mar.","Apr.","May.","Jun.","Jul.","Aug.","Sep.","Oct.","Nov.","Dec."};
        // set json data
        setJsonOption(workflowName, legend, xAxis, map);
        baiduChart = new BaiduChart();
        baiduChart.setSizeFull();
        baiduChart.setValue(jsonData);
       
        vlayout.addComponent(baiduChart);
    }
    
    private void setJsonOption(String title, String[] legendData, String[] xAxisData, Map<String, List<String>> seriesMap){
        // config info for chart
        StringBuilder series = new StringBuilder();
        
        // fill the json data
        // 1. set series data
        series.append("series:[");
        for(String str : seriesMap.keySet()){
            series.append("{name:'" + str + "',type:'bar',data:" + seriesMap.get(str).toString() + ",markPoint:{data:[{type:'max',name:'Max value'},{type:'min',name:'Min value'}]},markLine:{data:[{type:'average',name:'Average value'}]}},");
        }
        series = new StringBuilder(series.substring(0, series.length()-1));
        series.append("]");
        
        // 2. set the json string
        jsonData = "{"
                + "title:{text:'" + title + "'},"
                + "tooltip:{trigger:'axis'},"
                + "legend:{data:[" + toStringFromArray(legendData) + "]},"
                + "toolbox:{"
                    + "show:true,"
                    + "feature:{"
                        + "mark:{show:true, title:'Assistant line mark'},"
                        + "dataView:{show:true,title:'Data view',readOnly:false},"
                        + "magicType:{show:true,title:{line:'line',bar:'bar',stack:'stack',tiled:'tiled'},type:['line','bar']},"
                        + "restore:{show:true,title:'restore'},"
                        + "saveAsImage:{show:true,title:'saveAsImage'}}},"
                + "calculable:true,"
                + "xAxis:[{type:'category',data:[" + toStringFromArray(xAxisData) + "]}],"
                + "yAxis:[{type:'value'}]," + series.toString() + "}";
    }
    
    private String toStringFromArray(String[] array){
        if(array == null || array.length == 0)
            return "";
        StringBuilder str = new StringBuilder();
        for(int i=0; i<array.length; i++){
            str.append("'" + array[i] + "',");
        }
        return str.substring(0, str.length() - 1);
    }
}
