function link (object) {
	object._events = {};

	object.on = function (name, func) {
		if (object._events[name] == undefined) {
			object._events[name] = [];
		}

		object._events[name].push(func);

		return object;
	}

	object.trigger = function (name) {
		var args = Array.prototype.slice.call(arguments, 1);
		if (object._events[name] !== undefined) {
			object._events[name].forEach(function (e) {
				e.apply(this, args);
			}, object);
		}

		return object;
	}

	return object;
}

module.exports = {
	'link': link
};

/*

var x = require('events').link({});

x.on("event", function (a, b, c) {
	print(a, b, c);
});

x.trigger("event", 1, 2, 3);

 */