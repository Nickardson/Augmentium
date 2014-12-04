var apis = com.github.nickardson.augmentium.script.api;

/**
 * The minecraft world API.
 * @type {API}
 */
module.global.world = apis.APIWorld.instance;

/**
 * The minecraft client API.
 * @type {API}
 */
module.global.client = apis.APIClient.instance;

/**
 * RenderUtilities.
 */
module.global.draw = com.github.nickardson.gui.util.RenderUtility;