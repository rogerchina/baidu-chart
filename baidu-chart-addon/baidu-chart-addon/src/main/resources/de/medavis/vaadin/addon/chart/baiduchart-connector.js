window.de_medavis_vaadin_addon_chart_BaiduChart = function(){
	// Create the component
	var mycomponent = new mylibrary.MyComponent(this.getElement());
	// Handle changes from the server-side
	this.onStateChange = function() {
		var state = this.getState();
		if(state.mycomData.state === "LOAD"){
			mycomponent.setValue(state.mycomData.value);
		} else if(state.mycomData.state === "THEME"){
			mycomponent.setTheme(state.mycomData.theme);
		}
		
	};
	// Pass user interaction to the server-side
	var self = this;
	mycomponent.click = function() {
		self.onClick(mycomponent.getValue());
	};
}