var contactApp = angular.module('contactwebapp', [ 'ngRoute',
		'contactControllers' ]);

contactApp.config([ '$routeProvider', function($routeProvider) {
	$routeProvider.when('/contacts', {
		templateUrl : '/partials/contactList.html',
		controller : 'ContactListController'
	}).when('/contacts/:id', {
		templateUrl : '/partials/contactInfo.html',
		controller : 'ContactInfoController'
	}).when('/contacts/e/:id', {
		templateUrl : '/partials/editContact.html',
		controller : 'EditContactInfoController'
	}).otherwise({
		redirectTo : '/contacts'
	})
} ])
