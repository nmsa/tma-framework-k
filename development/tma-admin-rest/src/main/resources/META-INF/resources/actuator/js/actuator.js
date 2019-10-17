"use strict";


(function(){
	window.addEventListener("load", main);
}());

var inputPartner;
var buttonAddFile;
var buttonChooseFile;
var inputChooseFile;
var fileChosen;

function main(){
	inputPartner = document.getElementById("inputPartner");
	buttonAddFile = document.getElementById("buttonAddFile");
	buttonChooseFile = document.getElementById("buttonChooseFile");
	fileChosen = document.getElementById("fileChosen");
}

function actuatorController($scope, $http) {
	inputChooseFile = document.getElementById("inputChooseFile");
	
	var checkFields = function(){
		if (inputPartner.value === undefined ||
			inputPartner.value === 0){
			return false;
		}
		if (inputChooseFile.files[0] === undefined ||
			inputChooseFile.files[0] === ""){
			return false;
		}
		if ($scope.inputActuatorAddress === undefined ||
			$scope.inputActuatorAddress === ""){
			return false;
		}
		return true;
	}

	$scope.selectFile = function(){
		inputChooseFile.click();
	}

	inputChooseFile.onchange = function(){
		if(inputChooseFile.files[0] == undefined){
			fileChosen.innerHTML = "";
		}
		else{
			fileChosen.innerHTML = inputChooseFile.files[0].name;
		}
	};

	$scope.createActuator = function(){
		var statusText = createStatus("Waiting for server response");
		
		if (!checkFields()){
			statusText.innerHTML = "Please fill in all the fields";
			statusText.parentNode.style.borderColor = "red";
			return;
		}

		var formData = new FormData();
		formData.append("file", inputChooseFile.files[0]);
		formData.append("partnerId", inputPartner.value);
		formData.append("address", $scope.inputActuatorAddress);
		$http({
            method: 'POST',
            url: "http://" + ipToConnect + "/addactuator",
            headers: {'Content-Type': undefined },
            data: formData
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

var app = angular.module("actuator", []);

app.controller("actuatorController", actuatorController);
app.controller("partnerController", partnerController);


