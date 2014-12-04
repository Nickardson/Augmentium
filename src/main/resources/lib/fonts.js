var Font = java.awt.Font,
	FontUtil = com.github.nickardson.gui.util.FontUtility,
	UnicodeFontRenderer = com.github.nickardson.gui.util.UnicodeFontRenderer;

var style = {
	plain: Font.PLAIN,
	italic: Font.ITALIC,
	bold: Font.BOLD
};

var DEFAULT_MODS = style.plain,
	DEFAULT_SIZE = 12;

function fromStream (stream, size, mods) {
	size = size || DEFAULT_SIZE;
	mods = mods || DEFAULT_MODS;

	return new UnicodeFontRenderer(FontUtil.fromStream(stream, size, mods));
}

function fromResource (name, size, mods) {
	size = size || DEFAULT_SIZE;
	mods = mods || DEFAULT_MODS;

	return new UnicodeFontRenderer(FontUtil.fromClasspath('/fonts/' + name, size, mods));
}

function fromSystem (name, size, mods) {
	size = size || DEFAULT_SIZE;
	mods = mods || DEFAULT_MODS;

	return new UnicodeFontRenderer(FontUtil.fromSystem(name, size, mods));
}

function fromFile(file, size, mods) {
	if (typeof file == "string") {
		file = new java.io.File(file);
	}
	size = size || DEFAULT_SIZE;
	mods = mods || DEFAULT_MODS;
	
	return new UnicodeFontRenderer(FontUtil.fromFile(file, size, mods));
}

module.exports = {
	'style': style,

	'fromStream': fromStream,
	'fromResource': fromResource,
	'fromFile': fromFile,
	'fromSystem': fromSystem
};
