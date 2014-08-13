// Define the namespace
var mylibrary = mylibrary || {};
mylibrary.BaiduChartComponent = function(element) {
	// Set up the content
	element.innerHTML = "<div id='baiduchart'></div>";
	
	// Style it
//	element.style.border = "thin solid #c2d7e6";
//	element.style.borderTopColor = "#c8dfed";
//	element.style.borderBottomColor = "#a0c5dc";
//	element.style.display = "inline-block";
	
	// Getter and setter for the value property
	this.getValue = function() {
		//return element.getElementsByTagName("input")[0].value;
	};
	
	this.setValue = function(value) {
    	// 1. init the chart
        var myChart = echarts.init(document.getElementById('baiduchart')); 
        // 2. show loading
        myChart.showLoading({
            text: 'Loading...',
        });
        // 3. ajax getting the data from backend
        var option = eval('('+ value + ')'); // from JSON string to JSON object
        myChart.setTheme("dark");
        // 4. ajax callback
        myChart.hideLoading();	                    
        // 5. set up the myChart object with option data
        myChart.setOption(option); 
	};
	
	// Default implementation of the click handler
	this.click = function() {
		alert("Error: Must implement click() method");
	};
	
	// Set up button click
//	var button = element.getElementsByTagName("input")[1];
//	var self = this; // Can't use this inside the function
//	button.onclick = function() {
//		self.click();
//	};
};


