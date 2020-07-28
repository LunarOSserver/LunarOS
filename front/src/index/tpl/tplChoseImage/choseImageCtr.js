/*
 * @author tiansheng
 */

(function (LunarApp, undefined) {
  'use strict';
  if (typeof LunarApp === 'undefined') return;
  LunarApp.controller('choseImageCtr', ['$scope', '$modalInstance', '$modal', '$LunarProject', '$LunarImage', '$LunarData', 'dialog', '$state', function ($scope, $modalInstance, $modal, $LunarProject, $LunarImage, $LunarData, dialog, $state) {
    $scope.loading = true;
    $scope.key = {
      searchKey: ''
    };
    $scope.imageList = [];
    $LunarImage.imageService.getBaseImages().then(function (res) {
      $scope.imageList = res.data.result;
    }).finally(function () {
      $scope.loading = false;
    });
    $scope.cancel = function () {
      $modalInstance.dismiss('cancel');
    };
    $scope.choseImage = function (img) {
      $modalInstance.close(img);
    };
  }]);
})(angular.module('LunarApp'));