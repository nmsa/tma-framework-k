"use strict";

(function(){
	window.addEventListener("load", main);
}());

var inputPartner;

function main(){
	inputPartner = document.getElementById("inputPartner");
}

function resourceController($scope, $http) {
	var checkFields = function(){
		if (inputPartner.value === undefined ||
			inputPartner.value === 0){
			return false;
		}
		if ($scope.inputResourceName === undefined ||
			$scope.inputResourceName === ""){
			return false;
		}
		if ($scope.inputResourceType === undefined ||
			$scope.inputResourceType === ""){
			return false;
		}
		if ($scope.inputResourceAddress === undefined ||
			$scope.inputResourceAddress === ""){
			return false;
		}
		return true;
	}

	$scope.createResource = function(){
		var statusText = createStatus("Waiting for server response");
		if (!checkFields()){
			statusText.innerHTML = "Please fill in all the fields";
			statusText.parentNode.style.borderColor = "red";
			return;
		}
		$http({
			method: "POST",
			url: "http://" + ipToConnect + "/addresource",
			data: {
				"partnerId": inputPartner.value,
				"resourceName": $scope.inputResourceName,
			    "resourceType": $scope.inputResourceType,
			    "resourceAddress": $scope.inputResourceAddress
			}
		}).then(function successCallback(response) {
			statusResponse(response, statusText);
		}, function errorCallback(response) {
			if(response.status == -1){
				noConection(statusText);
			}
			else{
				statusResponse(response, statusText);
			}
		});
	}
}

var app = angular.module("resource", []);

app.controller("resourceController", resourceController);
app.controller("partnerController", partnerController);
