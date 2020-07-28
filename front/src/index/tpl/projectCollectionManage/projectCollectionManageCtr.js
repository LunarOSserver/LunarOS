/*
 * @author ChandraLee
 */

(function (LunarApp, undefined) {
    'use strict';
    LunarApp.controller('ProjectCollectionManageCtr', ['$scope','$LunarUser', '$LunarProjectCollection','$state', 'dialog', function ($scope,$LunarUser, $LunarProjectCollection, $state, dialog) {
        $scope.$emit('pageTitle', {
            title: '项目管理',
            descrition: '项目管理。',
            mod: 'projectCollection'
        });
        $scope.projectCollectionList = [];
        $scope.resourceType = 'PROJECT_COLLECTION';
        $scope.isLoading = true;
        var projectCollectionService = $LunarProjectCollection.projectCollectionService;
        var userService = $LunarUser.userService;
        function init() {
            projectCollectionService.getProjectCollection().then(function (res) {
                $scope.projectCollectionList = res.data.result || [];
                for(var i = 0 ; i < $scope.projectCollectionList.length; i++) {
                    $scope.projectCollectionList[i].isEdit = false;
                }
            }).finally(function () {
                $scope.isLoading = false;
            });
        }
        init();
        $scope.saveEdit = function (projectCollection) {
            var newProjectCollection = {
                id: projectCollection.id,
                name: projectCollection.name,
                description: projectCollection.description,
                creatorId: projectCollection.creatorId   
            };
            projectCollectionService.updateProjectCollectionDescription(projectCollection).then(function() {

            },function(res) {
                dialog.error('修改失败', res.data.resultMsg);
            });
        };
        $scope.deleteProjectCollection = function (projectCollectionId) {
            $LunarProjectCollection.deleteProjectCollection(projectCollectionId).then(function() {
                for (var i = 0; i < $scope.projectCollectionList.length; i++) {
                    if ($scope.projectCollectionList[i].id === projectCollectionId) {
                        $scope.projectCollectionList.splice(i, 1);
                    }
                }
            }).finally(function () {
                $scope.isLoading = false;
                $state.go('projectCollectionManage');
            });
        };
        $scope.cancelEdit =function() {
            init();
        };

        
    }]);
})(angular.module('LunarApp'));