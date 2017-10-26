//create angular app
var loginController = angular.module('AuthenticationController', []);

// create angular controller
loginController.controller('loginController', 
		function($scope, $rootScope, $location, $cookieStore, $http) {
	
	$scope.invalidCreds="";
	$scope.rememberMe = false;
	
	$scope.login = function() {
		$scope.authenticate()
			.success(function (authenticationResult) {
				var authToken = authenticationResult.token;
				$rootScope.authToken = authToken;
				if ($scope.rememberMe) {
					$cookieStore.put('authToken', authToken);
				}
				$scope.getUser()
					.success(function(userResult) {
						$rootScope.user = userResult;
						$scope.invalidCreds="";
						$location.path("/");
						})
					.error(function(){
						alert("error occured while retriving user information!")
					});
			})
			.error(function (){
				$scope.invalidCreds="Invalid credentials";
			});
	};
	
	$scope.authenticate = function(){
		var jsonData = JSON.stringify({userName: $scope.username,
			    						password: $scope.password});
		var req = {
    			method: "POST",
	            url: "rest/userService/authenticate",
	            headers: {'Content-Type': "application/json; charset=UTF-8"},
	            data:jsonData
    	}
		return $http(req);
   	};
	
	$scope.getUser = function(){
		var req = {
				method: "GET",
	            url: "rest/userService/getUser",
	            headers: {'Content-Type': "application/json; charset=UTF-8"}	            
				}
		return $http(req);  
	};
});