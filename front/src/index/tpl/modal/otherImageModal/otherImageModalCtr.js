/*
 * @author ChandraLee
 */

(function (LunarApp, undefined) {
	'use strict';
	if (typeof LunarApp === 'undefined') return;
	LunarApp.controller('OtherImageModalCtr', ['$scope', '$modalInstance', function ($scope, $modalInstance) {
		$scope.imageInfo = {
			name: '',
			tag: '',
			registry: ''
		};
		$scope.submitImage = function () {
			$modalInstance.close($scope.imageInfo);
		};
		$scope.cancel = function () {
			$modalInstance.dismiss('cancel');
		};
	}]);
})(angular.module('LunarApp'));