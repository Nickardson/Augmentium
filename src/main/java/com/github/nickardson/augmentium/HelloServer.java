package com.github.nickardson.augmentium;

import fi.iki.elonen.NanoHTTPD;
import org.mozilla.javascript.Scriptable;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class HelloServer extends NanoHTTPD {
    public HelloServer() {
        super("localhost", 8080);
    }

    @Override
    public Response serve(IHTTPSession session) {
        Method method = session.getMethod();

        StringBuilder msg = new StringBuilder();

        Map<String, String> files = new HashMap<String, String>();
        if (Method.PUT.equals(method) || Method.POST.equals(method)) {
            try {
                session.parseBody(files);
            } catch (IOException ioe) {
                return new Response(Response.Status.INTERNAL_ERROR, MIME_PLAINTEXT, "SERVER INTERNAL ERROR: IOException: " + ioe.getMessage());
            } catch (ResponseException re) {
                return new Response(re.getStatus(), MIME_PLAINTEXT, re.getMessage());
            }
        }
        // get the POST body
        //String postBody = session.getQueryParameterString();

        Map<String, String> params = session.getParms();
        msg.append("<html><title>Augmentium code input</title><body>\n");
        msg.append("<form action='?' method='post'><input type='submit' value='Run'><br/><textarea data-editor='javascript' name='code' rows='25' cols='100'>");
        if (params.get("code") != null) {
            // System.out.println(params.get("code"));

            String domain = "web/" + session.getHeaders().get("http-client-ip");
            Scriptable scope = ScriptEngine.spawnScope(domain);
            try {
                ScriptEngine.eval(scope, params.get("code"), domain);
            } catch (Exception e) {
                e.printStackTrace();
                //((Logger) scope.get("_logger", scope)).throwing(e);
            }

            msg.append(params.get("code"));
        }
        msg.append("</textarea></form>");
        // Make textarea an Ace editor.
        msg.append("<script src='//ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js'></script><script src='//d1n0x3qji82z53.cloudfront.net/src-min-noconflict/ace.js'></script><script>$(function(){$('textarea[data-editor]').each(function(){var e=$(this);var t=e.data('editor');var n=$('<div>',{position:'absolute',width:e.width(),height:e.height(),'class':e.attr('class')}).insertBefore(e);e.css('visibility','hidden');var r=ace.edit(n[0]);r.renderer.setShowGutter(false);r.getSession().setValue(e.val());r.getSession().setMode('ace/mode/'+t);e.closest('form').submit(function(){e.val(r.getSession().getValue())})})})</script>");
        msg.append("</body></html>");

        return new NanoHTTPD.Response(msg.toString());
    }
}
