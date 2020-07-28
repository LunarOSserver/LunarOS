/*
 * @author ChandraLee
 */

(function (LunarApp, undefined) {
	'use strict';
	if (typeof LunarApp === 'undefined') return;
	LunarApp.controller('CodeInfoModalCtr', CodeInfoModalCtr);

	function CodeInfoModalCtr($scope, $modalInstance, project, showForm) {
		var _this = this;
		_this.showForm = showForm;
		_this.project = angular.copy(project);
		$scope.close = function() {
			$modalInstance.dismiss('cancel');
		};
		$scope.toModify = function() {
			$modalInstance.close(_this.project);
		};
	}
	CodeInfoModalCtr.$inject = ['$scope', '$modalInstance', 'project', 'showForm'];
})(angular.module('LunarApp'));