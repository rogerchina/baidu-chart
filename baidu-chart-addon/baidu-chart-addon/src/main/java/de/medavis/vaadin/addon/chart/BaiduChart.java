package de.medavis.vaadin.addon.chart;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;

import com.vaadin.annotations.JavaScript;
import com.vaadin.ui.AbstractJavaScriptComponent;
import com.vaadin.ui.JavaScriptFunction;

@JavaScript({"vaadin://baiduchart/js/echarts-plain.js", 
    "vaadin://baiduchart/js/baiduchart-custom-library.js", 
    "baiduchart-connector.js"})
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
    
    public String setJsonOption(String title, Map<String, List<Point>> dataMap){
        // convert dataMap into stringMap
        List<String> xAxisList = new ArrayList<>();
        Map<String, List<String>> stringMap = new LinkedHashMap<>();
        List<String> stringList = null;
        List<Point> pointList = null;
        for(String key : dataMap.keySet()){
            pointList = dataMap.get(key);
            stringList = new ArrayList<>();
            for(Point point : pointList){
                stringList.add(point.getY().toString());
            }
            stringMap.put(key, stringList);
        }
        
        for(String key : dataMap.keySet()){
            List<Point> pointListTemp = dataMap.get(key);
            for(Point point : pointListTemp){
                xAxisList.add(point.getX());
            }
            break;
        }

        // json data
        String jsonData = new String();
        // legend data
        String [] legend = (String[])dataMap.keySet().toArray(new String[0]);
        // x axis data
        String[] xAxis = (String[])xAxisList.toArray(new String[0]);
        // y axis data
        StringBuilder series = new StringBuilder();
        series.append("series:[");
        for(String str : stringMap.keySet()){
            series.append("{name:'" + str + "',"
                    + "type:'bar',"
                    + "itemStyle:{normal:{color:'rgb(32, 80, 129)'}},"
                    + "data:" + stringMap.get(str).toString() + ","
                    + "markPoint:{data:[{type:'max',name:'Max value'},{type:'min',name:'Min value'}]},markLine:{data:[{type:'average',name:'Average value'}]}},");
        }
        series = new StringBuilder(series.substring(0, series.length()-1));
        series.append("]");
        
        jsonData = "{"
                + "title:{text:'" + title + "'},"
                + "tooltip:{trigger:'axis'},"
                + "legend:{data:[" + toStringFromArray(legend) + "]},"
                + "toolbox:{"
                    + "show:false,"
                    + "feature:{"
                        + "mark:{show:true, title:'Assistant line mark'},"
                        + "dataView:{show:true,title:'Data view',readOnly:false},"
                        + "magicType:{show:true,title:{line:'line',bar:'bar',stack:'stack',tiled:'tiled'},type:['line','bar']},"
                        + "restore:{show:true,title:'restore'},"
                        + "saveAsImage:{show:true,title:'saveAsImage'}}},"
                + "calculable:true,"
                + "xAxis:[{type:'category',data:[" + toStringFromArray(xAxis) + "]}],"
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
