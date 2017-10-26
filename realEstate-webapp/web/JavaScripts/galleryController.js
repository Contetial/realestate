'use strict';

var galleryController = angular.module('SliderPageController', [ 'ngAnimate','ngTouch' ]);

galleryController.controller('galleryController',function($scope,$rootScope,$q, $http, $timeout) {
		$scope.properties=[];
		$scope.property={};
		$scope.galleries=[];
		$scope.gallery={};  
		$scope.images=[];
		$scope.image={};
		$scope.videos=[];
		$scope.video={};

		$scope.getProperties = function(){
			var req = {
	    			method: "GET",
		            url: "rest/propertyService/getAllProperties",
		            headers: {'Content-Type': "application/json; charset=UTF-8"}	            
	    			}
	    	var respPromise = $http(req);
	        respPromise.success(function(data,status,headers,config) {
	         //fetch the data from server
	        	if(data.length>0){
	        		$scope.properties = data;                
	                $scope.property = $scope.properties[0];
	                $scope.getGalleryByProperty($scope.property.propertyId);
	        	}else{
	        		$scope.noDataMessage="No existing property found, creating new one";        		
	        	}         
	        });

	        respPromise.error(function (data, status, headers, config) {
	          //alert("Problem with gallery data retrival!");
	        	$scope.noDataMessage="Warning! No existing property found";
	        });
	        
		};
		
		$scope.getGalleryByProperty = function(propertyId){
			$scope.property.propertyId =1;
			var req = {
	    			method: "GET",
		            url: "rest/galleryService/getGalleryByPropertyId/"+propertyId,
		            headers: {'Content-Type': "application/json; charset=UTF-8"}	            
	    			}
	    	var respPromise = $http(req);
	        respPromise.success(function(data,status,headers,config) {
	         //fetch the data from server
	        	if(data.length>0){
	        		$scope.galleries = data;                
	                $scope.gallery = $scope.galleries[0];
	                $scope.images = $scope.gallery.images;
	                $scope.videos = $scope.gallery.videos;
	        	}else{
	        		$scope.noDataMessage="No existing gallery found, creating new one";        		
	        	}         
	        });

	        respPromise.error(function (data, status, headers, config) {
	          //alert("Problem with gallery data retrival!");
	        	$scope.noDataMessage="Warning! No existing gallery found";
	        });
	        
	     };	     
	    
	    $scope.getProperties();
	    
	    // initial property index
	    $scope._PropIndex = 0;

	    // if a current property is the same as requested property
	    $scope.isActiveProp = function (index) {	    	
	    	return $scope._PropIndex === index;
	    };

	    // show prev property
	    $scope.showPrevProp = function () {
	      $scope._PropIndex = ($scope._PropIndex > 0) ? --$scope._PropIndex : $scope.properties.length - 1;
	      $scope.getGalleryByProperty($scope.properties[$scope._PropIndex].propertyId);
	    };

	    // show next property
	    $scope.showNextProp = function () {
	       $scope._PropIndex = ($scope._PropIndex < $scope.properties.length - 1) ? ++$scope._PropIndex : 0;
	       $scope.getGalleryByProperty($scope.properties[$scope._PropIndex].propertyId);
	    };
	    
	    // initial image index
	    $scope._Index = 0;

	    // if a current image is the same as requested image
	    $scope.isActive = function (index) {
	        return $scope._Index === index;
	    };

	    // show prev image
	    $scope.showPrev = function () {
	        $scope._Index = ($scope._Index > 0) ? --$scope._Index : $scope.images.length - 1;	        	        
	    };

	    // show next image
	    $scope.showNext = function () {
	        $scope._Index = ($scope._Index < $scope.images.length - 1) ? ++$scope._Index : 0;	        
	    };

	    // show a certain image
	    $scope.showImage = function (index) {
	        $scope._Index = index;
	    };
});

galleryController.filter('trusted', ['$sce', function ($sce) {
    return function(url) {
        return $sce.trustAsResourceUrl(url);
    };
}]);

