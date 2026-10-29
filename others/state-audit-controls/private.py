import os
import tempfile

# WritePrivate keeps the output inside an automatically removed private directory.
def write_private(payload):
    with tempfile.TemporaryDirectory() as directory:
        descriptor = os.open(os.path.join(directory, "output"), os.O_WRONLY | os.O_CREAT | os.O_EXCL, 0o600)
        try:
            os.write(descriptor, payload)
        finally:
            os.close(descriptor)
