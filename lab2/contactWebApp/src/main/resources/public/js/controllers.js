/*
 * JS file for all of the Angular controllers in the app
 */
'use strict';

/*
 * Define the SFDC controllers scope for Angular
 */
var contactControllers = angular.module('contactControllers', []);

contactApp.controller('ContactListController', function($scope, $http) {

	$scope.getContacts = function() {
		// when landing on the page, get all contacts and show them
		$http.get('/contacts').success(function(data) {
			console.log("response data: " + JSON.stringify(data));
			$scope.contacts = data;
			// $http.get('/manage/env/CF_INSTANCE_INDEX').success(function(data) {
			// 	$scope.instance_index = data.property.value;
			// 	console.log('System Env CF_INSTANCE_INDEX: '+data.property.value);
			// }).error(function(data) {
			// 	console.log('Error: ' + data);
			// 	$scope.message = "";
			// 	$scope.error = "";
			// 	$scope.instance_index = 0;
			// });
			// $http.get('/sessionid').success(function(data) {
			// 	$scope.sessionid = data.sessionid;
			// }).error(function(data) {
			// 	console.log('Error: ' + data);
			// 	$scope.message = "";
			// 	$scope.error = "";
			// });
		}).error(function(data) {
			console.log('Error: ' + data);
			$scope.message = data.message;
			$scope.error = data.code;
		});
	};

	//Handles the delete request function
	$scope.delete = function(contactId) {
		console.log("deleting id " + contactId);
		$http.delete('/contacts/'+contactId).success(function(data) {
			$scope.getContacts();
		}).error(function(data) {
			console.log('Error: ' + data);
			$scope.message = data.message;
			$scope.error = data.code;
		});
	};

	// Initial page load
	$scope.getContacts();
});

contactApp.controller('ContactInfoController', function($scope, $http, $routeParams) {
	$scope.getContact = function() {
		$http.get("/contacts/" + $routeParams.id).success(function(data) {
			$scope.contact = data;
		});
	};

	//Handles the delete request function of the account
	$scope.delete = function(id) {
		if($routeParams.id != "new") {
			$http.delete('/contacts/'+id).success(function(data) {
				$scope.contact = "";
				$scope.message = "Successfully deleted the contact.";
				$scope.error = "";
			});
		}
	}


	// Initial page load
	$scope.getContact();
});

contactApp.controller('EditContactInfoController', function($scope, $http, $routeParams) {
	//Handles the update request function
	$scope.update = function(contact) {
		console.log("updating contact: "+JSON.stringify(contact));
		if(!(contact.id) && $routeParams.id == "new") {
			$http.post("/contacts", contact).success(function(data, status, headers, config) {
				console.log("created contact: "+status);
				console.log("created contact: "+data);
				$scope.contact = data;
				$scope.message = "Successfully saved the contact.";
			}).error(function(data, status, headers, config) {
				$scope.message = "";
				$scope.error = "There was an error saving the contact.";
			});
		} else {
			var contactId = contact.id;
			$http.put("/contacts/" + contactId, contact).success(function(data, status, headers, config) {
				$scope.message = "Successfully saved the contact.";
				$http.get("/contacts/" + contactId).success(function(data) {
					$scope.contact = data;
					$scope.message = "Successfully saved the contact.";
					$scope.error = "";
				});
			}).error(function(data, status, headers, config) {
				$scope.message = "Successfully saved the contact.";
				$scope.error = "";
			});
		}
	};

	//Handles the delete request function of the account
	$scope.delete = function(id) {
		if($routeParams.id != "new") {
			$http.delete('/contacts/'+id).success(function(data) {
				$scope.contact = "";
				$scope.message = "Successfully deleted the contact.";
				$scope.error = "";
			});
		}
	}

	//Handles the reset request function and the initial load of the entry
	$scope.reset = function() {
		if($routeParams.id != "new") {
			$http.get("/contacts/" + $routeParams.id).success(function(data) {
				$scope.contact = data;
			});
		}
	}

	//Initial load
	$scope.reset();
});
