"use strict";

(function(){
	window.addEventListener("load", main);
}());


function main(){



}

function probeController($scope, $http) {
	var row;
	var i;
	var cell;
	var cellText;
	var img;
	var probeRows = [];

	var tbody = document.getElementById("tableBody");


	function deleteACell(event){
		if(confirm("Are you sure you want to delete the probe?")){
			eraseProbe(event.currentTarget.parentElement);
		}else{
		   return;
		}
	}

	function eraseProbe(clickedRow){
		$http({
			method: "DELETE",
			url: "http://" + ipToConnect + "/deleteprobe?probeIdString=" + clickedRow.value
		}).then(function successCallback(response) {
			var statusText = createStatus("");
			statusResponse(response, statusText);
			for (i = 0; i < probeRows.length; i++) {
				if (probeRows[i] === clickedRow){
					probeRows.splice(0,i + 1);
				}
			}
			clickedRow.remove();
		}, function errorCallback(response) {
			var statusText = createStatus("");
			if(response.status == -1){
				noConection(statusText);
				return;
			}
			statusResponse(response, statusText);
		});
	}

	function editACell(event){
		alert("Feature not yet implemented.");
	}
	
	function getProbes(actuatorId){
		$http({
			method: "GET",
			url: "http://" + ipToConnect + "/getprobes",
		}).then(function successCallback(response) {
			fillProbeTable(response);
		}, function errorCallback(response) {
			var statusText = createStatus("");
			if(response.status == -1){
				noConection(statusText);
				return;
			}
			statusResponse(response, statusText);
		});
	}

	

	$scope.createProbe = function(){
		var statusText = createStatus("Waiting for server response");
		if (!checkFields()){
			statusText.innerHTML = "Please fill in all the fields";
			statusText.parentNode.style.borderColor = "red";
			return;
		}
		$http({
			method: "POST",
			url: "http://" + ipToConnect + "/addprobe",
			data: {
				"partnerId": inputPartner.value,
				"probeName": inputProbeName.value,
			    "password": inputPassword.value,
			    "token": inputToken.value,
			    "tokenExpiration": getTokenExpirationTime()
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

	function fillProbeTable(response){
		var option;
		var optionText;
		var probes = response.data.probes;

		for (i = 0; i < probes.length; i++) {
	        row = document.createElement("tr");
			row.className = "row";
	        row.value = probes[i].probeId;

	        cell = document.createElement("td");
            cellText = document.createTextNode(probes[i].probeName);
            cell.appendChild(cellText);
            row.appendChild(cell);

	        cell = document.createElement("td");
            cellText = document.createTextNode(probes[i].probeId);
            cell.appendChild(cellText);
            row.appendChild(cell);

	        cell = document.createElement("td");
            cellText = document.createTextNode(probes[i].token);
            cell.appendChild(cellText);
            row.appendChild(cell);

	        cell = document.createElement("td");
            cellText = document.createTextNode(probes[i].tokenExpirationString.split(" ")[0]);
            cell.appendChild(cellText);
            row.appendChild(cell);

	        cell = document.createElement("td");
	        img = document.createElement("img");
	        img.src = "../../commonFiles/img/delete.png";
	        img.className = "delete";
	        cell.addEventListener("click", deleteACell);
	        cell.appendChild(img);
	        row.appendChild(cell);
	        tbody.appendChild(row);

	        probeRows.push(row);
	    }
	}

	getProbes();
}

var app = angular.module("probe", []);

app.controller("probeController", probeController);