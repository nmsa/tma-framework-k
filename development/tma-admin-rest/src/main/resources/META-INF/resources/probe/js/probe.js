"use strict";

(function(){
	window.addEventListener("load", main);
}());

var tokenExpiration;
var inputPartner;

var calendar;
var selectYear;
var selectMonth;
var calendarTitle;
var table;

var today;
var currentMonth;
var currentYear;
var showCalendar;
var clickedDay;
var clickedMonth;
var clickedYear;

var inputProbeName;
var inputPassword;
var inputSalt;
var inputToken;

var months;

function main(){
	tokenExpiration = document.getElementById("inputTokenExpiration");
	inputPartner = document.getElementById("inputPartner");

	calendar = document.getElementById("calendarBeforeController");
	calendarTitle = document.getElementById("calendarTitle");
	table = document.getElementById("calendarTable");

	today = new Date();
	currentMonth = today.getMonth();
	currentYear = today.getFullYear();

	clickedDay   = today.getDate();
	clickedMonth = today.getMonth();
	clickedYear  = today.getFullYear();

	tokenExpiration.value = clickedDay + "-" + (parseInt(clickedMonth) + 1) + "-" + clickedYear;

	months = [
				"January",
				"February",
				"March",
				"April",
				"May",
				"June",
				"July",
				"August",
				"September",
				"October",
				"November",
				"December"
			];

	if(selectMonth === undefined){
		selectMonth = document.getElementById("selectMonth");
	}

	for (let i = 0; i < 12; i++) {
        let option = document.createElement("option");
        option.value = i;
        option.innerHTML = months[i];

	    selectMonth.appendChild(option);
    }

	if(selectYear === undefined){
		selectYear = document.getElementById("selectYear");
	}

    for (let i = 2019; i <= 2100; i++){
        let option = document.createElement("option");
        option.value     = i;
        option.innerHTML = i;

	    selectYear.appendChild(option);
    }
}

function isLeapYear(year){
	return ((year % 400 === 0) || ((year % 4 === 0) && (year % 100 != 0)));
}

function daysInMonth(month, year) {
    switch(month){
    	case 0: return 31;
    	case 1: return 28 + isLeapYear(year);
    	case 2: return 31;
    	case 3: return 30;
    	case 4: return 31;
    	case 5: return 30;
    	case 6: return 31;
    	case 7: return 31;
    	case 8: return 30;
    	case 9: return 31;
    	case 10: return 30;
    	case 11: return 31;
    	default: return -1;
    }
}

function probeController($scope, $http) {
	$scope.calendarHidden = true;

	inputProbeName = document.getElementById("inputProbeName");
	inputPassword = document.getElementById("inputPassword");
	inputSalt = document.getElementById("inputSalt");
	inputToken = document.getElementById("inputToken");

	showCalendar = function(month, year) {
	    var firstDay = (new Date(year, month)).getDay();
	    var date, cell, cellText, i, j, row;

	    table.innerHTML = "";
	    calendarTitle.innerHTML = months[month] + " " + year;
	    selectYear.value = year;
	    selectMonth.value = month;

	    date = 1;
	    for (i = 0; i < 6; i++) {
	        row = document.createElement("tr");

	        for (j = 0; j < 7; j++) {

	            cell = document.createElement("td");
                cellText = document.createTextNode("");
                cell.appendChild(cellText);
                row.appendChild(cell);
				
				if (date <= daysInMonth(month, year)) {
	            
	            	if(i * 7 + j >= firstDay) {
		                cellText.nodeValue = date;
		                if (date === clickedDay && month === clickedMonth && year === clickedYear) {
		                    cell.className = "clickedCell";
		                }
		                else{
			                cell.className = "cell";
		                }
		                cell.addEventListener("click", clickedACell);
		                date++;
		            }
	        	}
	        }
	        table.appendChild(row);
	    }
	}

	$scope.onShowPasswordClick = function(event){
		if (inputPassword.type == "text"){
			inputPassword.type = "password";
		}
		else{
			inputPassword.type = "text";
		}
	}

	$scope.onCalendarClick = function(event) {
		$scope.calendarHidden = !$scope.calendarHidden;
		if(!$scope.calendarHidden){
			showCalendar(currentMonth , currentYear);
		}
	}

	$scope.next = function(event) {
		if(currentYear < 2100 || (currentYear === 2100 && currentMonth < 11)){
			if(currentMonth === 11){
				currentYear = currentYear + 1;
			}
		    currentMonth = (currentMonth + 1) % 12;
		    showCalendar(currentMonth, currentYear);
		}
	}

	$scope.previous = function(event) {
		if(currentYear > 2019 || (currentYear === 2019 && currentMonth > 0)){
			if(currentMonth === 0){
				currentYear = currentYear - 1;
				currentMonth = 11;
			}
			else{
				currentMonth = currentMonth - 1;
			}
		    showCalendar(currentMonth, currentYear);
		}
	}

	var jump = function(event) {
	    currentYear = parseInt(selectYear.value);
	    currentMonth = parseInt(selectMonth.value);
	    showCalendar(currentMonth, currentYear);
	}

	var clickedACell = function(event){
		event.currentTarget.className = "clickedCell";
		$scope.calendarHidden = !$scope.calendarHidden;

		clickedDay   = parseInt(event.currentTarget.innerHTML);
		clickedMonth = parseInt(selectMonth.value);
		clickedYear  = parseInt(selectYear.value);

		tokenExpiration.value = clickedDay + "-" + (clickedMonth + 1) + "-" + clickedYear;
		$scope.$apply();
	}

	var checkFields = function(){
		if (inputPartner.value === undefined ||
			inputPartner.value === 0){
			return false;
		}
		if (inputProbeName.value === undefined ||
			inputProbeName.value === ""){
			return false;
		}
		if (inputPassword.value === undefined ||
			inputPassword.value === ""){
			return false;
		}
		if (inputSalt.value === undefined ||
			inputSalt.value === ""){
			return false;
		}
		if (inputToken.value === undefined ||
			inputToken.value === ""){
			return false;
		}
		return true;
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
			    "salt": inputSalt.value,
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



	function prepareInputTokenExpiration(){
		var tokens = tokenExpiration.value.split("-");
		if(tokens.length != 3){
			return;
		}
		return tokens[2] + "-" + tokens[1] + "-" + tokens[0];
	}

	function getTokenExpirationTime(){
		return (new Date(prepareInputTokenExpiration())).getTime();
	}
	
	

	
	if(selectMonth === undefined){
		selectMonth = document.getElementById("selectMonth");
	}
	selectMonth.addEventListener("change", jump);
	
	if(selectYear === undefined){
		selectYear = document.getElementById("selectYear");
	}
	selectYear.addEventListener("change", jump);
}

var app = angular.module("probe", []);

app.controller("probeController", probeController);
app.controller("partnerController", partnerController);


