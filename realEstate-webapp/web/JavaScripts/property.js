//create angular app
var propertyController = angular.module('AdminPropertyPageController', []);

// create angular controller
propertyController.controller('propertyController', function($scope,$rootScope,$http) {
	$scope.submitted = false;
	$scope.searchScreen=true;
	$scope.searchResultScreen=false;
	$scope.addScreen=false;
	$scope.isSearchResult=false;//Hide edit, delete buttons.
	$scope.noDataMessage="";
	$scope.messageSave="";
	$scope.properties=[];//Array or List of properties.
	$scope.property={};//One Bean or row of property.
	$scope.selectedproperty="-1";
	$scope.appUserId = $rootScope.user.userId;
	
	$scope.toggleDisplay=function(){
		$scope.searchScreen=!$scope.searchScreen;
		$scope.addScreen=!$scope.addScreen;
	}
	
	$scope.search = function(){
		var jsonData = JSON.stringify({
			propName:$scope.propName,
			propAddress:$scope.propAddress});
		var req = {
    			method: "POST",
	            url: "rest/propertyService/getPropertiesByUser/"+$scope.appUserId,
	            headers: {'Content-Type': "application/json; charset=UTF-8"},
	            data:jsonData
    			}
    	var respPromise = $http(req);
        respPromise.success(function(data,status,headers,config) {
         //fetch the data from server
        	$scope.searchResultScreen=true;
        	if(data.length>0){
        		$scope.properties = data;                
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
    	delete $scope.property;    	
    	$scope.toggleDisplay();
    };
    
//  Start Update
    $scope.startUpdate= function(){
    	$scope.property = $scope.properties[$scope.selectedProperty];    	
    	$scope.toggleDisplay();
    };
    
//  DELETE 
	$scope.deleteProperty = function(){		
		$scope.property = $scope.properties[$scope.selectedProperty];
		var confirmation=false;
		if(null!=$scope.property.propertyId){
			var confimMsg="Are you sure, you want to delete this property?";
			confirmation = confirm(confimMsg);
		}
		if(confirmation){
			var jsonData = JSON.stringify($scope.property.propertyId);
			var req = {
					method: "DELETE",
					url: "rest/propertyService/deleteProperty",
					headers: {'Content-Type': "application/json; charset=UTF-8"},
					data:jsonData
			}
			var respPromise = $http(req);
			respPromise.success(function(data,status,headers,config) {
				//fetch the data from server
				if(data){
					$scope.messageSave="Property deleted successfully";
					$scope.search();
					$scope.selectedProperty="-1";
				}else{
					$scope.messageSave="Problem deleting Property";
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
    		var serviceName="addProperty";
    		var actionName="Add";
    		
    		if(null!=$scope.property.propertyId && $scope.property.propertyId>0){    			
    			methodName="PUT";
    			serviceName="updateProperty";
    			actionName="Edit";
        	}
    		var jsonData = JSON.stringify($scope.property);
    		var req = {
        			method: methodName,
    	            url: "rest/propertyService/"+serviceName,
    	            headers: {'Content-Type': "application/json; charset=UTF-8"},
    	            data:jsonData
        			}
        	var respPromise = $http(req);
            respPromise.success(function(data,status,headers,config) {
             //fetch the data from server
            	if(data){
            		$scope.messageSave="Property "+actionName+"ed successfully";
            	}else{
            		$scope.messageSave="Problem "+actionName+"ing Property";
            	}             
            });

            respPromise.error(function (data, status, headers, config) {
              alert("Problem with server, please try again after some time!");
            });
            $scope.resetPropertyPage();
    	}    	
    };
    
   
	$scope.resetPropertyPage = function(){
		$scope.submitted=false;
		$scope.searchResultScreen=false;
		$scope.noDataMessage="";
		$scope.messageSave="";
		$scope.properties=[];//Array or List of properties.
		$scope.property={};//One Bean or row of property.
		$scope.toggleDisplay();
	};
});
