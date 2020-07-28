/*
 * @author ChandraLee
 */

(function (LunarApp, undefined) {'use strict';
    if (typeof LunarApp === 'undefined') return;
    LunarApp.controller('DeployCollectionManageCtr', ['$scope','$LunarUser', '$LunarDeployCollection', '$LunarDeploy', '$state', 'api', 'dialog', function ($scope,$LunarUser, $LunarDeployCollection,$LunarDeploy, $state, api, dialog) {
        $scope.$emit('pageTitle', {
            title: '服务管理',
            descrition: '服务管理。',
            mod: 'deployCollection'
        });
        $scope.resourceType = 'DEPLOY_COLLECTION';
        $scope.baseImageDeleteAuth = false;

        api.user.whoami().then(response => {
            let whoami = response;
            $scope.currentUser = {
                email: whoami.email, 
                id: whoami.id, 
                loginType: whoami.loginType,
                state: loginUser.state,
                username: loginUser.username
            };
            if(whoami.isAdmin){
                $scope.baseImageDeleteAuth = true;
            }
        });

        $scope.collectionList = [];
        $scope.isLoading = true;
        var deployCollectionService = $LunarDeployCollection.deployCollectionService;
        var deployService = $LunarDeploy.deployService;
        function init () {
            deployCollectionService.getDeployCollection().then(function (res) {
                $scope.collectionList = res.data.result || [];
                for(var i = 0 ; i < $scope.collectionList.length; i++) {
                    $scope.collectionList[i].isEdit = false;
                }
            }).finally(function () {
                $scope.isLoading = false;
            });
        }
        init();
        $scope.saveEdit = function (deployCollection) {
            var newDeployCollection = {
                id: deployCollection.id,
                name: deployCollection.name,
                description: deployCollection.description,
                creatorId: deployCollection.creatorId
            };
            deployCollectionService.updateDeployCollectionDescription(newDeployCollection).then(function() {

            },function(res) {
                dialog.error('操作失败', res.data.resultMsg);

            }).finally(function() {
                $state.go('deployCollectionManage');
            });
        };
        $scope.deleteDeployCollection = function (deployCollectionId) {
            $LunarDeployCollection.deleteDeployCollection(deployCollectionId).then(function() {
                for (var i = 0; i < $scope.collectionList.length; i++) {
                    if ($scope.collectionList[i].id === deployCollectionId) {
                        $scope.collectionList.splice(i, 1);
                    }
                }
            }).finally(function () {
                $scope.isLoading = false;
                $state.go('deployCollectionManage');
            });
        };
        $scope.cancelEdit = function () {
            init();
        };
    }]);
})(angular.module('LunarApp'));