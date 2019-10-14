"use strict";

(function(){
	window.addEventListener("load", main);
}());

var statusList;
var mainButton;
var createStatus;
var statusResponse;
var noConection;

function main(){
	statusList = document.getElementById("statusList");
	mainButton = document.getElementById("mainButton");

	function deleteStatusMessage(event){
		event.currentTarget.parentNode.remove();
		mainButton.disabled = false;
	}

	createStatus = function (text){
		var newStatus;
		var statusText;
		var deleteImage;

		newStatus = document.createElement("div");
		newStatus.className = "status";

		statusText = document.createElement("p");
		statusText.innerHTML = text;
		statusText.p = "statusText";
		newStatus.appendChild(statusText);

        deleteImage = document.createElement("img");
        deleteImage.className = "delete";
        deleteImage.src = "../../commonFiles/img/delete.png";
        deleteImage.addEventListener("click", deleteStatusMessage);
        newStatus.appendChild(deleteImage);

		mainButton.disabled = true;

		statusList.appendChild(newStatus);
		newStatus.className = "newStatus";

		return statusText;
	}

	statusResponse = function(response, statusText, newStatus){
		statusText.innerHTML = response.data.message;

		switch(response.data.messageType){
			case "error":
				statusText.parentNode.style.borderColor = "red";
				break;
			case "warning":
				statusText.parentNode.style.borderColor = "yellow";
				break;
			case "success":
				statusText.parentNode.style.borderColor = "green";
				break;
			case "unknown":
				statusText.parentNode.style.borderColor = "black";
				break;
			default:
				statusText.parentNode.style.borderColor = "black";
				break;
		}
	}

	noConection = function(statusText){
		statusText.innerHTML = "Error, unable to connect to the server. Please check if the server is up.";
		statusText.parentNode.style.borderColor = "red";
	}
}

