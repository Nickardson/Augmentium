var events = require('events');

var all = {};

/**
 * A command.
 * @param {String} name The globally unique name of this command.
 */
function Command(name) {
	events.link(this);

	this.data = {};

	this.data.name = name;

	var oldOn = this.on;

	/**
	 * Adds a listener for the given name.
	 * @param {String} name The name of the listener
	 * @param {Function} func The function to call when the given event is triggered.
	 * @return {Command} Self, for chaining.
	 */
	this.on = function (name, func) {
		if (name == 'enable' || name == 'disable') {
			this.data.enabled = false;
			this.data.toggleable = true;
		} else if (name == 'cmd') {
			this.data.cmdable = true;
		}

		oldOn(name, func);

		return this;
	};

	/**
	 * If this command has a 'enabled' or 'disabled' listener, toggles the state and calls the respective listener. Otherwise, does nothing.
	 * @return {Command} Self, for chaining.
	 */
	this.toggle = function () {
		if (this.data.toggleable) {
			this.data.enabled = !this.data.enabled;
			this.trigger(this.data.enabled ? 'enable' : 'disable');
		}

		return this;
	};

	/**
	 * If this command has an 'enabled' listener, calls it. Otherwise, does nothing
	 * @return {Command} Self, for chaining.
	 */
	this.enable = function () {
		if (this.data.toggleable && !this.data.enabled) {
			this.data.enabled = true;
			this.trigger('enable');
		}
		return this;
	};

	/**
	 * Returns whether this command is enabled.
	 * @return {Boolean} Whether this command is enabled
	 */
	this.enabled = function () {
		return this.get('enabled');
	}

	/**
	 * If this command has an 'disabled' listener, calls it. Otherwise, does nothing
	 * @return {Command} Self, for chaining.
	 */
	this.disable = function () {
		if (this.data.toggleable && this.data.enabled) {
			this.data.enabled = false;
			this.trigger('disable');
		}
		return this;
	};

	/**
	 * Sets a data value in this command.
	 * @param {String} name The name of the data to store
	 * @param {Object} data The object to store.
	 */
	this.set = function (name, data) {
		this.data[name] = data;
		return this;
	}

	/**
	 * Gets a data value in this command.
	 * @param {String} name The name of the data to retrieve
	 * @param {String} def Default value if the data doesn't exist
	 */
	this.get = function (name, def) {
		var dat = this.data[name];
		if (dat === undefined) {
			return def;
		} else {
			return dat;
		}
	}

	all[name] = this;
}

function add (name, func) {
	var c = new Command(name);

	return c;
}

module.exports = {
	'add': add,
	'all': all
}
