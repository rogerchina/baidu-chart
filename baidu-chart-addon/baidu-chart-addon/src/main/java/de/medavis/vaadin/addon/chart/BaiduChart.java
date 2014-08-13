package de.medavis.vaadin.addon.chart;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;

import com.vaadin.annotations.JavaScript;
import com.vaadin.annotations.StyleSheet;
import com.vaadin.ui.AbstractJavaScriptComponent;
import com.vaadin.ui.JavaScriptFunction;

@JavaScript({"vaadin://baiduchart/js/echarts-plain.js", 
    "vaadin://baiduchart/js/baiduchart-custom-library.js", 
    "baiduchart-connector.js"})
@StyleSheet({"vaadin://baiduchart/theme/baiduchart-themes.css"})
public class BaiduChart extends AbstractJavaScriptComponent {

    private static final long serialVersionUID = -9130987146148572563L;
    private static int componentCount = 0;
    private final int componentId;
    private String optionContent;
    
    ArrayList<ValueChangeListener> listeners = new ArrayList<ValueChangeListener>();
    
    public BaiduChart(){
        super();
        componentId = componentCount;
        componentCount++;
        addFunction("onClick", new JavaScriptFunction(){

            private static final long serialVersionUID = -6466944937417325451L;

            @Override
            public void call(JSONArray arguments) throws JSONException {
                setValue(arguments.getString(0));
                for(ValueChangeListener listener: listeners){
                    listener.valueChange();
                }
            }
        });
    }
    
    public String setJsonOption(String title, String[] legendData, String[] xAxisData, Map<String, List<String>> seriesMap){
        String jsonData = new String();
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
        return jsonData;
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
    
    public interface ValueChangeListener extends Serializable {
        void valueChange();
    }
    
    public void addValueChangeListener(ValueChangeListener listener) {
        listeners.add(listener);
    }
    
    public void setValue(String value) {
        optionContent = value;
        BaiduChartData data = new BaiduChartData();
        data.id = componentId;
        data.value = value;
        data.state = "LOAD";
        getState().mycomData = data;
    }
    
    public void setTheme(BaiduChartTheme theme){
        BaiduChartData data = new BaiduChartData();
        data.id = componentId;
        data.theme = theme.getName();
        data.state = "THEME";
        getState().mycomData = data;
    }
    
    public String getValue(){
        return optionContent;
    }
    
    @Override
    protected BaiduChartState getState() {
        return (BaiduChartState) super.getState();
    }
}
