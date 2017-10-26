var builderControllers = angular.module('builderControllers',
											['AdminCustomerPageController',
											 'AdminPropertyPageController',
											 'AdminAppUserPageController',
											 'AdminGalleryPageController',
											 'SliderPageController',
											 'AuthenticationController']);

builderControllers.controller('genericController', function($scope) {
	$scope.isDefault=true;
});