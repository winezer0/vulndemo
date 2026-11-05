"""Run pinned response consumers against a local deterministic server."""

import asyncio
import http.client
import http.server
import importlib.util
import importlib.metadata
import json
import os
from pathlib import Path
import threading
import unittest
import urllib.request
import urllib.response

import httpx
import requests

BODY = b'{"payload":"fixed-response","independent":"clean"}'


class FixedHandler(http.server.BaseHTTPRequestHandler):
    def do_GET(self):
        if self.path == "/failure":
            self.close_connection = True
            return
        self.send_response(201)
        self.send_header("Content-Type", "application/json")
        self.send_header("Content-Length", str(len(BODY)))
        self.end_headers()
        self.wfile.write(BODY)

    def log_message(self, *_args):
        pass


class ResponseContracts(unittest.TestCase):
    @classmethod
    def setUpClass(cls):
        os.environ["NO_PROXY"] = "127.0.0.1,localhost"
        cls.server = http.server.ThreadingHTTPServer(("127.0.0.1", 0), FixedHandler)
        cls.thread = threading.Thread(target=cls.server.serve_forever, daemon=True)
        cls.thread.start()
        cls.url = "http://127.0.0.1:%d" % cls.server.server_port

    @classmethod
    def tearDownClass(cls):
        cls.server.shutdown()
        cls.server.server_close()
        cls.thread.join(timeout=5)

    def test_versions(self):
        self.assertEqual(importlib.metadata.version("requests"), "2.32.3")
        self.assertEqual(importlib.metadata.version("httpx"), "0.27.2")

    def test_requests_content_and_stream(self):
        response = requests.get(self.url + "/request-marker", timeout=3)
        self.addCleanup(response.close)
        self.assertEqual(response.status_code, 201)
        self.assertEqual(response.content, BODY)
        self.assertEqual(response.text, BODY.decode())
        self.assertEqual(response.json(), json.loads(BODY))
        with requests.get(self.url, stream=True, timeout=3) as streamed:
            self.assertEqual(b"".join(streamed.iter_content(7)), BODY)

    def test_httpx_content_and_stream(self):
        response = httpx.get(self.url, timeout=3, trust_env=False)
        self.addCleanup(response.close)
        self.assertEqual(response.status_code, 201)
        self.assertEqual(response.content, BODY)
        self.assertEqual(response.text, BODY.decode())
        self.assertEqual(response.json(), json.loads(BODY))
        with httpx.stream("GET", self.url, timeout=3, trust_env=False) as streamed:
            self.assertEqual(b"".join(streamed.iter_bytes(7)), BODY)

    def test_urllib_protocols_and_partial_read(self):
        with urllib.request.urlopen(self.url, timeout=3) as response:
            self.assertIsInstance(response, http.client.HTTPResponse)
            prefix = response.read(7)
            self.assertEqual(prefix + response.read(), BODY)
        with urllib.request.urlopen("data:text/plain,local-content") as response:
            self.assertIsInstance(response, urllib.response.addinfourl)
            self.assertNotIsInstance(response, http.client.HTTPResponse)
            self.assertEqual(response.read(), b"local-content")

    def test_failures_and_custom_hook(self):
        with self.assertRaises(requests.ConnectionError):
            requests.get(self.url + "/failure", timeout=3)
        with self.assertRaises(httpx.RemoteProtocolError):
            httpx.get(self.url + "/failure", timeout=3, trust_env=False)

        def replace(response, **_kwargs):
            # Drain the original response before replacing its cached content so
            # urllib3 can release the underlying connection.
            _ = response.content
            response._content = b"custom-content"
            return response

        response = requests.get(self.url, hooks={"response": replace}, timeout=3)
        self.addCleanup(response.close)
        self.assertEqual(response.content, b"custom-content")

    def test_httpx_custom_hook_and_stream_close(self):
        def replace(response):
            response.read()
            response._content = b"httpx-custom-content"

        with httpx.Client(event_hooks={"response": [replace]}, trust_env=False) as client:
            response = client.get(self.url, timeout=3)
            self.assertEqual(response.content, b"httpx-custom-content")

        with httpx.stream("GET", self.url, timeout=3, trust_env=False) as response:
            self.assertEqual(next(response.iter_bytes(7)), BODY[:7])
        with self.assertRaises(httpx.StreamConsumed):
            next(response.iter_bytes(7))

        with httpx.stream("GET", self.url, timeout=3, trust_env=False) as unopened:
            self.assertFalse(unopened.is_closed)
        with self.assertRaises(httpx.StreamClosed):
            next(unopened.iter_bytes(7))

    def test_fixture_read_after_context_is_closed(self):
        fixture = Path(__file__).resolve().parents[2] / "python-variants" / "clients.py"
        spec = importlib.util.spec_from_file_location("httpx_variants_fixture", fixture)
        module = importlib.util.module_from_spec(spec)
        spec.loader.exec_module(module)
        with self.assertRaises(httpx.StreamClosed):
            module.stream_after_context(self.url)

    def test_context_manager_enter_can_replace_value(self):
        events = []

        class ReplacingManager:
            def __enter__(self):
                events.append("enter")
                return (b"entered-first", b"entered-second")

            def __exit__(self, *_args):
                events.append("exit")

        manager = ReplacingManager()
        with manager as (first, second):
            self.assertEqual((first, second), (b"entered-first", b"entered-second"))
            self.assertIsNot(first, manager)
            self.assertEqual(events, ["enter"])
        self.assertEqual(events, ["enter", "exit"])

    def test_httpx_async_stream_and_hook(self):
        async def check():
            async with httpx.AsyncClient(trust_env=False) as client:
                async with client.stream("GET", self.url, timeout=3) as response:
                    self.assertEqual(await response.aread(), BODY)
                async with client.stream("GET", self.url, timeout=3) as unopened:
                    self.assertFalse(unopened.is_closed)
                with self.assertRaises(httpx.StreamClosed):
                    await unopened.aread()
                with self.assertRaises(httpx.RemoteProtocolError):
                    await client.get(self.url + "/failure", timeout=3)

            async def replace(response):
                await response.aread()
                response._content = b"async-custom-content"

            async with httpx.AsyncClient(event_hooks={"response": [replace]}, trust_env=False) as client:
                response = await client.get(self.url, timeout=3)
                self.assertEqual(response.content, b"async-custom-content")

        asyncio.run(check())


if __name__ == "__main__":
    unittest.main(verbosity=2)
