"use strict";

(function(){
	window.addEventListener("load", main);
}());

var inputActuator;
var getActuators;
var fillActuatorBox;
var test;

function main(){
	inputActuator = document.getElementById("inputActuator");
	
}

function getActuatorsController($scope, $http){
	test = function() {
		console.log($scope.inputActuator);
	}

	getActuators = function(){
		$http({
			method: "GET",
			url: "http://" + ipToConnect + "/getactuators",
		}).then(function successCallback(response) {
			fillActuatorBox(response);
		}, function errorCallback(response) {
			var statusText = createStatus("");
			if(response.status == -1){
				noConection(statusText);
				return;
			}
			statusResponse(response, statusText);
		});
	}
	
	fillActuatorBox = function(response){
		var option;
		var optionText;
		var actuators = response.data.actuators;

		for (let i = 0; i < actuators.length; i++) {
	        option = document.createElement("option");
	        option.value = actuators[i].actuato.class;
            optionText = document.createTextNode(actuators[i].address + " (" + actuators[i].actuato.class + ")");
            option.appendChild(optionText);

            inputActuator.appendChild(option);
		}
	}

	getActuators();
}



