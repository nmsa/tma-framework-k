"use strict";

(function(){
	window.addEventListener("load", main);
}());



function main(){

	var newActuatorPopup = document.getElementById("newActuatorPreControler");
	newActuatorPopup.hideName = "newActuator";
}

function homePageController($scope, $http) {
	$scope.hideNewActuator = true;
	$scope.hideWhileWaitingForDownload = true;

	$scope.generatekeys = function(event) {
		$scope.hideNewActuator = false;
		$scope.hideWhileWaitingForDownload = false;
	}

	$scope.return = function(event){
		$scope.hideNewActuator = true;
	}
    

}

angular.module("homePage", [])
	.controller("homePageController", homePageController)
