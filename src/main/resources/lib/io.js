/**
 * This horker of code combines an argument list (not quite an array) into a comma separated string.
 * @internal
 * @param  {Arguments} args The arguments to combined.
 * @return {String}         The concatenated string.
 */
function conjoin(args) {
    return Array.prototype.slice.call(args).map(function(b) {
        return b.toString();
    }).join(', ');
}

/**
 * Logs a message to the console. Arguments will be separated by commas.
 */
function print() {
    _logger.info(conjoin(arguments));
}

/**
 * Logs an error to the console. Arguments will be separated by commas.
 */
function error() {
    _logger.error(conjoin(arguments));
}

module.exports = {
    'print': print,
    'error': error
};