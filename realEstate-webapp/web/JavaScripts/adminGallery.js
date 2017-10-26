var galleryController = angular.module('AdminGalleryPageController', []);

galleryController.controller('adminGalleryController',['$scope', '$rootScope','$http', 
                                                  function($scope,$rootScope,$http){
	$scope.submitted = false;
	$scope.searchScreen=true;
	$scope.searchResultScreen=false;
	$scope.addScreen=false;
	$scope.isSearchResult=false;//Hide edit, delete buttons.
	$scope.noPropDataMessage="";
	$scope.noDataMessage="";
	$scope.messageSave="";
	$scope.properties=[];//Array or List of properties.
	$scope.property={};//One Bean or row of property.
	$scope.selectedproperty="-1";
	$scope.galleries=[];
	$scope.gallery={};
	$scope.images=[];
	$scope.image={};
	$scope.appUserId = $rootScope.user.userId;

	$scope.toggleDisplay=function(){
		$scope.searchScreen=!$scope.searchScreen;
		$scope.addScreen=!$scope.addScreen;
	}
	
	$scope.search = function(){
		var jsonData = JSON.stringify({"propertyname":$scope.propName});
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
                $scope.noPropDataMessage="";                
        	}else{
        		$scope.isSearchResult=false;//Show edit, delete buttons.
        		$scope.noPropDataMessage="No property data found";        		
        	}         
        });

        respPromise.error(function (data, status, headers, config) {
          alert("Problem with property data retrival!");
        });    	
    };
	//Udate gallery
	$scope.startUpdate=function(){
		$scope.property=$scope.properties[$scope.selectedProperty];
		if($scope.property != undefined){
			$scope.toggleDisplay();
			$scope.getGalleryByProperty($scope.property.propertyId);			
		}else{
			alert("Please select a property.");
		}		
	};
	
	$scope.getGalleryByProperty = function(propertyID){
		var req = {
    			method: "GET",
	            url: "rest/galleryService/getGalleryByPropertyId/"+propertyID,
	            headers: {'Content-Type': "application/json; charset=UTF-8"}	            
    			}
    	var respPromise = $http(req);
        respPromise.success(function(data,status,headers,config) {
         //fetch the data from server
        	if(data.length>0){
        		$scope.galleries = data;                
                $scope.gallery = $scope.galleries[0];
                if(null==$scope.gallery){
                	$scope.noDataMessage="No existing gallery found, creating new one";
                }
        	}else{
        		$scope.noDataMessage="No existing gallery found, creating new one";        		
        	}         
        });

        respPromise.error(function (data, status, headers, config) {
          //alert("Problem with gallery data retrival!");
        	$scope.noDataMessage="Warning! No existing gallery found, new one will be created..";
        });
	};
	
	$scope.getGalleryById = function(galleryID){
		var req = {
    			method: "GET",
	            url: "rest/galleryService/getGalleryById/"+galleryID,
	            headers: {'Content-Type': "application/json; charset=UTF-8"}	            
    			}
    	var respPromise = $http(req);
        respPromise.success(function(data,status,headers,config) {
         //fetch the data from server
            $scope.gallery = data;        	         
        });

        respPromise.error(function (data, status, headers, config) {
          //alert("Problem with gallery data retrival!");
        	$scope.noDataMessage="Warning! No existing gallery found, new one will be created..";
        });
	};
	
	
	/*<DELETE IMAGE>*/
	
	  
	$scope.deleteImage = function(imageIdsStr){	
		   //alert(imageIdsStr);
			$scope.image = $scope.images[$scope.selectedImage];
			var confirmation=false;
			if(imageIdsStr>0){
				var confimMsg="Are you sure, you want to delete this image?";
				confirmation = confirm(confimMsg);
			}
			if(confirmation){
				var jsonData = imageIdsStr;
				//alert(jsonData);
				var req = {
						method: "DELETE",
						url: "rest/imageService/deleteImage",
						headers: {'Content-Type': "application/json; charset=UTF-8"},
						data:jsonData
				}
				var respPromise = $http(req);
				respPromise.success(function(data,status,headers,config) {
					//fetch the data from server
					if(data){
						$scope.messageSave="Image deleted successfully";
						$scope.search();
						$scope.selectedImage="-1";
					}else{
						$scope.messageSave="Problem deleting image";
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
    		$scope.uploadFile();    		
    	}
	};
	
	
	
	$scope.submitVideoForm = function(isValid){
    	$scope.submitted=true;
    	if(isValid){
    		$scope.uploadVideo();    		
    	}
	};
	
	$scope.uploadFile = function(){
		var uploadUrl = "rest/galleryService/addImage";
        var fd = new FormData();
        var propertyId = $scope.property.propertyId;
	    fd.append('propertyId', propertyId);
	    var fileName = $scope.imageName;        
        fd.append('imageName', fileName);
        var file = $scope.myFile;
        fd.append('file', file);
        if(null!=$scope.gallery && null!=$scope.gallery.galleryid){
        	var galleryId = $scope.gallery.galleryid;  
        	fd.append('galleryId', galleryId);
        }else{
        	fd.append('galleryId', '');
        }
        
        $http.post(uploadUrl, fd, {
            transformRequest: angular.identity,
            headers: {'Content-Type': undefined}
        })
        .success(function(data, status, headers, config){
       		$scope.resetGalleryPage();
           	$scope.messageSave=data.message;
           	if(null!=$scope.gallery
           			&& null!=$scope.gallery.galleryid 
           			&& undefined !=$scope.gallery.galleryid){
           		$scope.getGalleryById($scope.gallery.galleryid);
           	}else{
           		$scope.getGalleryByProperty($scope.property.propertyId);
           	}
        })
        .error(function(data, status, headers, config){
        	$scope.noDataMessage="Error uploading image. Server Response: "+data.message;
        });   
        
    };
   
	  
	
   
    
    $scope.uploadVideo = function(){
    	
	    var jsonData = "";
	    	if(null!=$scope.gallery && null!=$scope.gallery.galleryid){	    		 
	    		jsonData = JSON.stringify({	    	    	
	    			videoname:$scope.videoName,
	    			videopath:$scope.videopath,
	    			gallery:{galleryid:$scope.gallery.galleryid,
	    				propertyId:$scope.property.propertyId}
	    	    });
	    	}else{
	    		jsonData = JSON.stringify({
	    			gallery:{propertyId:$scope.property.propertyId},
	    			videoname:$scope.videoName,
	    			videopath:$scope.videopath
	    	    });
	    	}	
	    
	    var uploadUrl = "rest/galleryService/addVideo";
	    
        $http({
        	method: "POST",
            url: uploadUrl,
            headers: {'Content-Type': "application/json; charset=UTF-8"},
            data:jsonData
        })
        .success(function(data, status, headers, config){
       		$scope.resetGalleryPage();
           	$scope.messageSave=data.message;
           	if(null!=$scope.gallery
           			&& null!=$scope.gallery.galleryid 
           			&& undefined !=$scope.gallery.galleryid){
           		$scope.getGalleryById($scope.gallery.galleryid);
           	}else{
           		$scope.getGalleryByProperty($scope.property.propertyId);
           	}
        })
        .error(function(data, status, headers, config){
        	$scope.noDataMessage="Error uploading video. Server Response: "+data.message;
        });        
    };
    
    $scope.resetGalleryPage = function(){
		$scope.submitted=false;
		$scope.searchResultScreen=false;
		$scope.isSearchResult=false;
		$scope.noPropDataMessage="";
		$scope.noDataMessage="";
		$scope.messageSave="";
		delete $scope.imageName;
		delete $scope.myFile;
		delete $scope.videoName;
		delete $scope.videopath;
		document.getElementById("myFileId").value="";		
	};
	}

]);

galleryController.filter('trusted', ['$sce', function ($sce) {
    return function(url) {
        return $sce.trustAsResourceUrl(url);
    };
}]);

galleryController.directive('fileModel', ['$parse', function ($parse) {
    return {
        restrict: 'A',
        link: function(scope, element, attrs) {
            var model = $parse(attrs.fileModel);
            var modelSetter = model.assign;
            
            element.bind('change', function(){
                scope.$apply(function(){
                    modelSetter(scope, element[0].files[0]);
                });
            });
        }
    };
}]);

galleryController.service('fileUpload', ['$http', function ($http) {
    this.uploadFileToUrl = function(
    		propertyId, galleryId, fileName, file, uploadUrl){
        var fd = new FormData();
        fd.append('propertyId', propertyId);
        fd.append('galleryId', galleryId);
        fd.append('fileName', fileName);
        fd.append('file', file);
        $http.post(uploadUrl, fd, {
            transformRequest: angular.identity,
            headers: {'Content-Type': undefined}
        })
        .success(function(){
        	
        })
        .error(function(){
        });
        
    }
}]);
    
     