"use strict";

(function(){
	window.addEventListener("load", main);
}());

var inputActuator;
var inputResourceName;
var getActuators;
var getResources;
var fillActuatorBox;
var fillResourceBox;
var resourceBoxOptions = [];
var eraseResourceBox;


function actionsController($scope, $http) {
	inputActuator = document.getElementById("inputActuator");
	inputResourceName = document.getElementById("inputResourceName");
	
	var checkFields = function(){
		if (inputActuator.value === undefined ||
			inputActuator.value === 0){
			return false;
		}
		if (inputResourceName.value === undefined ||
			inputResourceName.value === ""){
			return false;
		}
		if ($scope.inputActionName === undefined ||
			$scope.inputActionName === ""){
			return false;
		}
		return true;
	}

	$scope.createAction = function(){
		var statusText = createStatus("Waiting for server response");
		
		if (!checkFields()){
			statusText.innerHTML = "Please fill in all the fields";
			statusText.parentNode.style.borderColor = "red";
			return;
		}
		
		$http({
			method: "POST",
			url: "http://" + ipToConnect + "/addaction",
			data: {
				"actuatorId": inputActuator.value,
			    "resourceId": inputResourceName.value,
			    "actionName": $scope.inputActionName
			}
		}).then(function successCallback(response) {
			statusResponse(response, statusText);
		}, function errorCallback(response) {
			if(response.status == -1){
				noConection(statusText);
				return;
			}
			statusResponse(response, statusText);
		});
	}

	getResources = function(actuatorId){
		$http({
			method: "GET",
			url: "http://" + ipToConnect + "/getresources?actuatorIdString=" + actuatorId
		}).then(function successCallback(response) {
			fillResourceBox(response);
		}, function errorCallback(response) {
			var statusText = createStatus("");
			if(response.status == -1){
				noConection(statusText);
				return;
			}
			statusResponse(response, statusText);
		});
	}
	
	inputActuator.onchange = function(){
		eraseResourceBox();
		getResources(inputActuator.value);
	}

	fillResourceBox = function(response){
		var option;
		var optionText;
		var resources = response.data.resources;

		for (let i = 0; i < resources.length; i++) {
	        option = document.createElement("option");
	        option.value = resources[i].resourceId;
            optionText = document.createTextNode(resources[i].resourceName);
            option.appendChild(optionText);

            inputResourceName.appendChild(option);
            resourceBoxOptions.push(option);
		}
	}

	eraseResourceBox = function(){
		for (let i = 0; i < resourceBoxOptions.length; i++) {
			resourceBoxOptions[i].remove();
		}
		resourceBoxOptions = [];
	}

	getActuators = function(){
		$http({
			method: "GET",
			url: "http://" + ipToConnect + "/getactuators",
		}).then(function successCallback(response) {
			fillActuatorBox(response);
			getResources(inputActuator.value);
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
	        option.value = actuators[i].actuatorId;
            optionText = document.createTextNode(actuators[i].address + " (" + actuators[i].actuatorId + ")");
            option.appendChild(optionText);

            inputActuator.appendChild(option);
		}
	}

	getActuators();
}

var app = angular.module("actions", []);

app.controller("actionsController", actionsController);
app.controller("partnerController", partnerController);


