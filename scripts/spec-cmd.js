var cmd = require('cmd')

// Basic command structure
cmd.add('domain:command').on('run', function () {
	print('Command run!');
});

// toggleable
cmd.add('domain:togglecommand').on('enabled', function () {
	print('On!');
}).on('disabled', function () {
	print('Off!');
}).enable().disable();

// commandline
cmd.add('domain:cmdline').on('cmd', function (args, fullText) {
	print(fullText);
});

// render with fuzzy filter

var SECTION = String.fromCharCode(167);

var ls = getCommandsForInput('Input');

client.onRender.on(function () {
	ls.forEach(function (cmdset, i) {
		var title = cmdset[0],
			command = cmdset[1];
		draw.text(title, 5, 5 + i*15);
	});
});

/**
 * Gets a list of commands given fuzzy input, and formats
 * @param  {String} input The input which will be checked against the list of commands
 * @param  {String} (formatCode) The charactercode which will be prepended to each matchedString, defaults to '#FF0000'
 * @return {Array}       An array of [matchedString, command]
 */
function getCommandsForInput(input, formatCode) {
	formatCode = formatCode || '#FF0000';
	
	var titles = [],
		titleMap = {};
	for (var command in cmd.all) {if (cmd.hasOwnProperty(command)) {
		var title = command.get('title', command.get('name', '?'));
		titleMap[title] = command;
		titles.push([title, command]);
	}}

	return fuzzy.filter(
		input, 
		titles.map(function (a) {return a[0];}),
		{
			pre: SECTION + formatCode,
			post: SECTION + "r"
		}).map(function (a) {
			return [
				a.string,
				titlemap[a.original]
			];
		});
}