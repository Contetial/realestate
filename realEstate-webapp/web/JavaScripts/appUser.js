//create angular app
var appUserController = angular.module('AdminAppUserPageController', []);

// create angular controller
appUserController.controller('appUserController', function($scope,$rootScope,$http) {
	$scope.submitted = false;
	$scope.searchScreen=true;
	$scope.searchResultScreen=false;
	$scope.addScreen=false;
	$scope.isSearchResult=false;//Hide edit, delete buttons.
	$scope.noDataMessage="";
	$scope.messageSave="";
	$scope.validationError="";
	$scope.appUsers=[];//Array or List of appUsers.
	$scope.appUser={};//One Bean or row of appUser.
	$scope.selectedappUser="-1";
	$scope.appUserId = $rootScope.user.userId;
	
	$scope.toggleDisplay=function(){
		$scope.searchScreen=!$scope.searchScreen;
		$scope.addScreen=!$scope.addScreen;
	}
	
	$scope.search = function(){
		var jsonData = JSON.stringify({
			managerId:$scope.appUserId,
			userName:$scope.appUserName,
			address:$scope.appUserAddress});
		var req = {
    			method: "POST",
	            url: "rest/userService/getUsers",
	            headers: {'Content-Type': "application/json; charset=UTF-8"},
	            data:jsonData
    			}
    	var respPromise = $http(req);
        respPromise.success(function(data,status,headers,config) {
         //fetch the data from server
        	$scope.searchResultScreen=true;
        	if(data.length>0){
        		$scope.appUsers = data;                
                $scope.isSearchResult=true;//Show edit, delete buttons.
                $scope.noDataMessage="";
        	}else{
        		$scope.isSearchResult=false;//Show edit, delete buttons.
        		$scope.noDataMessage="No data found";        		
        	}         
        });

        respPromise.error(function (data, status, headers, config) {
          alert("Problem with data retrival!");
        });    	
    };

//  Start add
    $scope.startAdd= function(){
    	delete $scope.appUser;    	
    	$scope.toggleDisplay();
    };
    
//  Start Update
    $scope.startUpdate= function(){
    	$scope.appUser = $scope.appUsers[$scope.selectedappUser];    	
    	$scope.toggleDisplay();
    };
    
//  DELETE 
	$scope.deleteAppUser = function(){		
		$scope.appUser = $scope.appUsers[$scope.selectedappUser];
		var confirmation=false;
		if(null!=$scope.appUser.userId){
			var confimMsg="Are you sure, you want to delete this appUser?";
			confirmation = confirm(confimMsg);
		}
		if(confirmation){
			var jsonData = JSON.stringify($scope.appUser.userId);
			var req = {
					method: "DELETE",
					url: "rest/userService/deleteAppUser",
					headers: {'Content-Type': "application/json; charset=UTF-8"},
					data:jsonData
			}
			var respPromise = $http(req);
			respPromise.success(function(data,status,headers,config) {
				//fetch the data from server
				if(data){
					$scope.messageSave="AppUser deleted successfully";
					$scope.search();
					$scope.selectedappUser="-1";
				}else{
					$scope.messageSave="Problem deleting AppUser";
				}             
			});

			respPromise.error(function (data, status, headers, config) {
				alert("Problem with server, please try again after some time!");
			});
		}
	  
	};
   
    $scope.submitForm = function(isValid){
    	$scope.submitted=true;
    	if(isValid){
    		var methodName="POST";
    		var serviceName="addAppUser";
    		var actionName="Add";
    		
    		if(null!=$scope.appUser.userId && $scope.appUser.userId>0){    			
    			methodName="PUT";
    			serviceName="updateAppUser";
    			actionName="Edit";
        	}
    		//$scope.setParseSafeData();
    		var jsonData = JSON.stringify($scope.appUser);
    		var req = {
        			method: methodName,
    	            url: "rest/userService/"+serviceName,
    	            headers: {'Content-Type': "application/json; charset=UTF-8"},
    	            data:jsonData
        			}
        	var respPromise = $http(req);
            respPromise.success(function(data,status,headers,config) {
             //fetch the data from server
            	if(data){
            		$scope.resetAdminAppUserPage();
            		$scope.messageSave=data.message;            		
            	}else{
            		$scope.messageSave="Problem "+actionName+"ing appUser";
            	}             
            });

            respPromise.error(function (data, status, headers, config) {
              // alert("Problem with server, please try again after some time!");
              if(status==500){
            	  $scope.validationError="Validation Error: "+data.message;
              }
            });
            
    	}    	
    };
    
   
	$scope.resetAdminAppUserPage = function(){
		$scope.submitted=false;
		$scope.searchResultScreen=false;
		$scope.noDataMessage="";
		$scope.messageSave="";
		$scope.validationError="";
		$scope.appUsers=[];//Array or List of appUsers.
		$scope.appUser={};//One Bean or row of appUser.
		$scope.toggleDisplay();
	};	
});
