var GL11 = org.lwjgl.opengl.GL11;

var gl = {
	/**
	 * Creates a new display list, and begins adding future draw instructions to the displaylist.
	 * @see endList, callList
	 * @return {Number} An identifier to reference the create display list.
	 */
	newList: function () {
		var index = GL11.glGenLists(1);
		GL11.glNewList(index, GL11.GL_COMPILE);
		return index;
	},

	/**
	 * Stops adding draw instructions to a displaylist.
	 * @see newList
	 */
	endList: function () {
		GL11.glEndList();
	},

	/**
	 * Calls a created displaylist with the given identifier
	 * @param  {Number} index The identifier of the displaylist, as given by gl.newList()
	 */
	callList: function (index) {
		GL11.glCallList(index);
	},

	/**
	 * Deletes an existing displaylist.
	 * @param  {Number} index The identifier of the displaylist, as given by gl.newList()
	 */
	deleteList: function (index) {
		GL11.glDeleteLists(index, 1);
	},

	/**
	 * Begins drawing quads, each defined by 4 vertices.
	 * Call gl.end() when done
	 * @see vertex2, end
	 */
	quads: function () {
		GL11.glBegin(GL11.GL_QUADS);
	},

	/**
	 * Begins drawing triangles, each defined by 3 vertices.
	 * Call gl.end() when done
	 * @see vertex2, end
	 */
	triangles: function () {
		GL11.glBegin(GL11.GL_TRIANGLES);
	},

	/**
	 * Begins drawing a polygon, defined by a number of vertices.
	 * Call gl.end() when done
	 * @see vertex2, end
	 */
	polygon: function () {
		GL11.glBegin(GL11.GL_POLYGON);
	},

	/**
	 * Draws a 2d vertex.
	 * Use along with quads, triangles, polygon, etc.
	 * @param  {Number} x
	 * @param  {Number} y
	 */
	vertex2: function (x, y) {
		GL11.glVertex2f(x, y);
	},

	/**
	 * Ends drawing primitives.
	 */
	end: function () {
		GL11.glEnd();
	},

	/**
	 * Draws a rectangle with top-left (x,y) and bottom-right (x2, y2)
	 * @param  {Number} x  X coordinate of top-left
	 * @param  {Number} y  Y coordinate of top-left
	 * @param  {Number} x2 X coordinate of bottom-right
	 * @param  {Number} y2 Y coordinate of bottom-right
	 */
	rectangle: function (x, y, x2, y2) {
		GL11.glRectf(x, y, x2, y2);
	},

	/**
	 * Sets the current color, ie for primitives.
	 * @param  {Number} r Red component, 0-255
	 * @param  {Number} g Green component, 0-255
	 * @param  {Number} b Blue component, 0-255
	 * @param  {Number} (a) Optional Alpha component, 0-1 with 0 fully transparent and 1 fully opaque
	 */
	color: function (r, g, b, a) {
		GL11.glColor4f(r / 255, g / 255, b / 255, a === undefined ? 1 : a);
	}
};

module.exports = gl;
