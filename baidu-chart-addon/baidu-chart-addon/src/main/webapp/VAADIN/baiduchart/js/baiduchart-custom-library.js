// Define the namespace
var mylibrary = mylibrary || {};
mylibrary.BaiduChartComponent = function(element, id) {
	// Set up the content
	element.innerHTML = "<div id='baiduchart" + id + "' style='height:250px;'></div>";
	
	// Style it
	element.style.border = "thin solid #c2d7e6";
	element.style.borderTopColor = "#c8dfed";
	element.style.borderBottomColor = "#a0c5dc";
	element.style.display = "inline-block";
	
	// Getter and setter for the value property
	this.getValue = function() {
		//return element.getElementsByTagName("input")[0].value;
	};
	
	var myChart;
	this.setValue = function(value) {
    	// 1. init the chart
        myChart = echarts.init(document.getElementById('baiduchart' + id)); 
        // 2. show loading
        myChart.showLoading({
            text: 'Loading...',
        });
        // 3. ajax getting the data from backend
        var option = eval('('+ value + ')'); // from JSON string to JSON object
        
        // 4. ajax callback
        myChart.hideLoading();	                    
        // 5. set up the myChart object with option data
        myChart.setOption(option); 
	};
	
	// Default implementation of the click handler
	this.click = function() {
		alert("Error: Must implement click() method");
	};
	
	this.setTheme = function(theme){
		myChart.setTheme(theme);
	}
};


