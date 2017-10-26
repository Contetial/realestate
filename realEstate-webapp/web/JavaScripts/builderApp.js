var builderapp = angular.module('builderApp',['ngRoute',
                                              'ngCookies',
                                              'builderControllers',
                                              'builderApp.services']);

builderapp.config(['$routeProvider','$locationProvider', '$httpProvider', 
                   function($routeProvider, $locationProvider, $httpProvider) {
	$routeProvider.when('/',{
		templateUrl:'pages/info/HomePage.jsp',
		controller:'genericController'				
	});	
	$routeProvider.when('/home',{
		templateUrl:'pages/info/HomePage.jsp',
		controller:'genericController'				
	});
	$routeProvider.when('/properties',{
		templateUrl:'pages/info/Properties.jsp',
		controller:'genericController'
	});
	$routeProvider.when('/aboutus',{
		templateUrl:'pages/info/AboutUs.jsp',
		controller:'genericController'
	});
	$routeProvider.when('/gallery',{
		templateUrl:'pages/info/Gallery.jsp',
		controller:'galleryController'
	});
	$routeProvider.when('/admin',{
		templateUrl:'pages/templates/blankAdmin.jsp',
		controller:'genericController'
	});
	$routeProvider.when('/login',{
		templateUrl:'pages/misc/login.jsp',
		controller:'loginController'
	});
	$routeProvider.when('/AdminCustomer',{
		templateUrl:'pages/admin/AdminCustomer.jsp',
		controller:'customerController'
	});
	$routeProvider.when('/AdminProperties',{
		templateUrl:'pages/admin/AdminProperties.jsp',
		controller:'propertyController'
	});
	$routeProvider.when('/AdminAppUser',{
		templateUrl:'pages/admin/AdminAppUser.jsp',
		controller:'propertyController'
	});	
	$routeProvider.when('/AdminGallery',{
		templateUrl:'pages/admin/AdminGallery.jsp',
		controller:'adminGalleryController'
	});	
	$routeProvider.otherwise({redirectTo:'/home'});
	
	//$locationProvider.hashPrefix('!');
	/* Register error provider that shows message on failed requests or redirects to login page on
	 * unauthenticated requests */
    $httpProvider.interceptors.push(function ($q, $rootScope, $location, $cookieStore) {
	        return {
	        	'responseError': function(rejection) {
	        		var status = rejection.status;
	        		var config = rejection.config;
	        		var method = config.method;
	        		var url = config.url;
	      
	        		if (status == 401) {
	        			delete $rootScope.user;
	        			delete $rootScope.authToken;
	        			$cookieStore.remove('authToken');
	        			$location.path("/login");	        			
	        		} else if (status != 500){
	        			$rootScope.error = method + " on " + url + " failed with status " + status;
	        		}
	              
	        		return $q.reject(rejection);
	        	}
	        };
	    }
    );
    
    /* Registers auth token interceptor, auth token is either passed by header or by query parameter
     * as soon as there is an authenticated user */
    $httpProvider.interceptors.push(function ($q, $rootScope, $location) {
        return {
        	'request': function(config) {
        		var isRestCall = config.url.indexOf('rest') == 0;
        		if (isRestCall && angular.isDefined($rootScope.authToken)) {
        			var authToken = $rootScope.authToken;
        			if (builderAppConfig.useAuthTokenHeader) {
        				config.headers['X-Auth-Token'] = authToken;
        			} else {
        				config.url = config.url + "?token=" + authToken;
        			}
        		}
        		return config || $q.when(config);
        	}
        };
    });
  }	
]);

builderapp.run(function($rootScope, $location, $cookieStore, AuthenticationService) {
	
	/* Reset error when a new view is loaded */
	$rootScope.$on('$viewContentLoaded', function() {
		delete $rootScope.error;
	});
	
	$rootScope.hasRole = function(role) {
		
		if ($rootScope.user === undefined) {
			return false;
		}
		
		if ($rootScope.user.roles[role] === undefined) {
			return false;
		}
		
		return $rootScope.user.roles[role];
	};
	
	$rootScope.hasPermission = function() {
		
		if ($rootScope.user === undefined) {
			return false;
		}
		var permittedRoles = ["admin","agent","manager"];		
		var arrayLength = permittedRoles.length;
		
		for (var i = 0; i < arrayLength; i++) {
			if ($rootScope.user.roles[permittedRoles[i]] != undefined
					&& $rootScope.user.roles[permittedRoles[i]] == true) {
				return true;				
			}
		}		
		return false;
	};
	
	$rootScope.logout = function() {
		delete $rootScope.user;
		delete $rootScope.authToken;
		$cookieStore.remove('authToken');
		$location.path("/login");		
	};
	
	 /* Try getting valid user from cookie or go to login page */
	var originalPath = $location.path();
	$location.path("/home");
	var authToken = $cookieStore.get('authToken');
	if (authToken !== undefined) {
		$rootScope.authToken = authToken;
		AuthenticationService.get(function(user) {
				$rootScope.user = user;
				$location.path(originalPath);
			});
			//.error(function(){
				//alert("Problem with server, please try again after some time!");
			//});
	}
	
	$rootScope.initialized = true;
});
