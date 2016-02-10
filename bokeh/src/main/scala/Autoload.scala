package io.continuum.bokeh

object Autoload {
    def template(js_urls: Seq[String], js_raw: Seq[String],
                 css_urls: Seq[String], css_raw: Seq[String],
                 elementid: Option[String] = None) = s"""
(function(global) {
  function now() {
    return new Date();
  }

  if (typeof (window._bokeh_onload_callbacks) === "undefined") {
    window._bokeh_onload_callbacks = [];
  }

  function run_callbacks() {
    window._bokeh_onload_callbacks.forEach(function(callback) { callback() });
    delete window._bokeh_onload_callbacks
    console.info("Bokeh: all callbacks have finished");
  }

  function load_libs(js_urls, callback) {
    window._bokeh_onload_callbacks.push(callback);
    if (window._bokeh_is_loading > 0) {
      console.log("Bokeh: BokehJS is being loaded, scheduling callback at", now());
      return null;
    }
    if (js_urls == null || js_urls.length === 0) {
      run_callbacks();
      return null;
    }
    console.log("Bokeh: BokehJS not loaded, scheduling load and callback at", now());
    window._bokeh_is_loading = js_urls.length;
    for (var i = 0; i < js_urls.length; i++) {
      var url = js_urls[i];
      var s = document.createElement('script');
      s.src = url;
      s.async = false;
      s.onreadystatechange = s.onload = function() {
        window._bokeh_is_loading--;
        if (window._bokeh_is_loading === 0) {
          console.log("Bokeh: all BokehJS libraries loaded");
          run_callbacks()
        }
      };
      s.onerror = function() {
        console.warn("failed to load library " + url);
      };
      console.log("Bokeh: injecting script tag for BokehJS library: ", url);
      document.getElementsByTagName("head")[0].appendChild(s);
    }
  };

  ${elementid.map { elementid => s"""
  var element = document.getElementById("$elementid");
  if (element == null) {
    console.log("Bokeh: ERROR: autoload.js configured with elementid '$elementid' but no matching script tag was found. ")
    return false;
  }
  """.trim
  } getOrElse ""}

  var js_urls = [${js_urls.map(u => "\"" + u + "\"").mkString(", ")}];

  var inline_js = [
    ${js_raw.map { js => s"""
    function(Bokeh) {
      $js
    },
    """.trim
    }.mkString("\n")}
    function(Bokeh) {
      ${css_urls.map { url => s"""
      console.log("Bokeh: injecting CSS: $url");
      Bokeh.embed.inject_css("$url");
      """.trim
      }.mkString("\n")}
      ${css_raw.map { css => s"""
      console.log("Bokeh: injecting raw CSS");
      Bokeh.embed.inject_raw_css("$css");
      """.trim
      }.mkString("\n")}
    }
  ];

  function run_inline_js() {
    for (var i = 0; i < inline_js.length; i++) {
      inline_js[i](window.Bokeh);
    }
  }

  if (window._bokeh_is_loading === 0) {
    console.log("Bokeh: BokehJS loaded, going straight to plotting");
    run_inline_js();
  } else {
    load_libs(js_urls, function() {
      console.log("Bokeh: BokehJS plotting callback run at", now());
      run_inline_js();
    });
  }
}(this));
""".trim
}
