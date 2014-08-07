package de.medavis.vaadin.addon.chart;

import java.io.Serializable;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;

import com.vaadin.annotations.JavaScript;
import com.vaadin.ui.AbstractJavaScriptComponent;
import com.vaadin.ui.JavaScriptFunction;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;

@JavaScript({"vaadin://baiduchart/js/echarts-plain.js", "vaadin://baiduchart/js/baiduchart-custom-library.js", "baiduchart-connector.js"})
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
                Notification.show(getValue(), Type.HUMANIZED_MESSAGE);
            }
        });
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
        data.state = "Load";
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
