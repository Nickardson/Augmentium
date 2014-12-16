package com.github.nickardson.augmentium;

import fi.iki.elonen.NanoHTTPD;
import fi.iki.elonen.SimpleWebServer;
import org.mozilla.javascript.Scriptable;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class AugmentiumServer extends SimpleWebServer {
    public AugmentiumServer(File wwwroot, boolean quiet) {
        super("localhost", 8080, wwwroot, quiet);
    }

    @Override
    public Response serve(IHTTPSession session) {
        Method method = session.getMethod();
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
        Map<String, String> params = session.getParms();

        if (session.getUri().equals("/api/code")) {
            if (params.get("code") != null) {
                String domain = "web/" + session.getHeaders().get("http-client-ip");
                Scriptable scope = ScriptEngine.spawnScope(domain);
                try {
                    ScriptEngine.eval(scope, params.get("code"), domain);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            return new NanoHTTPD.Response("OK");
        }

        return super.serve(session);
    }
}
