var services = angular.module('builderApp.services', ['ngResource']);

services.factory('AuthenticationService', function($resource) {
	
	return $resource('rest/userService/getUser', {},
			{
				authenticate: {
					method: 'GET',
					headers : {'Content-Type': "application/json; charset=UTF-8"}
				},
			}
		);
});