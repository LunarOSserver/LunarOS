/*
 * @author ChandraLee
 */

(function (LunarApp, undefined) {
	'use strict';
	if (typeof LunarApp === 'undefined') return;
	LunarApp.controller('BranchCheckModalCtr', BranchCheckModalCtr);

	function BranchCheckModalCtr($modalInstance, $LunarProject, codeInfo, projectId) {
		var vm = this;
		vm.check = 'Branch';
		vm.branchKey = '';
		if (projectId) {
			$LunarProject.projectService.getBranches(projectId).then(function (res) {
				vm.branches = res.data.result || [];
			});
			$LunarProject.projectService.getTags(projectId).then(function (res) {
				vm.tags = res.data.result || [];
			});
		} else {
			$LunarProject.projectService.getBranchesWithoutId(codeInfo.codeId, codeInfo.codeManagerUserId, codeInfo.codeManager).then(function (res) {
				vm.branches = res.data.result || [];
			});
			$LunarProject.projectService.getTagsWithoutId(codeInfo.codeId, codeInfo.codeManagerUserId, codeInfo.codeManager).then(function (res) {
				vm.tags = res.data.result || [];
			});
		}
		vm.toggle = (model) => {
			vm.check = model;
			vm.branchKey = '';
			vm.selectedBranch = '';
		};
		vm.cancel = function () {
			$modalInstance.dismiss('cancel');
		};
		vm.submitBranch = function () {
			$modalInstance.close({
				type: vm.check.toLowerCase(),
				value: vm.selectedBranch
			});
		};
		vm.toggleBranch = function (branch) {
			vm.branchKey = '';
			vm.selectedBranch = branch;
		};
	}
	BranchCheckModalCtr.$inject = ['$modalInstance', '$LunarProject', 'codeInfo', 'projectId'];
})(angular.module('LunarApp'));