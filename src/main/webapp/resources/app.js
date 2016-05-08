var myApp = angular.module('ioBoard', [
//                                       'ngResource' 
                                       ]);


myApp.controller('MainCtrl', function(personsManager, Person, $filter, $window, $http, $scope) {

	var vm = $scope;
	vm.views = [ 'mainList', 'testingGround' ];
	vm.selection = vm.views[0];
	vm.testing = true;
	vm.persons = []; vm.starts = [], vm.hours = [], vm.mins = [], vm.durations = [];
	vm.newItem = new Person();

	/* Functions */
	vm.addPerson = function(newPerson) {
		var person = angular.copy(newPerson);
		newPerson.create();
		vm.persons.push(person);
		newPerson = new Person();
//		refresh();
	};
	vm.loadTestPeople = function(people) {
		vm.persons = [];
		for (i = 0; i < people.length; i++) {
			var p = people[i];
			vm.persons.push(new Person(p));
		}
	};
	var refresh = function() {
		console.log('List before: '+JSON.stringify(vm.persons));
		personsManager.loadAllPersons().then(function(persons) {
			vm.persons = [];
			for(var i=0;i<persons.length;i++) {
				vm.persons.push(persons[i]);
			}
		});
		console.log('List after: '+JSON.stringify(vm.persons));
	};
	vm.init = function() {
//		vm.loadTestPeople(testData);
		refresh();
	};

	/* INIT */
	vm.init();

});