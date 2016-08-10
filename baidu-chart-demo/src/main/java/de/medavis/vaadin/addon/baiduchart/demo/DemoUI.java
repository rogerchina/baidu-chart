package de.medavis.vaadin.addon.baiduchart.demo;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
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
import de.medavis.vaadin.addon.chart.BaiduChartTheme;
import de.medavis.vaadin.addon.chart.Point;


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
    // Chart component
    private BaiduChart baiduChart;
    
    @Override
    protected void init(final VaadinRequest request) {
        try {
            // Set the page title (window or tab caption)
            Page.getCurrent().setTitle("BaiduChart");
            // Set the root panel
            vlayout.setStyleName("demoContentLayout");
            // Enable layout margins. Affects all four sides of the layout
            vlayout.setMargin(true); 
            vlayout.setSpacing(true);
            
            final ComboBox comboxWorkflow = new ComboBox("Please select workflow:", Arrays.asList(WorkflowDummy.values()));
            comboxWorkflow.setTextInputAllowed(false);
            comboxWorkflow.setNewItemsAllowed(false);
            comboxWorkflow.setNullSelectionAllowed(false);
            comboxWorkflow.setRequired(true);
            comboxWorkflow.setRequiredError("should choose one item!");
            comboxWorkflow.setImmediate(true);
            comboxWorkflow.addValueChangeListener(new Property.ValueChangeListener() {
                @Override
                public void valueChange(Property.ValueChangeEvent event) {
                    try {
                        final WorkflowDummy workflow = (WorkflowDummy)event.getProperty().getValue();
                        Notification.show(workflow.getName());
                        vlayout.removeComponent(baiduChart);
                        if(workflow != null){
                            if(workflow.getName().equals("ris_his")){
                                showChart("ris_his");
                            }else if(workflow.getName().equals("pacs_g4m")){
                                showChart("pacs_g4m");
                            }else if(workflow.getName().equals("relabit_pabs_refer")){
                                showChart("relabit_pabs_refer");
                            }
                        }
                    } catch(Exception e) {
                        //do nothing
                    }
                    
                }
            });
            comboxWorkflow.setValue("ris_his");
            vlayout.addComponent(comboxWorkflow);
            
            final ComboBox comboxTheme = new ComboBox("Please select theme:", Arrays.asList(BaiduChartTheme.values()));
            comboxTheme.setImmediate(true);
            comboxTheme.addValueChangeListener(new Property.ValueChangeListener() {
                @Override
                public void valueChange(Property.ValueChangeEvent event) {
                    final BaiduChartTheme theme = (BaiduChartTheme)event.getProperty().getValue();
                    Notification.show(theme.getName());
                    //TODO
                    baiduChart.setTheme(theme);
                }
            });
            vlayout.addComponent(comboxTheme);
            
            // display by default
            showChart("ris_his");
            //showChart("ris_his");
            
            setContent(vlayout);
        } catch(Exception ex) {
            ex.printStackTrace(System.err);
        }
    }
    
    private void showChart(String titleName){
        Map<String, List<Point>> tempMap = new LinkedHashMap<>();
        
        // if dataMap is empty, set default value
        List<Point> pointList = new ArrayList<>();
        Point point = null;
        for(int i=0; i<10; i++){
            point = new Point();
            point.setX(i+"");
            point.setY(4);
            pointList.add(point);
        }
        tempMap.put("hello1", pointList);
        
        List<Point> point1List = new ArrayList<>();
        Point point1 = null;
        for(int i=0; i<10; i++){
            point1 = new Point();
            point1.setX(i+"");
            point1.setY(9);
            point1List.add(point);
        }
        tempMap.put("hello2", point1List);
        
        BaiduChart baiduChart = new BaiduChart();
        baiduChart.setSizeFull();
        baiduChart.setValue(baiduChart.setJsonOption(titleName, tempMap));
   
        vlayout.addComponent(baiduChart);
    }
    
}
