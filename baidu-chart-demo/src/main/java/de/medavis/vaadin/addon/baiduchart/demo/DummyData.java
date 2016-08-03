package de.medavis.vaadin.addon.baiduchart.demo;


public class DummyData {
    // for work flow
    public enum WorkflowDummy {
        RIS_HIS("ris_his"),
        PACS_G4M("pacs_g4m"),
        RELABIT_PABS_REFER("relabit_pabs_refer");
        
        private final String name;
        private WorkflowDummy(String name){
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
    
    // for time category
    public enum timeCategoryDummy {
        YEAR("year"),
        MONTH("month"),
        WEEK("week"),
        DAY("day"),
        HOUR("hour");
        
        private final String name;
        private timeCategoryDummy(String name){
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

}
