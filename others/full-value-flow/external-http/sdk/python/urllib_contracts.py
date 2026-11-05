"""Verify urllib response semantics with only the Python standard library."""

import http.server
import io
import pathlib
import sys
import threading
import unittest
import urllib.request
import urllib.response

sys.path.insert(0, str(pathlib.Path(__file__).resolve().parents[2] / "python"))
from urllib_context import urllib_context, urllib_context_custom


class FixedHandler(http.server.BaseHTTPRequestHandler):
    def do_GET(self):
        body = b"fixed-response"
        self.send_response(200)
        self.send_header("Content-Length", str(len(body)))
        self.end_headers()
        self.wfile.write(body)

    def log_message(self, *_args):
        pass


class OverrideHandler(urllib.request.BaseHandler):
    handler_order = 100

    def http_open(self, request):
        response = urllib.response.addinfourl(io.BytesIO(b"custom-content"), {}, request.full_url, 200)
        response.msg = "OK"
        return response


class UrllibContracts(unittest.TestCase):
    def test_custom_opener_and_independent_default(self):
        server = http.server.ThreadingHTTPServer(("127.0.0.1", 0), FixedHandler)
        thread = threading.Thread(target=server.serve_forever, daemon=True)
        thread.start()
        url = "http://127.0.0.1:%d/request-marker" % server.server_port
        try:
            opener = urllib.request.build_opener(OverrideHandler())
            self.assertEqual(urllib_context(url, "data:text/plain,local-content"),
                             (b"fixed-response", b"local-content"))
            self.assertEqual(urllib_context_custom(url, opener), b"custom-content")
            with opener.open(url) as response:
                self.assertEqual(response.read(), b"custom-content")
            with urllib.request.urlopen(url, timeout=3) as response:
                prefix = response.read(4)
                self.assertEqual(prefix + response.read(), b"fixed-response")
            with urllib.request.urlopen("data:text/plain,local-content") as response:
                self.assertEqual(response.read(), b"local-content")
        finally:
            server.shutdown()
            server.server_close()
            thread.join(timeout=5)


if __name__ == "__main__":
    unittest.main(verbosity=2)
