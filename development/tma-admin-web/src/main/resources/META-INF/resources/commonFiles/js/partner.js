"use strict";

(function(){
	window.addEventListener("load", main);
}());

var inputPartner;
var getPartners;
var fillPartnerBox;

function main(){
	inputPartner = document.getElementById("inputPartner");
	
}

function partnerController($scope, $http){
	getPartners = function(){
		$http({
			method: "GET",
			url: "http://" + ipToConnect + "/getpartners",
		}).then(function successCallback(response) {
			fillPartnerBox(response.data.partnerList);
		}, function errorCallback(response) {
			var newStatus = createStatus("");
			if(response.status == -1){
				noConection(newStatus);
				return;
			}
			statusResponse(response, newStatus);
		});
	}

	fillPartnerBox = function(partnerList){
		var option;

		for (let i = 0; i < partnerList.length; i++) {
	        option = document.createElement("option");
	        option.value = partnerList[i].partnerId;
	        option.innerHTML = partnerList[i].partnerName;

            inputPartner.appendChild(option);
		}
	}

	getPartners();
}



