package de.medavis.vaadin.addon.chart;


public enum BaiduChartShape {
    LINE("line"),
    BAR("bar"),
    SCATTER("scatter"),
    PIE("pie"),
    RADAR("radar");
    
    private final String name;
    private BaiduChartShape(String name){
        this.name = name;
    }
    
    public String getName() {
        return name;
    }
    
    @Override
    public String toString(){
        return name;
    }
}
