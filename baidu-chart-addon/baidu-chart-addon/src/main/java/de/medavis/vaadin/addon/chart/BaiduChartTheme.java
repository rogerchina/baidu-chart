package de.medavis.vaadin.addon.chart;


public enum BaiduChartTheme {
    MACARONS("macarons"),
    INFOGRAPHIC("infographic"),
    SHINE("shine"),
    DARK("dark"),
    BLUE("blue"),
    GREEN("green"),
    RED("red"),
    GRAY("gray"),
    DEFAULT("default");
    
    private final String name;
    private BaiduChartTheme(String name){
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
