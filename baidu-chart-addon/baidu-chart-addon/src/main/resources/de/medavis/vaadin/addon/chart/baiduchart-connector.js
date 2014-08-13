window.de_medavis_vaadin_addon_chart_BaiduChart = function(){
	// Create the component
	var baiduChart = new mylibrary.BaiduChartComponent(this.getElement());
	// Handle changes from the server-side
	this.onStateChange = function() {
		var state = this.getState();
		if(state.mycomData.state === "LOAD"){
			baiduChart.setValue(state.mycomData.value);
		} else if(state.mycomData.state === "THEME"){
			baiduChart.setTheme(state.mycomData.theme);
		}
		
	};
	// Pass user interaction to the server-side
	var self = this;
	baiduChart.click = function() {
		self.onClick(baiduChart.getValue());
	};
}