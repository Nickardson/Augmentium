/*

Required features:

world.players
world.entities
world.tileEntities

(Player)
Player.bb
Player.position

draw.loadImage(url)

draw.image(img, x, y)
draw.image(img, x, y, width, height)
draw.bb(bb, color)
draw.bb(minX, minY, minZ, maxX, maxY, maxZ, color)

draw.push();
draw.pop();
draw.translate(x, y, z);
draw.translate(Vec3);

draw.rotate(x, y, z, n);

 */

var cmd = require('cmd')
	.add('player:playeresp')
	.set('name', 'Player: PlayerESP')
	.set('togglable', true);

/* Simple version, no rotation, for reference
client.onRender.on(function () {
	if (cmd.enabled()) {
		world.players.forEach(function (player) {
			draw.bb(player.bb, Color.AQUA)
		});
	}
});
*/

// slightly more advanced, rotates with player
client.onRender.on(function () {
	if (cmd.enabled()) {
		world.players.forEach(function (player) {
			draw.push();
			
			// translate to rendering origin
			var dp = client.user.position.subtract(player.position);
			draw.translate(dp);

			// Rotate along with player
			var r = player.yaw * -(Math.PI / 180);
			draw.rotate(0, 1, 0, r);

			// translate back to player
			draw.translate(dp.negate());

			// draw bounding box
			draw.bb(player.bb, Color.AQUA)
			
			draw.pop();
		});
	}
});