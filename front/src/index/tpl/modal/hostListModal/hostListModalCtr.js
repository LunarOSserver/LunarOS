/*
 * @author ChandraLee
 */

(function (LunarApp, undefined) {
	'use strict';
	if (typeof LunarApp === 'undefined') return;
	LunarApp.controller('HostListModalCtr', ['$scope', 'hostList', '$modalInstance', 'filterFilter', function ($scope, hostList, $modalInstance, filterFilter) {
		$scope.hostList = filterFilter(hostList, {
			'labelFilter': true
		});
		$scope.cancel = function(){
			$modalInstance.dismiss('cancel');
		};
	}]);
})(angular.module('LunarApp'));