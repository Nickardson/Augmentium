var editor;
$(function () {
    editor = ace.edit($("#editor")[0]);
    editor.renderer.setShowGutter(false);
    editor.renderer.setPadding(10);
    editor.setHighlightActiveLine(false)
    editor.setDisplayIndentGuides(true);
    editor.getSession().setMode("ace/mode/javascript");
    editor.setTheme("ace/theme/monokai");

    $("#run").click(function () {
    	var self = $(this);
    	self.html('<span class="loader"><span class="loader-inner"></span></span>');
        $.post('/api/code', {
            'code': editor.getSession().getValue()
        }).done(function (data) {
            console.log("Sent", data);
            setTimeout(function () {
            	self.html("Run");
            }, 2000);
        });
    });

    // try to load from localstorage
    if (localStorage && localStorage.code) {
        editor.getSession().setValue(localStorage.code);
    }

    // enable drag-n-drop
    var event = ace.require('ace/lib/event');
    event.addListener(editor.container, "dragover", function(e) {
        var types = e.dataTransfer.types;
        if (types && Array.prototype.indexOf.call(types, 'Files') !== -1)
            return event.preventDefault(e);
    });

    event.addListener(editor.container, "drop", function(e) {
        var file;
        try {
            file = e.dataTransfer.files[0];
            if (window.FileReader) {
                var reader = new FileReader();
                reader.onload = function() {
                    editor.session.doc.setValue(reader.result);
                };
                reader.readAsText(file);
            }
            return event.preventDefault(e);
        } catch(err) {
            return event.stopEvent(e);
        }
    });


});