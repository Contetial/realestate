//create angular app
var adminCustomerController = angular.module('AdminCustomerPageController', []);

// create angular controller
adminCustomerController.controller('customerController', function($scope,$rootScope,$http) {
	$scope.submitted = false;
	$scope.searchScreen=true;
	$scope.searchResultScreen=false;
	$scope.addScreen=false;
	$scope.isSearchResult=false;//Hide edit, delete buttons.
	$scope.noDataMessage="";
	$scope.messageSave="";
	$scope.validationError="";
	$scope.customers=[];//Array or List of Customers.
	$scope.customer={};//One Bean or row of Customer.
	$scope.selectedCustomer="-1";
	$scope.appUserId = $rootScope.user.userId;
	$scope.appUser = {"userName":$rootScope.user.name,
			"userId":$scope.appUserId};
	
	
	$scope.toggleDisplay=function(){
		$scope.searchScreen=!$scope.searchScreen;
		$scope.addScreen=!$scope.addScreen;
	}
	
	$scope.search = function(){
		var jsonData = JSON.stringify({customerName:$scope.custName,address:$scope.custAddress});
		var req = {
    			method: "POST",
	            url: "rest/customerService/getCustomersByUser/"+$scope.appUserId,
	            headers: {'Content-Type': "application/json; charset=UTF-8"},
	            data:jsonData
    			}
    	var respPromise = $http(req);
        respPromise.success(function(data,status,headers,config) {
         //fetch the data from server
        	$scope.searchResultScreen=true;
        	if(data.length>0){
        		$scope.customers = data;                
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
    	delete $scope.customer;    	
    	$scope.toggleDisplay();
    };
    
//  Start Update
    $scope.startUpdate= function(){
    	$scope.customer = $scope.customers[$scope.selectedCustomer];
    	$scope.tempdobDate = new Date();
    	$scope.tempdoaDate = new Date();
    	if(null!=$scope.customer.dobStr){
    		$scope.tempdobDate = new Date($scope.customer.dobStr);
    	}
    	if(null!=$scope.customer.doaStr){
    		$scope.tempdoaDate = new Date($scope.customer.doaStr);
    	}
    	
    	$scope.customer.dobStr = $scope.tempdobDate;
    	$scope.customer.doaStr = $scope.tempdoaDate;
    	$scope.toggleDisplay();
    };
//  DELETE 
	$scope.deleteCustomer = function(){		
		$scope.customer = $scope.customers[$scope.selectedCustomer];
		var confirmation=false;
		if(null!=$scope.customer.customerId){
			var confimMsg="Are you sure, you want to delete this customer?";
			confirmation = confirm(confimMsg);
		}
		if(confirmation){
			var jsonData = JSON.stringify($scope.customer.customerId);
			var req = {
					method: "DELETE",
					url: "rest/customerService/deleteCustomer",
					headers: {'Content-Type': "application/json; charset=UTF-8"},
					data:jsonData
			}
			var respPromise = $http(req);
			respPromise.success(function(data,status,headers,config) {
				//fetch the data from server
				if(data){
					$scope.messageSave="Customer deleted successfully";
					$scope.search();
					$scope.selectedCustomer="-1";
				}else{
					$scope.messageSave="Problem deleting Customer";
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
    		var serviceName="addCustomer";
    		var actionName="Add";
    		
    		if(null!=$scope.customer.customerId && $scope.customer.customerId>0){    			
    			methodName="PUT";
    			serviceName="updateCustomer";
    			actionName="Edit";
        	}
    		$scope.setParseSafeData();
    		$scope.customer.referedBy = $scope.appUser;
    		var jsonData = JSON.stringify($scope.customer);
    		var req = {
        			method: methodName,
    	            url: "rest/customerService/"+serviceName,
    	            headers: {'Content-Type': "application/json; charset=UTF-8"},
    	            data:jsonData
        			}
        	var respPromise = $http(req);
            respPromise.success(function(data,status,headers,config) {
             //fetch the data from server
            	if(data){
            		$scope.resetCustomerPage();
            		$scope.messageSave=data.message;
            	}else{
            		$scope.messageSave="Problem "+actionName+"ing Customer";
            	}             
            });

            respPromise.error(function (data, status, headers, config) {
              //alert("Problem with server, please try again after some time!");
            	if(status==500){
              	  $scope.validationError="Validation Error: "+data.message;
                }
            });
            
    	}    	
    };
    
   	$scope.setParseSafeData = function(){
   		if(null==$scope.customer.email){
   			$scope.customer.email="";
   		}
   		if(null==$scope.customer.dobStr){
   			$scope.customer.dobStr="";
   		}
   		if(null==$scope.customer.doaStr){
   			$scope.customer.doaStr="";
   		}
   	}; 
	
	$scope.resetCustomerPage = function(){
		$scope.submitted=false;
		$scope.searchResultScreen=false;
		$scope.noDataMessage="";
		$scope.messageSave="";
		$scope.validationError="";
		$scope.customers=[];//Array or List of Customers.
		$scope.customer={};//One Bean or row of Customer.
		$scope.toggleDisplay();
	};
});
