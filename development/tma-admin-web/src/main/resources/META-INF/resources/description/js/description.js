"use strict";

(function(){
	window.addEventListener("load", main);
}());

var inputPartner;
var inputDataTypeMeasurement;
var inputDataTypeEvent;

function main(){
	inputDataTypeMeasurement = document.getElementById("inputDataTypeMeasurement");
	inputDataTypeEvent = document.getElementById("inputDataTypeEvent");
}

function descriptionController($scope, $http) {
	inputPartner = document.getElementById("inputPartner");
	
	var checkFields = function(){
		if (inputPartner.value === undefined ||
			inputPartner.value === 0){
			return false;
		}
		if ($scope.inputDescriptionName === undefined ||
			$scope.inputDescriptionName === ""){
			return false;
		}
		if (!inputDataTypeMeasurement.checked &&
			!inputDataTypeEvent.checked){
			return false;
		}
		if ($scope.inputUnit === undefined ||
			$scope.inputUnit === ""){
			return false;
		}

		return true;
	}
	
	$scope.createDescription = function(){
		console.log(ipToConnect);
		var statusText = createStatus("Waiting for server response");
		var dataType;
		
		if (!checkFields()){
			statusText.innerHTML = "Please fill in all the fields";
			statusText.parentNode.style.borderColor = "red";
			return;
		}

		if(inputDataTypeMeasurement.checked){
			dataType = "Measurement";
		}
		else if(inputDataTypeEvent.checked){
			dataType = "Event";
		}
		else{
			statusText.innerHTML = "Please check a data type";
			return
		}

		$http({
			method: "POST",
			url: "http://" + ipToConnect + "/adddescription",
			data: {
				"partnerId": inputPartner.value,
				"descriptionName": $scope.inputDescriptionName,
			    "dataType": dataType,
			    "unit": $scope.inputUnit
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
}

var app = angular.module("description", []);

app.controller("descriptionController", descriptionController);
app.controller("partnerController", partnerController);

