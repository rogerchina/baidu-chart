window.de_medavis_vaadin_addon_chart_BaiduChart = function(){
	var baiduChart;
	var e = this.getElement();
	var self = this;
	
	this.onStateChange = function() {
	    var state = this.getState();
	    initBaiduChart(state.mycomData.id);
	    if (state.mycomData.state === 'LOAD') {
	    	baiduChart.setValue(state.mycomData.value);
	    }
	    else if (state.mycomData.state === 'THEME') {
	    	baiduChart.setTheme(state.mycomData.theme);
	    }
	};
	
	initBaiduChart = function(id) {
	    if (typeof baiduChart === 'undefined') {
	    	baiduChart = new mylibrary.BaiduChartComponent(e, id);
	    }
	}
};