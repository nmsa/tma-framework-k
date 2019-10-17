"use strict";


(function(){
	window.addEventListener("load", main);
}());

var inputActuator;
var inputDomain;
var tableActions;
var tableConfigurations;
var inputPartner;

var selectedRow = 0;

var configurationRows = [];
var actionRows = [];

var getConfigurations;
var getActions;
var getActuators;

var eraseConfigTable;
var eraseActionTable;

var deleteRowConfiguration;
var deleteRowAction;

var eraseConfiguration;
var eraseAction;

var fillConfigTable;
var fillActionsTable;
var fillActuatorBox;

var updateConfigurations;

var cellClick;

function main(){
	inputDomain = document.getElementById("inputDomain");
	tableConfigurations = document.getElementById("tableConfigurations");
}

function actionsController($scope, $http) {
	var chooseAnActuatorOption;
	$scope.actuatorWasNotChanged = true;
	$scope.actionNotChosen = true;

	var checkFields = function(){
		if (selectedRow.value === undefined ||
			selectedRow.value === 0){
			return false;
		}
		if (inputKey.value === undefined ||
			inputKey.value === ""){
			return false;
		}
		if (inputDomain.value === undefined ||
			inputDomain.value === ""){
			return false;
		}
		return true;
	}

	inputActuator = document.getElementById("inputActuator");
	tableActions = document.getElementById("tableActions");

	fillConfigTable = function(response){
		var row;
		var cell;
		var insideCell;
		var configurations = response.data.configurations;

	    for (let i = 0; i < configurations.length; i++) {
	        row = document.createElement("tr");
	        row.value = configurations[i].configurationId;


            cell = document.createElement("td");
            insideCell = document.createTextNode(configurations[i].keyName);
            cell.appendChild(insideCell);
            cell.className = "cell";
            row.appendChild(cell);

            cell = document.createElement("td");
            insideCell = document.createTextNode(configurations[i].domain);
            cell.appendChild(insideCell);
            cell.className = "cell";
            row.appendChild(cell);

            cell = document.createElement("td");
            insideCell = document.createElement("img");
            insideCell.className = "delete";
            insideCell.src = "../../commonFiles/img/delete.png";
            insideCell.addEventListener("click", deleteRowConfiguration);
            cell.appendChild(insideCell);
            cell.className = "cell";
            row.appendChild(cell);
           	configurationRows.push(row);


	        tableConfigurations.appendChild(row);
	    }
	}

	getConfigurations = function(actionId){
		var urlGet = "http://" + ipToConnect + "/getconfigurations?actionIdString=" + actionId;
		$http({
			method: "GET",
			url: urlGet,
		}).then(function successCallback(response) {
			fillConfigTable(response);
		}, function errorCallback(response) {
			var statusText = createStatus("");
			if(response.status == -1){
				noConection(statusText);
				return;
			}
			statusResponse(response, statusText);
		});
	}

	eraseConfigTable = function(){
		for (let i = 0; i < configurationRows.length; i++) {
			configurationRows[i].remove();
		}
		configurationRows = [];
	}

	eraseActionTable = function(){
		for (let i = 0; i < actionRows.length; i++) {
			actionRows[i].remove();
		}
		actionRows = [];
	}

	var rowSelection = function(clickedRow){
		if(selectedRow === clickedRow){
			return;
		}
		else if(selectedRow != 0){
			selectedRow.className = "row";
			eraseConfigTable();
		}
		else{
			$scope.actionNotChosen = false;
		}
		selectedRow = clickedRow;
		selectedRow.className = "selectedRow";
		getConfigurations(selectedRow.value);
	}

	cellClick = function(event){
		rowSelection(event.currentTarget.parentNode);
	}

	eraseAction = function(clickedRow){
		$http({
			method: "DELETE",
			url: "http://" + ipToConnect + "/deleteaction?actionIdString=" + clickedRow.value
		}).then(function successCallback(response) {
			var statusText = createStatus("");
			statusResponse(response, statusText);
			if (selectedRow === clickedRow){
				$scope.actionNotChosen = true;
				selectedRow = 0;
				eraseConfigTable();
			}
			for (let i = 0; i < actionRows.length; i++) {
				if (actionRows[i] === clickedRow){
					actionRows.splice(0,i + 1);
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

	deleteRowAction = function(event){
		if(confirm("Are you sure you want to delete the action?")){
			eraseAction(event.currentTarget.parentElement.parentElement);
		}else{
		   return;
		}
	}

	eraseConfiguration = function(clickedRow){
		$http({
			method: "DELETE",
			url: "http://" + ipToConnect + "/deleteconfiguration?configurationIdString=" + clickedRow.value
		}).then(function successCallback(response) {
			var statusText = createStatus("");
			statusResponse(response,statusText);
			for (let i = 0; i < configurationRows.length; i++) {
				if (configurationRows[i] === clickedRow){
					configurationRows.splice(0,i + 1);
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

	deleteRowConfiguration = function(event){
		if(confirm("Are you sure you want to delete the action?")){
			eraseConfiguration(event.currentTarget.parentElement.parentElement);
		}else{
		   return;
		}
	}

	fillActionsTable = function(response){
		var row;
		var cell;
		var insideCell;
		var actions = response.data.actions;

		if(actions.length == 0){
			$scope.actionNotChosen = true;
			selectedRow = 0;
		}

		for (let i = 0; i < actions.length; i++) {
	        row = document.createElement("tr");
	        row.className = "row";
	        row.value = actions[i].actionId;


            cell = document.createElement("td");
            insideCell = document.createTextNode(actions[i].actionName);
            cell.appendChild(insideCell);
            cell.className = "cell";
            cell.addEventListener("click", cellClick);
            row.appendChild(cell);

            cell = document.createElement("td");
            insideCell = document.createTextNode(actions[i].resourceName);
            cell.appendChild(insideCell);
            cell.className = "cell";
            cell.addEventListener("click", cellClick);
            row.appendChild(cell);

            cell = document.createElement("td");
            insideCell = document.createElement("img");
            insideCell.className = "delete";
            insideCell.src = "../../commonFiles/img/delete.png";
            insideCell.addEventListener("click", deleteRowAction);
            cell.appendChild(insideCell);
            cell.className = "cell";
            cell.addEventListener("click", cellClick);
            row.appendChild(cell);

            row.value = actions[i].actionId;

            tableActions.appendChild(row);
            actionRows.push(row);
            if(i == 0){
            	cell.click();
            }
		}
	}

	getActions = function(actuatorId){
		$http({
			method: "GET",
			url: "http://" + ipToConnect + "/getactions?actuatorIdString=" + actuatorId,
		}).then(function successCallback(response) {
			fillActionsTable(response);
		}, function errorCallback(response) {
			var statusText = createStatus("");
			if(response.status == -1){
				noConection(statusText);
				return;
			}
			statusResponse(response, statusText);
		});
	}

	function removeChooseActuatorOption(){
		chooseAnActuatorOption.remove();
	}


	inputActuator.onchange = function(){
		if($scope.actuatorWasNotChanged == true){
			$scope.actuatorWasNotChanged = false;
			removeChooseActuatorOption();	
		}
		eraseConfigTable();
		eraseActionTable();
		getActions(inputActuator.value);
	}
	
	updateConfigurations = function(key, domain, configurationId){
		var row;
		var cell;
		var insideCell;

		row = document.createElement("tr");
	    row.value = configurationId;

        cell = document.createElement("td");
        insideCell = document.createTextNode(key);
        cell.appendChild(insideCell);
        cell.className = "cell";
        row.appendChild(cell);

        cell = document.createElement("td");
        insideCell = document.createTextNode(domain);
        cell.appendChild(insideCell);
        cell.className = "cell";
        row.appendChild(cell);

        cell = document.createElement("td");
        insideCell = document.createElement("img");
        insideCell.className = "delete";
        insideCell.src = "../../commonFiles/img/delete.png";
        insideCell.addEventListener("click", deleteRowConfiguration);
        cell.appendChild(insideCell);
        cell.className = "cell";
        row.appendChild(cell);
        configurationRows.push(row);


        tableConfigurations.appendChild(row);
	}

	$scope.addConfiguration = function(){
		if (selectedRow === 0){
			statusText.parentNode.style.borderColor = "red";
			var statusText = createStatus("No action selected");
			return;
		}
		var statusText = createStatus("Waiting for server response");
		if (!checkFields()){
			statusText.innerHTML = "Please fill in all the fields";
			statusText.parentNode.style.borderColor = "red";
			return;
		}
		$http({
			method: "POST",
			url: "http://" + ipToConnect + "/addconfiguration",
			data: {
				"actionId": selectedRow.value,
				"keyName": inputKey.value,
			    "domain": inputDomain.value
			}
		}).then(function successCallback(response) {
			statusResponse(response, statusText);
			updateConfigurations(
				$scope.inputKey,
				inputDomain.value,
				response.data.configurationId);
		}, function errorCallback(response) {
			if(response.status == -1){
				noConection(statusText);
				return;
			}
			statusResponse(response, statusText);
		});
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

		option = document.createElement("option");
		option.value = -1;
		optionText = document.createTextNode("Choose an Actuator");
		option.appendChild(optionText);

		chooseAnActuatorOption = option;

        inputActuator.appendChild(option);

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

