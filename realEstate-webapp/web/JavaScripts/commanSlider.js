
//'use strict';

commanSlider=angular.module('CommanSliderPage', ['ngAnimate', 'ngTouch'])
  commanSlider.controller('commanSlider', function ($scope) {

    // Set of images
    $scope.images = [
        {src: '/images/1.jpg', desc: 'Image 01'},
        {src: '../images/2.jpg', desc: 'Image 02'},
        {src: '../images/3.jpg', desc: 'Image 03'},
        {src: '../images/1.jpg', desc: 'Image 04'},
        {src: '../images/2.jpg', desc: 'Image 05'},
        {src: '../images/3.jpg', desc: 'Image 06'},
       ];
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
    $scope.showimage = function (index) {
        $scope._Index = index;
    };
});

