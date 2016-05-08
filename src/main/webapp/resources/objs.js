myApp.factory('Person', function($http,$window) {  
    function Person(personData) {
        if (personData) {
            this.setData(personData);
        }
        // Some other initializations related to person
    };
	

    Person.prototype = {
    		
    		/* Client */
			edit: function() {
				console.log("edit: "+this.burp());
				this.backup = angular.copy(this);
				this.toggleShow();
			},
			cancel: function() {
				if(this.backup != null) {
					this.setData(this.backup);
				}
				console.log("cancel: "+this.burp());
				this.show = false;
				this.backup = null;
			},
			add: function() {
				console.log("create: "+this.burp());
//				this.toggleShow();
				this.create();
				this.cancel();
//				this.setData(new Person());
//				$window.location.reload();
			},
			save: function() {
				console.log("update: "+this.burp());
				this.toggleShow();
				this.backup = null;
				this.update();
			},
			remove: function(list) {
				for (i=0; i<list.length;i++) {
					if(list[i].id == this.id) {
						list.splice(i,1);
						i--;
					}
				}
				this.delete();
			},
			burp: function() {
				return JSON.stringify(this);
			},
	        toggleShow: function() {
// console.log("toggled");
				this.show = !this.show;
	        },
	        
    		/* Server */
	        setData: function(personData) {
	        	angular.extend(this, personData);
//	        	this.show = false;
	        },
	        create: function() {
	        	$http.post('/api/persons/create', this)
	        		console.log("Create Complete: " + this.name);
	        },
	        read: function() {
	        	$http.put('/api/persons/' + this.name + '/read', this);
	        },
	        update: function() {
	        	$http.put('/api/persons/' + this.name + '/update', this)
	        		console.log("Update Complete: " + this.name);
	        },
	        delete: function() {
	        	$http.delete('/api/persons/' + this.name + '/delete')
	        		console.log("Delete Complete: " + this.name);
	        },
	        getImageUrl: function(width, height) {
	        	return '/api/image/service/' + this.name + '/width/height';
	        }
    };
    return Person;
});

myApp.factory('personsManager', function($http, $q, Person) {  
    var personsManager = {
            _pool: {},
            _retrieveInstance: function(personName, personData) {
                var instance = this._pool[personName];

                if (instance) {
                    instance.setData(personData);
                } else {
                    instance = new Person(personData);
                    this._pool[personName] = instance;
                }

                return instance;
            },
            _search: function(personName) {
                return this._pool[personName];
            },
            _load: function(personName, deferred) {
                var scope = this;

                $http.get('/api/persons/' + personName)
                    .success(function(personData) {
                        var person = scope._retrieveInstance(personData.name, personData);
                        deferred.resolve(person);
                    })
                    .error(function() {
                        deferred.reject();
                    });
            },
            /* Public Methods */
            /* Use this function in order to get a person instance by it's name */
            getPerson: function(personName) {
                var deferred = $q.defer();
                var person = this._search(personName);
                if (person) {
                    deferred.resolve(person);
                } else {
                    this._load(personName, deferred);
                }
                return deferred.promise;
            },
            /* Use this function in order to get instances of all the persons */
            loadAllPersons: function() {
                var deferred = $q.defer();
                var scope = this;
                $http.get('/api/persons/all')
                    .success(function(personsArray) {
                        var persons = [];
//                        vm.persons = [];
                        personsArray.forEach(function(personData) {
                            var person = scope._retrieveInstance(personData.name, personData);
                          persons.push(person);
//                            vm.persons.push(person);
                        });
                        deferred.resolve(persons);
//                        deferred.resolve(personsArray);
                    })
                    .error(function() {
                        deferred.reject();
                    });
                return deferred.promise;
            },
            /*
			 * This function is useful when we got somehow the person data and
			 * we wish to store it or update the pool and get a person instance
			 * in return
			 */
            setPerson: function(personData) {
                var scope = this;
                var person = this._search(personData.name);
                if (person) {
                    person.setData(personData);
                } else {
                    person = scope._retrieveInstance(personData);
                }
                return person;
            },

        };
        return personsManager;
    });