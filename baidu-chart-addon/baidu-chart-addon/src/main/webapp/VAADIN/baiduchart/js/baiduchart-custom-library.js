// Define the namespace
var mylibrary = mylibrary || {};
mylibrary.MyComponent = function(element) {
	// Set up the content
	element.innerHTML = "<div id='main' style='height:400px; width:100%'></div>";
	
	// Style it
	element.style.border = "thin solid red";
	element.style.display = "inline-block";
	
	// Getter and setter for the value property
	this.getValue = function() {
		//return element.getElementsByTagName("input")[0].value;
	};
	
	this.setValue = function(value) {
        // 1.基于准备好的dom，初始化echarts图表
    	// srcipt标签式引入
        var myChart = echarts.init(document.getElementById('main')); 
        // 2. 过度...
        myChart.showLoading({
            text: 'Loading...',    //loading话术
        });
        // 3. ajax getting the data from backend
        var option = eval('('+ value + ')'); // from JSON string to JSON object
        // 4. ajax callback
        myChart.hideLoading();	                    
        // 5. 为echarts对象加载数据 
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


