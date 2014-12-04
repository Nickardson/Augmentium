// Prepare console logging
var io = require('io');
this.print = io.print;
this.error = io.error;

// Load minecraft apis into global namespace
require('minecraft');

// Load 'depend' into global namespace
require('depend');
